/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.internal.event;

import org.mule.runtime.api.event.Event;
import org.mule.runtime.api.event.EventContext;
import org.mule.runtime.api.message.Error;
import org.mule.runtime.api.message.Message;
import org.mule.runtime.api.metadata.DataType;
import org.mule.runtime.api.metadata.TypedValue;
import org.mule.runtime.api.security.SecurityContext;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Default implementation for {@link Event.Builder}.
 * 
 * @since 1.0
 */
public class EventBuilderImplementation implements Event.Builder {

  private String correlationId;
  private Message message;
  private Map<String, TypedValue<?>> variables = new HashMap<>();
  private Map<String, TypedValue<?>> parameters = new HashMap<>();;
  private Map<String, TypedValue<?>> properties = new HashMap<>();;
  private Error error;
  private EventContext eventContext;
  private SecurityContext securityContext;

  public EventBuilderImplementation() {}

  @Override
  public Event.Builder from(Event event) {
    this.message = event.getMessage();
    this.variables = event.getVariables();
    this.parameters = event.getParameters();
    this.properties = event.getProperties();
    this.error = event.getError().orElse(null);
    this.eventContext = event.getContext();
    this.securityContext = event.getSecurityContext();
    this.correlationId = event.getCorrelationId();
    return this;
  }

  @Override
  public Event.Builder message(Message message) {
    this.message = message;
    return this;
  }

  @Override
  public Event.Builder variables(Map<String, ?> variables) {
    variables.forEach((key, value) -> {
      this.variables.put(key, valueAsTypedValue(value));
    });
    return this;
  }

  private TypedValue<?> valueAsTypedValue(Object value) {
    return value instanceof TypedValue ? (TypedValue<?>) value : TypedValue.of(value);
  }

  @Override
  public Event.Builder addVariable(String key, Object value) {
    variables.put(key, valueAsTypedValue(value));
    return this;
  }

  @Override
  public Event.Builder addVariable(String key, Object value, DataType mediaType) {
    variables.put(key, new TypedValue<>(value, mediaType));
    return this;
  }

  @Override
  public Event.Builder removeVariable(String key) {
    variables.remove(key);
    return this;
  }

  @Override
  public Event.Builder properties(Map<String, Object> properties) {
    properties.putAll(properties);
    return this;
  }

  @Override
  public Event.Builder parameters(Map<String, Object> parameters) {
    parameters.putAll(parameters);
    return this;
  }

  @Override
  public Event.Builder addParameter(String key, Object value) {
    parameters.put(key, valueAsTypedValue(value));
    return this;
  }

  @Override
  public Event.Builder addParameter(String key, Object value, DataType dataType) {
    parameters.put(key, new TypedValue<>(value, dataType));
    return this;
  }

  @Override
  public Event.Builder removeParameter(String key) {
    parameters.remove(key);
    return this;
  }

  @Override
  public Event.Builder error(Error error) {
    this.error = error;
    return this;
  }

  @Override
  public Event build() {
    return new EventImplementation(message, variables, parameters, properties, Optional.ofNullable(error), eventContext,
                                   securityContext, correlationId);
  }
}
