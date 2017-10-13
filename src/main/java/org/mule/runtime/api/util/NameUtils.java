/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.util;

import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.isBlank;

import java.util.Optional;

/**
 * Utilities for manipulating names of DSL supported components.
 *
 * @since 1.0
 */
public class NameUtils {

  protected NameUtils() {}

  /**
   * Transforms a camel case value into a hyphenized one.
   * <p>
   * For example:
   * {@code messageProcessor} would be transformed to {@code message-processor}
   *
   * @param camelCaseName a {@link String} in camel case form
   * @return the {@code camelCaseName} in hypenized form
   */
  public static String hyphenize(String camelCaseName) {
    return camelScatterConcat(camelCaseName, '-');
  }

  public static String underscorize(String camelCaseName) {
    return camelScatterConcat(camelCaseName, '_');
  }

  private static String camelScatterConcat(String camelCaseName, char concatChar) {
    if (isBlank(camelCaseName)) {
      return camelCaseName;
    }

    StringBuilder result = new StringBuilder();
    String[] parts = camelCaseName.split("(?<!(^|[A-Z]))(?=[A-Z])|(?<!^)(?=[A-Z][a-z])");

    for (int i = 0; i < parts.length; i++) {
      String part = parts[i].trim().toLowerCase();
      result.append(part);
      char lastChar = part.charAt(part.length() - 1);
      if (lastChar == concatChar || lastChar == '_' || lastChar == '-') {
        continue;
      }

      if (i < parts.length - 1) {
        result.append(concatChar);
      }
    }

    return result.toString();
  }

  /**
   * Removes everything that's not a word, a dot nor a hyphen
   *
   * @param originalName name that needs the removal of invalid characters
   * @return name without invalid characters
   */
  public static String sanitizeName(String originalName) {
    return originalName.replaceAll("[^\\w|\\.\\-]", EMPTY);
  }

  /**
   * Removes everything that's not a word, a dot nor a hyphen
   *
   * @param originalName name that needs the removal of invalid characters
   * @return name without invalid characters or an empty string if {@core originalName} was empty
   */
  public static String sanitizeName(Optional<String> originalName) {
    return originalName.map(name -> sanitizeName(name)).orElse(EMPTY);
  }
}
