/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.metadata;

import org.mule.runtime.api.message.MuleMessage;

import java.io.InputStream;
import java.io.Serializable;
import java.util.Collection;

/**
 * Defines a Java type and its association with additional information about the data, like MIME
 * type and encoding.
 * <p>
 * The java type may be a {@link Collection}, in which case implementations should provide
 * additional information about the item type.
 * <p>
 * This also provides constants for some commonly used {@link DataType}s.
 *
 * @since 1.0
 */
public interface DataType<T> extends Serializable
{

    /**
     * Provides a builder to create {@link DataType} objects.
     * 
     * @return a new {@link DataTypeBuilder}.
     */
    static DataTypeBuilder builder()
    {
        return DataTypeBuilderFactory.getInstance().builder();
    }

    /**
     * Shortcut to create {@link DataType} objects.
     * 
     * @param type the java type to set.
     * @return a new {@link DataTypeBuilder} with the given {@code javaType} already set..
     */
    static <T> DataType<T> forJavaType(Class<T> type)
    {
        return builder().type(type).build();
    }

    DataType<String> TEXT_STRING = builder().type(String.class).mimeType(MimeType.TEXT).build();
    DataType<String> XML_STRING = builder().type(String.class).mimeType(MimeType.XML).build();
    DataType<String> JSON_STRING = builder().type(String.class).mimeType(MimeType.APPLICATION_JSON).build();
    DataType<String> HTML_STRING = builder().type(String.class).mimeType(MimeType.HTML).build();
    DataType<String> ATOM_STRING = builder().type(String.class).mimeType(MimeType.ATOM).build();
    DataType<String> RSS_STRING = builder().type(String.class).mimeType(MimeType.RSS).build();

    //Common Java types
    DataType<String> STRING = forJavaType(String.class);
    DataType<Number> NUMBER = forJavaType(Number.class);
    DataType<Boolean> BOOLEAN = forJavaType(Boolean.class);
    DataType<Object> OBJECT = forJavaType(Object.class);
    DataType<byte[]> BYTE_ARRAY = forJavaType(byte[].class);
    DataType<InputStream> INPUT_STREAM = forJavaType(InputStream.class);
    DataType<MuleMessage> MULE_MESSAGE = builder().type(MuleMessage.class).mimeType(MimeType.ANY).build();
    DataType<Collection<MuleMessage>> MULE_MESSAGE_COLLECTION =
            builder().collectionType(Collection.class).itemType(MuleMessage.class).mimeType(MimeType.ANY).build();

    /**
     * The object type of the source object to transform.
     *
     * @return the class object of the source object. This must not be null
     */
    Class<T> getType();

    /**
     * TODO MULE-9958 return a {@link MimeType}
     * <p>
     * The mime type of the the source object to transform.
     *
     * @return the mime type of the source object.
     */
    String getMimeType();

    /**
     * TODO MULE-9958 remove this, this will be moved to {@link MimeType}
     * <p>
     * The encoding for the object to transform
     */
    String getEncoding();

    /**
     * Used to determine if this data type is compatible with the data type passed in.  This checks to see if the mime types are
     * equal and whether the Java types are assignable
     *
     * @param dataType the dataType object to compare with
     * @return true if the mime types are the same and this type can be assigned to the dataType.type.
     */
    boolean isCompatibleWith(DataType dataType);

}
