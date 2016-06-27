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
 * Represents any data event occurring in the Mule environment. All data sent or
 * received within the mule environment will be passed between components as an MuleEvent.
 *
 * @see MuleMessage
 * @since 1.0
 */
public interface MuleEvent extends Serializable
{

    /**
     * Every event in the system is assigned a universally unique id (UUID).
     *
     * @return the unique identifier for the event
     */
    String getId();

    /**
     * Returns the flow variable registered under the given {@code kye}
     *
     * @param key the name or key of the variable. This must be non-null.
     * @param <T> the value's generic type
     * @return the variable's value
     */
    <T> T getFlowVariable(String key);

    /**
     * Sets a flow variable value with a default data type
     *
     * @param key   the name or key of the variable. This must be non-null.
     * @param value value for the variable
     */
    void setFlowVariable(String key, Object value);

    /**
     * Sets a flow variable value with a given data type
     *
     * @param key      the name or key of the variable. This must be non-null.
     * @param value    value for the variable
     * @param dataType value's dataType. Not null.
     */
    void setFlowVariable(String key, Object value, DataType dataType);

    /**
     * Removes the flow variable registered under the given {@code key}
     *
     * @param key the name or key of the variable. This must be non-null.
     */
    void removeFlowVariable(String key);

    /**
     * Returns the message payload for this event
     *
     * @param <PAYLOAD>    the generic type of the {@link MuleMessage#getPayload()} value
     * @param <ATTRIBUTES> the generic type of the {@link MuleMessage#getAttributes()} value
     * @return the event's {@link MuleMessage}
     */
    <PAYLOAD, ATTRIBUTES extends Serializable> MuleMessage<PAYLOAD, ATTRIBUTES> getMessage();
}
