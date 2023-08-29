/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.util;

import static org.mule.runtime.api.component.ComponentIdentifier.builder;
import static org.mule.runtime.api.util.Preconditions.checkArgument;

import static java.util.Locale.getDefault;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.apache.commons.lang3.StringUtils.isEmpty;

import org.mule.runtime.api.component.ComponentIdentifier;

import java.util.Optional;

/**
 * Utility class to parse the string representation of an identifier with the syntax "namespace:name".
 *
 * @since 1.5.0
 */
public final class IdentifierParsingUtils {

  private static final String SEPARATOR = ":";

  private IdentifierParsingUtils() {}

  /**
   * Creates a {@link ComponentIdentifier} from a string representation where the expected format is namespace:name. If the string
   * doesn't contain the namespace then it just needs to be the name of the component and the namespace will default to the passed
   * {@code defaultNamespace}.
   *
   * @param stringRepresentation the component identifier represented as a string
   * @param defaultNamespace     if the passed string representation doesn't have a namespace, the resulting
   *                             {@link ComponentIdentifier} will use this value as the namespace.
   * @return the {@link ComponentIdentifier} created from its string representation.
   */
  public static ComponentIdentifier parseComponentIdentifier(String stringRepresentation, String defaultNamespace) {
    return parseIdentifier(stringRepresentation, defaultNamespace, false);
  }

  /**
   * Creates a {@link ComponentIdentifier} for an error type from a string representation where the expected format is
   * NAMESPACE:TYPE. If the string doesn't contain the namespace then it just needs to be the error type, and the namespace will
   * default to the passed {@code defaultNamespace}.
   *
   * @param stringRepresentation the error type represented as a string
   * @param defaultNamespace     if the passed string representation doesn't have a namespace, the resulting
   *                             {@link ComponentIdentifier} will use this value as the namespace.
   * @return the {@link ComponentIdentifier} created from its string representation.
   */
  public static ComponentIdentifier parseErrorType(String stringRepresentation, String defaultNamespace) {
    return parseIdentifier(stringRepresentation, defaultNamespace, true);
  }

  private static ComponentIdentifier parseIdentifier(String stringRepresentation, String defaultNamespace, boolean toUpperCase) {
    checkArgument(!isEmpty(stringRepresentation), "identifier cannot be an empty string or null");
    String[] values = stringRepresentation.split(SEPARATOR);

    String namespace;
    String identifier;
    if (values.length == 2) {
      namespace = values[0];
      identifier = values[1];
    } else {
      namespace = defaultNamespace;
      identifier = values[0];
    }

    if (toUpperCase) {
      namespace = namespace.toUpperCase(getDefault());
      identifier = identifier.toUpperCase(getDefault());
    }

    return builder().namespace(namespace).name(identifier).build();
  }

  /**
   * @param stringRepresentation the string representation of a {@link ComponentIdentifier}
   * @return the namespace if the string representation has the syntax "namespace:name", or an empty {@link Optional} otherwise.
   */
  public static Optional<String> getNamespace(String stringRepresentation) {
    int indexOfSeparator = stringRepresentation.indexOf(SEPARATOR);
    if (indexOfSeparator == -1) {
      return empty();
    } else {
      return of(stringRepresentation.substring(0, indexOfSeparator));
    }
  }
}
