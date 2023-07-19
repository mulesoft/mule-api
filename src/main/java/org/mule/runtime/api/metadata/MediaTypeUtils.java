/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.metadata;

import static java.util.Collections.unmodifiableList;
import static java.util.stream.Collectors.toList;
import static org.mule.runtime.api.util.Preconditions.checkNotNull;

import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.List;
import java.util.stream.Stream;

/**
 * Utility class to share logic about {@link MediaType media types}
 *
 * @since 1.0
 * @see MediaType
 */
public final class MediaTypeUtils {

  private static final String TEXT = "text";
  private static final List<MediaType> STRING_REPRESENTABLE_MIME_TYPES = unmodifiableList(Stream.of(
                                                                                                    "application/json",
                                                                                                    "application/xml",
                                                                                                    "application/javascript",
                                                                                                    "application/rtf",
                                                                                                    "application/csv",
                                                                                                    "application/vnd.mozilla.xul+xml",
                                                                                                    "application/x-csh",
                                                                                                    "application/x-sh",
                                                                                                    "application/xhtml+xml",
                                                                                                    "image/svg+xml",
                                                                                                    "text/*")
      .map(MediaType::parse)
      .collect(toList()));


  private MediaTypeUtils() {}

  /**
   * @return the {@link List} of {@link MediaType} that are considered as {@link String} representable
   */
  public static List<MediaType> getStringRepresentableMimeTypes() {
    return STRING_REPRESENTABLE_MIME_TYPES;
  }

  /**
   * Verifies that the given {@code mediaType} is representable as a {@link String}
   *
   * @param mediaType to verify
   * @return boolean indicating whether the {@link MediaType} is {@link String} representable or not
   */
  public static boolean isStringRepresentable(MediaType mediaType) {
    checkNotNull(mediaType, "'mediaType' parameter can not be null");
    return mediaType.getPrimaryType().equals(TEXT) || STRING_REPRESENTABLE_MIME_TYPES
        .stream()
        .anyMatch(type -> type.matches(mediaType));
  }

  /**
   * Parses the {@link String} representation of the {@code charset} into a {@link Charset}
   *
   * @param charset to parse
   * @return a {@link Charset}
   */
  public static Charset parseCharset(String charset) {
    try {
      return Charset.forName(charset);
    } catch (UnsupportedCharsetException e) {
      throw new IllegalArgumentException("No support is available for the requested charset: " + charset, e);
    }
  }
}
