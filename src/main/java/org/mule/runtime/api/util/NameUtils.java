/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.util;

import static java.util.regex.Pattern.compile;
import static java.util.stream.Collectors.joining;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.capitalize;
import static org.apache.commons.lang3.StringUtils.isBlank;

import java.util.Optional;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
 * Utilities for manipulating names of DSL supported components.
 *
 * @since 1.0
 */
public class NameUtils {

  private static final Pattern WORD_BOUND = compile("\\b(?=\\w)");
  private static final Pattern CAMEL_SCATTER_CONCAT = compile("(?<!(^|[A-Z]))(?=[A-Z])|(?<!^)(?=[A-Z][a-z])");
  private static final Pattern SANITIZE_PATTERN = compile("[^\\w|\\.\\-]");

  protected NameUtils() {}

  /**
   * the configuration file component name separator.
   */
  public static final String COMPONENT_NAME_SEPARATOR = "-";

  /**
   * Transforms a camel case value into a hyphenized one.
   * <p>
   * For example: {@code messageProcessor} would be transformed to {@code message-processor}
   *
   * @param camelCaseName a {@link String} in camel case form
   * @return the {@code camelCaseName} in hypenized form
   */
  public static String hyphenize(String camelCaseName) {
    return camelScatterConcat(camelCaseName, '-');
  }

  /**
   * Transforms a camel case value into an underscorized one.
   * <p>
   * For example: {@code messageProcessor} would be transformed to {@code message_processor}
   *
   * @param camelCaseName a {@link String} in camel case form
   * @return the {@code camelCaseName} in underscorized form
   */
  public static String underscorize(String camelCaseName) {
    return camelScatterConcat(camelCaseName, '_');
  }

  /**
   * Converts an string with multiple parts separated by {@code separator} to another string where each part is concatenated using
   * camel case for each part expect for the first part which will be all lowercase.
   * 
   * @param value     the input string
   * @param separator the string that represents the separator
   * @return the converted value.
   */
  public static String toCamelCase(String value, String separator) {
    if (isBlank(value)) {
      return value;
    }
    String[] parts = value.split(separator);
    StringBuilder result = new StringBuilder();
    for (int i = 0; i < parts.length; i++) {
      String part = parts[i].trim().toLowerCase();

      if (i == 0) {
        result.append(part);
      } else {
        result.append(capitalize(part));
      }
    }
    return result.toString();
  }

  private static String camelScatterConcat(String camelCaseName, char concatChar) {
    if (isBlank(camelCaseName)) {
      return camelCaseName;
    }

    StringBuilder result = new StringBuilder();
    String[] parts = CAMEL_SCATTER_CONCAT.split(camelCaseName);

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
    return SANITIZE_PATTERN.matcher(originalName).replaceAll(EMPTY);
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

  private static String humanize(String name) {
    return capitalize(name.replace("_", " ")
        .replace("-", " "));
  }

  /**
   * Transforms a name into a title
   *
   * @param name Name to transform
   * @return The titleized name
   * @since 1.1.1
   */
  public static String titleize(final String name) {
    return WORD_BOUND.splitAsStream(humanize(underscorize(name)))
        .map(StringUtils::capitalize)
        .collect(joining());
  }

}
