/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.message;

import org.mule.runtime.api.metadata.DataType;
import org.mule.runtime.api.metadata.MediaType;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

/**
 * Represents any data event occurring in the Mule environment. All data sent or
 * received within the mule environment will be passed between components as an MuleEvent.
 *
 * @see MuleMessage
 * @since 1.0
 */
public interface MuleEvent extends Serializable {

  /**
   * Every event in the system is assigned a universally unique id (UUID).
   *
   * @return the unique identifier for the event
   */
  String getId();

  /**
   * Returns the flow variable registered under the given {@code key}
   *
   * @param key the name or key of the variable. This must be non-null.
   * @param <T> the value's generic type
   * @return the variable's value
   * @throws java.util.NoSuchElementException if the flow variable does not exist.
   */
  <T> T getFlowVariable(String key);

  /**
   * Gets the data type for a given flow variable
   *
   * @param key the name or key of the variable. This must be non-null.
   * @return the flow variable data type or null if the flow variable does not exist
   * @throws java.util.NoSuchElementException if the flow variable does not exist.
   */
  DataType getFlowVariableDataType(String key);

  /**
   * Returns an immutable {@link Set} of flow variable names.
   *
   * @return the set of names
   */
  Set<String> getFlowVariableNames();

  /**
   * Returns the message for this event
   *
   * @return the event's {@link MuleMessage}
   */
  MuleMessage getMessage();

}
