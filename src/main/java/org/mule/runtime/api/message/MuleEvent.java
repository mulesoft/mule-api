/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.message;

import org.mule.runtime.api.metadata.DataType;

import java.io.Serializable;
import java.util.Optional;
import java.util.Set;

/**
 * Represents any data event occurring in the Mule environment. All data sent or
 * received within the mule environment will be passed between components as an MuleEvent.
 *
 * @see Message
 * @since 1.0
 *
 * TODO MULE-10487 Remove MuleEvent from mule-api module
 */
public interface MuleEvent extends Serializable {

  /**
   * Returns the variable registered under the given {@code key}
   *
   * @param key the name or key of the variable. This must be non-null.
   * @param <T> the value's generic type
   * @return the variable's value
   * @throws java.util.NoSuchElementException if the flow variable does not exist.
   */
  <T> T getVariable(String key);

  /**
   * Gets the data type for a given variable
   *
   * @param key the name or key of the variable. This must be non-null.
   * @return the variable data type or null if the variable does not exist
   * @throws java.util.NoSuchElementException if the variable does not exist.
   */
  DataType getVariableDataType(String key);

  /**
   * Returns an immutable {@link Set} of variable names.
   *
   * @return the set of names
   */
  Set<String> getVariableNames();

  /**
   * Returns the message for this event
   *
   * @return the event's {@link Message}
   */
  Message getMessage();

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
   * @return an optional of the error associated with the event. Will be empty if there's no error associated with the event.
   */
  Optional<Error> getError();

}
