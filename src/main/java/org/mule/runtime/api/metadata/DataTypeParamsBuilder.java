/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.metadata;

import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;

public interface DataTypeParamsBuilder<T>
{
    /**
     * Sets the given {@code mimeType} string. See {@link DataType#getMimeType()}.
     * <p>
     * If the MIME type for the given string has a {@code charset} parameter, that will be set as
     * the encoding for the {@link DataType} being built, unless it had been previously set.
     * <p>
     * An encoding set by a call to this method can be overridden by calling
     * {@link #encoding(String)}.
     * 
     * @param mimeType the MIME type string to set. If null or empty, the builder is not changed.
     * @return this builder.
     * @throws IllegalArgumentException if the given MIME type string is invalid.
     */
    DataTypeParamsBuilder<T> mimeType(String mimeType);

    /**
     * Sets the given {@code mimeType}. See {@link DataType#getMimeType()}.
     * <p>
     * If the MIME type for the given string has a {@code charset} parameter, that will be set as
     * the encoding for the {@link DataType} being built, unless it had been previously set.
     * <p>
     * An encoding set by a call to this method can be overridden by calling
     * {@link #encoding(String)}.
     * 
     * @param mimeType the MIME type to set. If null, the builder is not changed.
     * @return this builder.
     */
    DataTypeParamsBuilder<T> mimeType(MediaType mimeType);

    /**
     * Sets the given encoding. See {@link MediaType#getEncoding()}.
     * 
     * @param encoding the encoding to set. If null or empty, the builder is not changed.
     * @return this builder.
     * @throws IllegalCharsetNameException if the {@code encoding} is invalid.
     */
    DataTypeParamsBuilder<T> encoding(String encoding);

    /**
     * Sets the given encoding. See {@link MediaType#getEncoding()}.
     * 
     * @param encoding the encoding to set. If null or empty, the builder is not changed.
     * @return this builder.
     */
    DataTypeParamsBuilder<T> encoding(Charset encoding);

    /**
     * Builds a new {@link DataType} with the values set in this builder.
     * 
     * @return a newly built {@link DataType}.
     */
    DataType<T> build();

}
