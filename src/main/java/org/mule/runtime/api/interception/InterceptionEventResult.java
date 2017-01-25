/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.interception;

import org.mule.runtime.api.message.Error;
import org.mule.runtime.api.message.Message;
import org.mule.runtime.api.metadata.DataType;
import org.mule.runtime.api.metadata.TypedValue;

import java.util.Optional;
import java.util.Set;

/**
 * Provides access to the attributes of the input event of a component.
 *
 * @since 1.0
 */
public interface InterceptionEventResult {

  /**
   * Returns the message payload for this event
   * 
   * @return the message payload for this event
   */
  Message getMessage();

  /**
   * Returns an immutable {@link Set} of variable names.
   *
   * @return the set of names
   */
  Set<String> getVariableNames();

  /**
   * Returns the variable registered under the given {@code key}
   *
   * @param key the name or key of the variable. This must be non-null.
   * @param <T> the type of the variable value.
   * @return a {@link TypedValue} containing the variable's value and {@link DataType}
   * @throws java.util.NoSuchElementException if the flow variable does not exist.
   */
  <T> TypedValue<T> getVariable(String key);

  /**
   * When a mule component throws an error then an {@code Error} object gets generated with all the data associated to the error.
   *
   * This field will only contain a value within the error handler defined to handle errors. After the error handler is executed
   * the event error field will be cleared. If another flow is called from within the error handler the flow will still have
   * access to the error field.
   *
   * To avoid losing the error field after the error handler the user can define a variable pointing to the error field.
   *
   * @return an optional of the error associated with the event. Will be empty if there's no error associated with the event.
   */
  Optional<Error> getError();

}
