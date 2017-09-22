/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.component.execution;

import org.mule.runtime.api.event.Event;
import org.mule.runtime.api.message.Error;
import org.mule.runtime.api.message.Message;
import org.mule.runtime.api.metadata.DataType;
import org.mule.runtime.api.metadata.TypedValue;
import org.mule.runtime.internal.event.DefaultInputEvent;

import java.util.Map;
import java.util.Optional;

/**
 * Interface for defining the content of an event.
 * <p/>
 * {@link InputEvent} can be used to trigger the execution of processor chains programmatically.
 * 
 * @since 1.0
 */
public interface InputEvent {

  /**
   * @return a brand new {@link InputEvent}
   */
  static InputEvent create() {
    return new DefaultInputEvent();
  }

  /**
   * @param event an {@link Event} to use as source of data
   * @return a new {@link InputEvent} with the content of an existent {@link Event}
   */
  static InputEvent create(Event event) {
    return new DefaultInputEvent(event);
  }

  /**
   * @param inputEvent an {@link InputEvent} to use as source of data
   * @return a new {@link InputEvent} with the content of an existent {@link InputEvent}
   */
  static InputEvent create(InputEvent inputEvent) {
    return new DefaultInputEvent(inputEvent);
  }

  /**
   * Set the {@link Message} to construct {@link Event} with.
   *
   * @param message the message instance.
   * @return the builder instance
   */
  InputEvent message(Message message);

  /**
   * Set a map of variables. Any existing variables added to the builder will be removed.
   *
   * @param variables variables to be set.
   * @return the builder instance
   */
  InputEvent variables(Map<String, ?> variables);

  /**
   * Add a variable.
   *
   * @param key the key of the variable to add.
   * @param value the value of the variable to add. {@code null} values are supported.
   * @return the builder instance.
   */
  InputEvent addVariable(String key, Object value);

  /**
   * Add a variable.
   *
   * @param key the key of the variable to add.
   * @param value the value of the variable to add. {@code null} values are supported.
   * @param dataType additional metadata about the {@code value} type.
   * @return the builder instance
   */
  InputEvent addVariable(String key, Object value, DataType dataType);

  /**
   * Set a map of properties to be consumed within a XML connector operation
   * <p>
   * For every module's <operation/> being consumed in a Mule Application, when being macro expanded, these properties will be
   * feed to it in a new and isolated {@link Event}, so that we can guarantee that for each invocation there's a real variable
   * scoping for them.
   *
   * @param properties properties to be set.
   * @return the builder instance
   * @see #parameters(Map)
   */
  InputEvent properties(Map<String, Object> properties);

  /**
   * Set a map of parameters to be consumed within a XML connector operation
   * <p>
   * For every module's <operation/> being consumed in a Mule Application, when being macro expanded, these parameters will be
   * feed to it in a new and isolated {@link Event}, so that we can guarantee that for each invocation there's a real variable
   * scoping for them.
   *
   * @param parameters parameters to be set.
   * @return the builder instance
   * @see #properties(Map)
   */
  InputEvent parameters(Map<String, Object> parameters);

  /**
   * Add a parameter.
   *
   * @param key the key of the parameter to add.
   * @param value the value of the variable to add. {@code null} values are supported.
   * @return the builder instance.
   */
  InputEvent addParameter(String key, Object value);

  /**
   * Add a parameter.
   *
   * @param key the key of the parameter to add.
   * @param value the value of the parameter to add. {@code null} values are supported.
   * @param dataType additional metadata about the {@code value} type.
   * @return the builder instance
   */
  InputEvent addParameter(String key, Object value, DataType dataType);

  /**
   * Sets an error related to the produced event.
   *
   * @param error the error associated with the produced event
   * @return the builder instance
   */
  InputEvent error(Error error);

  /**
   * Returns the variables in the event
   *
   * @return a map of {@link TypedValue} containing the variable's names and values.
   */
  Map<String, TypedValue<?>> getVariables();

  /**
   * Returns the properties in the event
   *
   * @return a map of {@link TypedValue} containing the properties's names and values.
   */
  Map<String, TypedValue<?>> getProperties();

  /**
   * Returns the parameters in the event
   *
   * @return a map of {@link TypedValue} containing the parameter's names and values.
   */
  Map<String, TypedValue<?>> getParameters();

  /**
   * Returns the message payload for this event
   *
   * @return the message payload for this event
   */
  Message getMessage();

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
