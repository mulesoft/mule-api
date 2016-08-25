/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.message;

import java.io.Serializable;
import java.util.Set;

import org.mule.runtime.api.metadata.DataType;

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

  /**
   * When a mule component throws an error then an {@code Error} object
   * gets generated with all the data associated to the error.
   *
   * This field will only contain a value within the error handler defined
   * to handle errors. After the error handler is executed the event error field
   * will be cleared. If another flow is called from within the error handler the flow
   * will still have access to the error field.
   *
   * To avoid losing the error field after the error handler the user can define a variable
   * pointing to the error field.
   *
   * @return the error associated with the event.
   */
  Error getError();

}
