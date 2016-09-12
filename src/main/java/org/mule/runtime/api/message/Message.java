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
import org.mule.runtime.api.metadata.TypedValue;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;

/**
 * Represents a message. The Message is comprised of the payload, (a value and its {@link DataType}) and {@link Attributes}
 * associated with the content.
 *
 * @since 1.0
 */
public interface Message extends Serializable {

  /**
   * Provides a builder to create {@link Message} objects.
   *
   * @return a new {@link Builder}.
   */
  static PayloadBuilder builder() {
    return AbstractMuleMessageBuilderFactory.getDefaultFactory().create();
  }

  /**
   * Provides a builder to create {@link Message} objects based on an existing {@link Message} instance.
   *
   * @param message existing {@link Message} to use as a template to create a new {@link Builder} instance.
   * @return a new {@link Builder} based on the template {@code message} provided.
   */
  static Builder builder(Message message) {
    return AbstractMuleMessageBuilderFactory.getDefaultFactory().create(message);
  }

  /**
   * Gets a {@link TypedValue} with the payload of this message.
   *
   * @param <T> the type of the payload.
   * @return the message payload.
   */
  <T> TypedValue<T> getPayload();

  /**
   * Gets the attributes associated with the Message. The {@code Attributes} attributes object is specific to the connector
   * that was the source of the current message and is used for obtaining message properties or headers if applicable plus
   * additional information that provides context for the current message such as file size, file name and last modified date for
   * FILE, and origin IP address, query parameters etc. for HTTP.
   * <p>
   * If there are no attributes associated with the current message, for example if the source of the message was not a connector,
   * then the attributes with be null.
   *
   * @return attributes associated with the message, or null if none exist.
   */
  Attributes getAttributes();

  interface PayloadBuilder {

    /**
     * Sets a {@code #null} value for the {@link Message}'s payload to be built.
     *
     * @return this builder
     */
    Builder nullPayload();

    /**
     * Sets the value for the {@link Message}'s payload to be built.
     * <p>
     * If a {@link DataType} has previously been set it's {@code type} will be updated to reflect the type of the new
     * {@code content} class while preserving it's {@link MediaType}, unless the new {@code content} type defines it's own
     * {@link MediaType} in which case this will be used instead. See
     * {@link org.mule.runtime.api.metadata.DataTypeBuilder#fromObject(Object)}
     *
     * @param value the message content
     * @return this builder.
     */
    Builder payload(Object value);

    /**
     * Sets the consumable streaming collection value for the {@link Message}'s payload to be built.
     * <p>
     * If a {@link DataType} has previously been set it's {@code type} will be updated to reflect the type of the new
     * {@code content} class while preserving it's {@link MediaType}, unless the new {@code value} type defines it's own
     * {@link MediaType} in which case this will be used instead. See
     * {@link org.mule.runtime.api.metadata.DataTypeBuilder#fromObject(Object)}.
     * <p>
     * If you already have a {@link Collection} instance, use {@link #collectionPayload(Collection, Class)} instead.
     *
     * @param value the iterator for the collection content
     * @param itemType the collection item type
     * @return this builder
     * @throws NullPointerException if the payload is null
     */
    Builder streamPayload(Iterator value, Class<?> itemType);

    /**
     * Sets the collection value for the {@link Message}'s payload to be built.
     * <p>
     * If a {@link DataType} has previously been set it's {@code type} will be updated to reflect the type of the new
     * {@code content} class while preserving it's {@link MediaType}, unless the new {@code value} type defines it's own
     * {@link MediaType} in which case this will be used instead. See
     * {@link org.mule.runtime.api.metadata.DataTypeBuilder#fromObject(Object)}
     *
     * @param value the collection content
     * @param itemType the collection item type
     * @return this builder
     * @throws NullPointerException if the content is null
     */
    Builder collectionPayload(Collection value, Class<?> itemType);

    /**
     * Sets the collection value for the {@link Message}'s payload to be built.
     * <p>
     * If a {@link DataType} has previously been set it's {@code type} will be updated to reflect the type of the new
     * {@code content} class while preserving it's {@link MediaType}, unless the new {@code value} type defines it's own
     * {@link MediaType} in which case this will be used instead. See
     * {@link org.mule.runtime.api.metadata.DataTypeBuilder#fromObject(Object)}
     *
     * @param value the array to use as a collection content
     * @return this builder
     * @throws NullPointerException if the content is null
     */
    Builder collectionPayload(Object[] value);

  }

  interface Builder extends PayloadBuilder {

    /**
     * Sets the {@link MediaType} for the {@link Message} to be built. See {@link DataType#getMediaType()}
     *
     * @param mediaType the mediaType to set
     * @return this builder
     * @throws NullPointerException if the mediaType is null
     */
    Builder mediaType(MediaType mediaType);

    /**
     * Populates the builder from the given {@code value}.
     * <p>
     * This method will get the {@code type}, {@code mimeType} and {@code encoding} from the given {@code value} according to its
     * concrete type.
     *
     * @param value an object instance.
     * @return this builder.
     * @throws NullPointerException if the value is null
     */
    Builder attributes(Attributes value);

    /**
     * Builds a new {@link Message} with the values set in this builder.
     *
     * @return a newly built {@link Message}.
     */
    Message build();

  }

  interface CollectionBuilder extends Builder {

    /**
     * Sets the {@link MediaType} for the collection items in the {@link Message} to be built. See
     * {@link CollectionDataType#getItemDataType()}
     *
     * @param mediaType the mediaType to set
     * @return this builder
     * @throws NullPointerException if the mediaType is null
     */
    CollectionBuilder itemMediaType(MediaType mediaType);

  }

}
