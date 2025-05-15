/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.metadata;

import org.mule.api.annotation.NoImplement;

import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;

/**
 * Provides a way to set the java-type independent attributes when building an immutable {@link DataType} objects.
 * <p>
 * Implementations need not be thread safe.
 *
 * @since 1.0
 */
@NoImplement
public interface DataTypeParamsBuilder {

  /**
   * Sets the given {@code mediaType} string. See {@link DataType#getMediaType()}.
   * <p>
   * If the media type for the given string has a {@code charset} parameter, that will be set as the encoding for the
   * {@link DataType} being built, unless it had been previously set.
   * <p>
   * An encoding set by a call to this method can be overridden by calling {@link #charset(String)}.
   *
   * @param mediaType the MIME type string to set. If null or empty, the builder is not changed.
   * @return this builder.
   * @throws IllegalArgumentException if the given media type string is invalid.
   */
  DataTypeParamsBuilder mediaType(String mediaType);

  /**
   * Sets the given {@code mediaType}. See {@link DataType#getMediaType()}.
   * <p>
   * If the media type for the given string has a {@code charset} parameter, that will be set as the encoding for the
   * {@link DataType} being built, unless it had been previously set.
   * <p>
   * An encoding set by a call to this method can be overridden by calling {@link #charset(String)}.
   *
   * @param mediaType the media type to set. If null, the builder is not changed.
   * @return this builder.
   */
  DataTypeParamsBuilder mediaType(MediaType mediaType);

  /**
   * Sets the given encoding. See {@link MediaType#getCharset()}.
   *
   * @param charset the encoding to set. If null or empty, the builder is not changed.
   * @return this builder.
   * @throws IllegalCharsetNameException if the {@code charset} is invalid.
   */
  DataTypeParamsBuilder charset(String charset);

  /**
   * Sets the given encoding. See {@link MediaType#getCharset()}.
   *
   * @param charset the encoding to set. If null or empty, the builder is not changed.
   * @return this builder.
   */
  DataTypeParamsBuilder charset(Charset charset);

  /**
   * Builds a new {@link DataType} with the values set in this builder.
   *
   * @return a newly built {@link DataType}.
   */
  DataType build();

}
