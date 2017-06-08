/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.metadata;

import static java.util.Collections.unmodifiableList;
import static java.util.stream.Collectors.toList;

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
  private static final List<MediaType> STRING_REPRESENTABLE_MIME_TYPES = Stream.of(
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
      .collect(toList());


  private MediaTypeUtils() {}

  /**
   * @return the {@link List} of {@link MediaType} that are considered as {@link String} representable
   */
  public static List<MediaType> getStringRepresentableMimeTypes() {
    return unmodifiableList(STRING_REPRESENTABLE_MIME_TYPES);
  }

  /**
   * Utility method which given a {@link MediaType} will verify if this one is {@link String} representable
   *
   * @param mediaType to verify
   * @return boolean indicating whether the {@link MediaType} is String representable or not
   */
  public static boolean isStringRepresentable(MediaType mediaType) {
    return mediaType.getPrimaryType().equals(TEXT) || STRING_REPRESENTABLE_MIME_TYPES
        .stream()
        .anyMatch(type -> type.matches(mediaType));
  }
}
