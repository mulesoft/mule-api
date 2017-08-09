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
import org.mule.runtime.api.metadata.TypedValue;
import org.mule.runtime.api.security.Authentication;

import java.util.Map;
import java.util.Optional;

/**
 * Context of information related to the execution of the components.
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
   * Returns the authentication information for the event
   * 
   * @return authentication information for the event, may be {@link Optional#empty()}.
   */
  Optional<Authentication> getAuthentication();

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
  <T extends EventContext> T getContext();

  /**
   * Creates a {@link BindingContext} from the event.
   * 
   * @return a {@link BindingContext} created from the event content.
   */
  BindingContext asBindingContext();

}
