/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.message.error.matcher;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;
import static org.mule.runtime.api.component.ComponentIdentifier.buildFromStringRepresentation;
import static org.mule.runtime.api.message.error.matcher.WildcardErrorTypeMatcher.WILDCARD_TOKEN;
import static org.mule.runtime.api.i18n.I18nMessageFactory.createStaticMessage;

import org.mule.runtime.api.component.ComponentIdentifier;
import org.mule.runtime.api.exception.ErrorTypeRepository;
import org.mule.runtime.api.exception.MuleRuntimeException;
import org.mule.runtime.api.message.ErrorType;

import java.util.List;
import java.util.Objects;

/**
 * Utils to create the corresponding {@link ErrorTypeMatcher} given an expression.
 *
 * @since 1.6
 */
public class ErrorTypeMatcherUtils {

  private ErrorTypeMatcherUtils() {

  }

  /**
   * @param errorType
   * @return an {@link ErrorTypeMatcher} for a specific {@link ErrorType} (and its parents)
   */
  public static ErrorTypeMatcher createErrorTypeMatcher(ErrorType errorType) {
    return new SingleErrorTypeMatcher(errorType);
  }

  /**
   * @param errorTypeRepository the {@link ErrorTypeRepository} to be used to resolve every {@link ErrorType} in the expression.
   *                            If the error is not found in the registry, a {@link MuleRuntimeException} will be thrown.
   * @param errorTypeNames      the expression to define the error types to match in the {@link ErrorTypeMatcher} to return. The
   *                            expression can have {@link ErrorType}s separated by commas, as well as using wildcards (*) in the
   *                            error's namespace or name.
   * @return an {@link ErrorTypeMatcher} that includes matches all the {@link ErrorType} in the expression, including its parents
   *         and considering the usage of wildcards.
   */
  public static ErrorTypeMatcher createErrorTypeMatcher(ErrorTypeRepository errorTypeRepository, String errorTypeNames) {
    if (errorTypeNames == null) {
      return null;
    }
    String[] errorTypeIdentifiers = errorTypeNames.split(",");
    List<ErrorTypeMatcher> matchers = stream(errorTypeIdentifiers).map((identifier) -> {
      String parsedIdentifier = identifier.trim();
      final ComponentIdentifier errorTypeComponentIdentifier = buildFromStringRepresentation(parsedIdentifier);

      if (doesErrorTypeContainWildcards(errorTypeComponentIdentifier)) {
        return new WildcardErrorTypeMatcher(errorTypeComponentIdentifier);
      } else {
        return new SingleErrorTypeMatcher(errorTypeRepository.lookupErrorType(errorTypeComponentIdentifier)
            .orElseThrow(() -> new MuleRuntimeException(createStaticMessage("Could not find ErrorType for the given identifier: '%s'",
                                                                            parsedIdentifier))));
      }

    }).collect(toList());
    return new DisjunctiveErrorTypeMatcher(matchers);
  }

  private static boolean doesErrorTypeContainWildcards(ComponentIdentifier errorTypeIdentifier) {
    if (errorTypeIdentifier == null) {
      return false;
    }

    if (Objects.equals(WILDCARD_TOKEN, errorTypeIdentifier.getName())) {
      return true;
    }

    if (Objects.equals(WILDCARD_TOKEN, errorTypeIdentifier.getNamespace())) {
      return true;
    }

    return false;
  }

}
