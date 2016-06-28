/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.metadata;

import static org.mule.runtime.api.metadata.AbstractDataTypeBuilderFactory.getDefaultFactory;

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
    static <T> DataTypeBuilder<T> builder()
    {
        return getDefaultFactory().create();
    }

    /**
     * Provides a builder to create {@link DataType} objects based on an existing {@link DataType} instance.
     *
     * @param dataType existing {@link DataType} to use as a template to create a new {@link DataTypeBuilder} instance.
     * @return a new {@link DataTypeBuilder} based on the template {@code dataType} provided.
     * */
    static <T> DataTypeBuilder<T> builder(DataType<T> dataType)
    {
        return getDefaultFactory().create(dataType);
    }

    /**
     * Shortcut to create a {@link DataType} using just a Java type. Default values will be used for {@code mimeType}
     * and {@code encoding}.
     * 
     * @param type the Java type to create {@link DataType} for.
     * @return a new {@link DataTypeBuilder} for the given {@code type}.
     */
    static <T> DataType<T> fromType(Class<T> type)
    {
        return builder().type(type).build();
    }

    /**
     * Shortcut to create the {@link DataType} from an Object instance.
     * <p>
     * This behaves in the same way as {@link #fromType(Class)} creating a {@link DataType} based on
     * the value type with default values being used for {@code mimeType} and {@code encoding} if
     * the Object type has no mimeType or encoding. The {@link DataTypeBuilder} used by default may
     * introspect certain types that do contain type this meta-data such as
     * {@link javax.activation.DataHandler} and {@link javax.activation.DataSource} and populate
     * {@code mimeType} and {@code encoding} values based on this.
     *
     * @param value the object to determine the {@link DataType} of.
     * @param <T> the type of the object
     * @return a new {@link DataType} for the given {@code value}.
     */
    static <T> DataType<T> fromObject(T value)
    {
        return getDefaultFactory().<T> create().fromObject(value).build();
    }


    DataType<String> TEXT_STRING = builder().type(String.class).mediaType(MediaType.TEXT).build();
    DataType<String> XML_STRING = builder().type(String.class).mediaType(MediaType.XML).build();
    DataType<String> JSON_STRING = builder().type(String.class).mediaType(MediaType.APPLICATION_JSON).build();
    DataType<String> HTML_STRING = builder().type(String.class).mediaType(MediaType.HTML).build();
    DataType<String> ATOM_STRING = builder().type(String.class).mediaType(MediaType.ATOM).build();
    DataType<String> RSS_STRING = builder().type(String.class).mediaType(MediaType.RSS).build();

    //Common Java types
    DataType<String> STRING = fromType(String.class);
    DataType<Number> NUMBER = fromType(Number.class);
    DataType<Boolean> BOOLEAN = fromType(Boolean.class);
    DataType<Object> OBJECT = fromType(Object.class);
    DataType<byte[]> BYTE_ARRAY = fromType(byte[].class);
    DataType<InputStream> INPUT_STREAM = fromType(InputStream.class);
    DataType<MuleMessage> MULE_MESSAGE = builder().type(MuleMessage.class).mediaType(MediaType.ANY).build();
    DataType<Collection<MuleMessage>> MULE_MESSAGE_COLLECTION =
            getDefaultFactory().<Collection<MuleMessage>> create().collectionType(Collection.class).mediaType(MediaType.ANY).build();

    /**
     * The object type of the source object to transform.
     *
     * @return the class object of the source object. This must not be null
     */
    Class<T> getType();

    /**
     * The mime type of the the source object to transform.
     *
     * @return the mime type of the source object.
     */
    MediaType getMediaType();

    /**
     * Used to determine if this data type is compatible with the data type passed in.  This checks to see if the mime types are
     * equal and whether the Java types are assignable
     *
     * @param dataType the dataType object to compare with
     * @return true if the mime types are the same and this type can be assigned to the dataType.type.
     */
    boolean isCompatibleWith(DataType dataType);

}
