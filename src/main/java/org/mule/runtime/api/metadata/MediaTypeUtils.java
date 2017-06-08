/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.metadata;

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

  public static final List<MediaType> STRING_REPRESENTABLE_MIME_TYPES = Stream.of(
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
                                                                                  "text/calendar",
                                                                                  "text/css",
                                                                                  "text/csv",
                                                                                  "text/json",
                                                                                  "text/html",
                                                                                  "text/plain")
      .map(MediaType::parse)
      .collect(toList());


  private MediaTypeUtils() {}
}
