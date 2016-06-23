/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.message;

import org.mule.runtime.api.metadata.DataType;

import java.io.Serializable;

/**
 * Represents a message payload. The Message is comprised of
 * the payload itself and properties associated with the payload.
 *
 * @since 1.0
 */
public interface MuleMessage<Payload, Attributes extends Serializable> extends Serializable
{
    /**
     * @return the current message
     */
    Payload getPayload();

    /**
     * Gets the attributes associated with the MuleMessage. The {@code Attributes} attributes object is specifc to
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
     */
    DataType<?> getDataType();
}
