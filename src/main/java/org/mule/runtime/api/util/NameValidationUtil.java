/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.util;

import static java.lang.String.join;
import org.mule.runtime.api.exception.MuleRuntimeException;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;

/**
 * Utility class to validate the names used for global components name attribute.
 * 
 * @since 1.0
 */
public class NameValidationUtil {

  private static final List<String> SPECIAL_CHARACTERS = Arrays.asList("/", "[", "]", "{", "}", "#");
  private static final Pattern SPECIAL_CHARACTERS_PATTERN = Pattern.compile("[/\\[\\]{}#]");
  // cache for identifiers validation to avoid performance impact over regex
  private static final Cache<String, Boolean> validIdentifiersCache = CacheBuilder.newBuilder().maximumSize(10000).build();

  /**
   * Validates that the given {@code string} does not contain any {@link #SPECIAL_CHARACTERS} character
   * 
   * @param string the string to validate.
   * @throws IllegalArgumentException if the string contains an invalid character.
   */
  public static void verifyStringDoesNotContainsReservedCharacters(String string) {
    try {
      Boolean isValidIdentifier = validIdentifiersCache.get(string, () -> !SPECIAL_CHARACTERS_PATTERN.matcher(string).find());
      if (!isValidIdentifier) {
        throw new IllegalArgumentException("Invalid character used in location. Invalid characters are "
            + join(",", SPECIAL_CHARACTERS));
      }
    } catch (ExecutionException e) {
      throw new MuleRuntimeException(e);
    }
  }

}
