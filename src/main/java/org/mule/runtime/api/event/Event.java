/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.event;

import org.mule.runtime.api.el.BindingContext;
import org.mule.runtime.api.message.Error;
import org.mule.runtime.api.message.Message;
import org.mule.runtime.api.metadata.DataType;
import org.mule.runtime.api.metadata.TypedValue;
import org.mule.runtime.api.security.SecurityContext;
import org.mule.runtime.internal.event.EventBuilderImplementation;

import java.util.Map;
import java.util.Optional;

/**
 * Context of information related to the execution of the components.
 * <p/>
 * Instances of this interface can be build using {@link Event#builder()} static method.
 * <p/>
 * 
 * @since 1.0
 */
public interface Event {

  /**
   * Returns the variables in the event
   *
   * @return a map of {@link TypedValue} containing the variable's names and values.
   */
  Map<String, TypedValue<?>> getVariables();

  /**
   * Returns the properties in the current event
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

  /**
   * @return the context applicable to all events created from the same root {@link Event} from a source.
   */
  EventContext getContext();

  /**
   * The security context for this session. If not null outbound, inbound and/or method invocations will be authenticated using
   * this context
   *
   * @return the context for this session or null if the request is not secure.
   */
  SecurityContext getSecurityContext();

  /**
   * The returned value will depend on the source that created this event, and the flow that is executing the event.
   *
   * @return the correlation id to use for this event.
   */
  String getCorrelationId();

  /**
   * Creates a {@link BindingContext} from the event.
   * 
   * @return the event context.SourceWithFlowInfoTestCase
   */
  BindingContext asBindingContext();

  static Builder builder() {
    return new EventBuilderImplementation();
  }

  /**
   * Builder interface for {@link Event}.
   * 
   * @since 1.0
   */
  interface Builder {

    /**
     * Sets the configuration provided by the {@link org.mule.runtime.api.event.Event} into the builder
     *
     * @param event the event to get the data from
     * @return the builder instance
     */
    Builder from(org.mule.runtime.api.event.Event event);

    /**
     * Set the {@link Message} to construct {@link Event} with.
     *
     * @param message the message instance.
     * @return the builder instance
     */
    Builder message(Message message);

    /**
     * Set a map of variables. Any existing variables added to the builder will be removed.
     *
     * @param variables variables to be set.
     * @return the builder instance
     */
    Builder variables(Map<String, ?> variables);

    /**
     * Add a variable.
     *
     * @param key the key of the variable to add.
     * @param value the value of the variable to add. {@code null} values are supported.
     * @return the builder instance.
     */
    Builder addVariable(String key, Object value);

    /**
     * Add a variable.
     *
     * @param key the key of the variable to add.
     * @param value the value of the variable to add. {@code null} values are supported.
     * @param mediaType additional metadata about the {@code value} type.
     * @return the builder instance
     */
    Builder addVariable(String key, Object value, DataType mediaType);

    /**
     * Remove a variable.
     *
     * @param key the variable key.
     * @return the builder instance
     */
    Builder removeVariable(String key);

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
    Builder properties(Map<String, Object> properties);

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
    Builder parameters(Map<String, Object> parameters);

    /**
     * Add a parameter.
     *
     * @param key the key of the parameter to add.
     * @param value the value of the variable to add. {@code null} values are supported.
     * @return the builder instance.
     */
    Builder addParameter(String key, Object value);

    /**
     * Add a parameter.
     *
     * @param key the key of the parameter to add.
     * @param value the value of the parameter to add. {@code null} values are supported.
     * @param dataType additional metadata about the {@code value} type.
     * @return the builder instance
     */
    Builder addParameter(String key, Object value, DataType dataType);

    /**
     * Remove a parameter.
     * <p>
     *
     * @see #parameters(Map)
     *
     * @param key the parameter key.
     * @return the builder instance
     */
    Builder removeParameter(String key);

    /**
     * Sets an error related to the produced event.
     *
     * @param error the error associated with the produced event
     * @return the builder instance
     */
    Builder error(Error error);

    /**
     * Build a new {@link Event} based on the state configured in the {@link Builder}.
     *
     * @return new {@link Event} instance.
     */
    Event build();

  }

}
