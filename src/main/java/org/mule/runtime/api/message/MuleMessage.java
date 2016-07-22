/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.message;

import org.mule.runtime.api.metadata.CollectionDataType;
import org.mule.runtime.api.metadata.DataType;
import org.mule.runtime.api.metadata.MediaType;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;

/**
 * Represents a message payload. The Message is comprised of
 * the payload itself and properties associated with the payload.
 *
 * @since 1.0
 */
public interface MuleMessage extends Serializable
{

    /**
     * Provides a builder to create {@link MuleMessage} objects.
     *
     * @return a new {@link Builder}.
     */
    static PayloadBuilder builder()
    {
        return AbstractMuleMessageBuilderFactory.getDefaultFactory().create();
    }

    /**
     * Provides a builder to create {@link MuleMessage} objects based on an existing {@link MuleMessage} instance.
     *
     * @param message existing {@link MuleMessage} to use as a template to create a new {@link Builder} instance.
     * @return a new {@link Builder} based on the template {@code message} provided.
     */
    static Builder builder(MuleMessage message)
    {
        return AbstractMuleMessageBuilderFactory.getDefaultFactory().create(message);
    }

    /**
     * Create a new {@link MuleMessage instance} with the given payload.
     *
     * @param payload the message payload
     * @return new message instance
     */
    static MuleMessage of(Object payload)
    {
        return builder().payload(payload).build();
    }

    /**
     * @param <T> the payload type.
     * @return the message payload.
     */
    <T> T getPayload();

    /**
     * Gets the attributes associated with the MuleMessage. The {@code Attributes} attributes object is specific to
     * the connector that was the source of the current message and is used for obtaining message properties or headers
     * if applicable plus additional information that provides context for the current message such as file size, file
     * name and last modified date for FILE, and origin ip address, query parameters etc. for HTTP.
     * <p>
     * If there are no attributes associated with the current message, for example if the source of the message was not
     * a connector, then the attributes with be null.
     *
     * @return attributes associated with the message, or null if none exist.
     */
    Attributes getAttributes();

    /**
     * Returns the data type (if any) associated with the message's payload.
     *
     * @return the message {@link DataType}
     */
    DataType getDataType();

    interface PayloadBuilder
    {

        /**
         * Sets a {@code #null} payload value for the {@link MuleMessage} to be built.
         *
         * @return this builder
         */
        Builder nullPayload();

        /**
         * Sets the  payload for the {@link MuleMessage} to be built.
         * <p>
         * If a {@link DataType} has previously been set it's {@code type} will be updated to reflect the type of the
         * new {@code payload} class while preserving it's {@link MediaType}, unless the new {@code payload} type defines
         * it's own {@link MediaType} in which case this will be used instead. See
         * {@link org.mule.runtime.api.metadata.DataTypeBuilder#fromObject(Object)}
         *
         * @param payload the message payload
         * @return this builder.
         */
        Builder payload(Object payload);

        /**
         * Sets the consumable streaming collection payload for the {@link MuleMessage} to be built.
         * <p>
         * If a {@link DataType} has previously been set it's {@code type} will be updated to reflect the type of the
         * new {@code payload} class while preserving it's {@link MediaType}, unless the new {@code payload} type defines
         * it's own {@link MediaType} in which case this will be used instead. See
         * {@link org.mule.runtime.api.metadata.DataTypeBuilder#fromObject(Object)}.
         * <p>
         * If you already have a {@link Collection} instance, use {@link #collectionPayload(Collection, Class)} instead.
         *
         * @param payload the iterator for the collection payload
         * @param itemType the collection item type
         * @return this builder
         * @throws NullPointerException if the payload is null
         */
        Builder streamPayload(Iterator payload, Class<?> itemType);

        /**
         * Sets the collection payload for the {@link MuleMessage} to be built.
         * <p>
         * If a {@link DataType} has previously been set it's {@code type} will be updated to reflect the type of the
         * new {@code payload} class while preserving it's {@link MediaType}, unless the new {@code payload} type defines
         * it's own {@link MediaType} in which case this will be used instead. See
         * {@link org.mule.runtime.api.metadata.DataTypeBuilder#fromObject(Object)}
         *
         * @param payload the collection payload
         * @param itemType the collection item type
         * @return this builder
         * @throws NullPointerException if the payload is null
         */
        Builder collectionPayload(Collection payload, Class<?> itemType);
        
        /**
         * Sets the collection payload for the {@link MuleMessage} to be built.
         * <p>
         * If a {@link DataType} has previously been set it's {@code type} will be updated to reflect the type of the
         * new {@code payload} class while preserving it's {@link MediaType}, unless the new {@code payload} type defines
         * it's own {@link MediaType} in which case this will be used instead. See
         * {@link org.mule.runtime.api.metadata.DataTypeBuilder#fromObject(Object)}
         *
         * @param payload the array to use as a collection payload
         * @param itemType the collection item type
         * @return this builder
         * @throws NullPointerException if the payload is null
         */
        Builder collectionPayload(Object[] payload);
        
    }

    interface Builder extends PayloadBuilder
    {

        /**
         * Sets the {@link MediaType} for the {@link MuleMessage} to be built. See {@link DataType#getMediaType()}
         *
         * @param mediaType the mediaType to set
         * @return this builder
         * @throws NullPointerException if the payload is null
         */
        Builder mediaType(MediaType mediaType);

        /**
         * Populates the builder from the given {@code value}.
         * <p>
         * This method will get the {@code type}, {@code mimeType} and {@code encoding} from the given {@code value}
         * according to its concrete type.
         *
         * @param value an object instance.
         * @return this builder.
         * @throws NullPointerException if the payload is null
         */
        Builder attributes(Attributes value);

        /**
         * Builds a new {@link MuleMessage} with the values set in this builder.
         *
         * @return a newly built {@link MuleMessage}.
         */
        MuleMessage build();

    }

    interface CollectionBuilder extends Builder
    {

        /**
         * Sets the {@link MediaType} for the collection items in the  {@link MuleMessage} to be built.
         * See {@link CollectionDataType#getItemDataType()}
         *
         * @param mediaType the mediaType to set
         * @return this builder
         * @throws NullPointerException if the payload is null
         */
        CollectionBuilder itemMediaType(MediaType mediaType);

    }

}
