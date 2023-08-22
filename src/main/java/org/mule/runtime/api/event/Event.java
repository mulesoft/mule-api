/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.event;

import org.mule.api.annotation.NoImplement;
import org.mule.runtime.api.el.BindingContext;
import org.mule.runtime.api.message.Error;
import org.mule.runtime.api.message.ItemSequenceInfo;
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
@NoImplement
public interface Event {

  /**
   * Returns the variables in the event
   *
   * @return a map of {@link TypedValue} containing the variable's names and values.
   */
  Map<String, TypedValue<?>> getVariables();

  /**
   * The event parameters are set when a Mule operation is executed.
   * <p>
   * These parameters do not escape the scope of the executed operation. If in turn the operation invokes another one, the second
   * operation's event will only contain its own parameters and will have no visibility to the original ones.
   * <p>
   * This map is always empty for an event circulating through a traditional flow.
   *
   * @return a map of {@link TypedValue} containing the parameter's names and values.
   * @since 1.5.0
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
   * <p>
   * To avoid losing the error field after the error handler the user can define a variable pointing to the error field.
   *
   * @return an optional of the error associated with the event. Will be empty if there's no error associated with the event.
   */
  Optional<Error> getError();

  /**
   * The returned value will depend on the flow source that created this event, and the flow that is executing the event.
   *
   * @return the correlation id to use for this event.
   */
  String getCorrelationId();

  /**
   * Returns the sequence metadata of this event. See {@link ItemSequenceInfo}.
   * <p/>
   * The value can be an {@link Optional#empty()} meaning that this event does not belong to any specific sequence.
   *
   * @return the item sequence metadata of this event.
   */
  Optional<ItemSequenceInfo> getItemSequenceInfo();

  /**
   * @return the context applicable to all events created from the same root {@link Event} from a source.
   */
  EventContext getContext();

  /**
   * Creates a {@link BindingContext} from the event.
   *
   * @return a {@link BindingContext} created from the event content.
   */
  BindingContext asBindingContext();
}
