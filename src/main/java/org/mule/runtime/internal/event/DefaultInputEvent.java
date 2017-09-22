/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.internal.event;

import static java.util.Collections.unmodifiableMap;
import static java.util.Optional.ofNullable;
import org.mule.runtime.api.event.Event;
import org.mule.runtime.api.component.execution.InputEvent;
import org.mule.runtime.api.message.Error;
import org.mule.runtime.api.message.Message;
import org.mule.runtime.api.metadata.DataType;
import org.mule.runtime.api.metadata.TypedValue;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Default implementation for {@link InputEvent}.
 * 
 * @since 1.0
 */
public class DefaultInputEvent implements InputEvent {

  private Message message = Message.builder().nullValue().build();
  private Map<String, TypedValue<?>> variables = new HashMap<>();
  private Map<String, TypedValue<?>> parameters = new HashMap<>();
  private Map<String, TypedValue<?>> properties = new HashMap<>();
  private Error error;

  public DefaultInputEvent() {}

  public DefaultInputEvent(Event event) {
    this.message = event.getMessage();
    this.variables.putAll(event.getVariables());
    this.parameters.putAll(event.getParameters());
    this.properties.putAll(event.getProperties());
    this.error = event.getError().orElse(null);
  }

  public DefaultInputEvent(InputEvent inputEvent) {
    this.message = inputEvent.getMessage();
    this.variables.putAll(inputEvent.getVariables());
    this.parameters.putAll(inputEvent.getParameters());
    this.properties.putAll(inputEvent.getProperties());
    this.error = inputEvent.getError().orElse(null);
  }

  @Override
  public InputEvent message(Message message) {
    DefaultInputEvent inputEvent = new DefaultInputEvent(this);
    inputEvent.message = message;
    return inputEvent;
  }

  @Override
  public InputEvent variables(Map<String, ?> variables) {
    DefaultInputEvent inputEvent = new DefaultInputEvent(this);
    inputEvent.variables.clear();
    variables.forEach((key, value) -> {
      this.variables.put(key, valueAsTypedValue(value));
    });
    return inputEvent;
  }

  private TypedValue<?> valueAsTypedValue(Object value) {
    return value instanceof TypedValue ? (TypedValue<?>) value : TypedValue.of(value);
  }

  @Override
  public InputEvent addVariable(String key, Object value) {
    DefaultInputEvent inputEvent = new DefaultInputEvent(this);
    inputEvent.variables.put(key, valueAsTypedValue(value));
    return inputEvent;
  }

  @Override
  public InputEvent addVariable(String key, Object value, DataType dataType) {
    DefaultInputEvent inputEvent = new DefaultInputEvent(this);
    inputEvent.variables.put(key, valueAsTypedValue(value));
    return inputEvent;
  }

  @Override
  public InputEvent properties(Map<String, Object> properties) {
    DefaultInputEvent inputEvent = new DefaultInputEvent(this);
    inputEvent.properties.clear();
    properties.forEach((key, value) -> {
      this.properties.put(key, valueAsTypedValue(value));
    });
    return inputEvent;
  }

  @Override
  public InputEvent parameters(Map<String, Object> parameters) {
    DefaultInputEvent inputEvent = new DefaultInputEvent(this);
    inputEvent.parameters.clear();
    parameters.forEach((key, value) -> {
      this.parameters.put(key, valueAsTypedValue(value));
    });
    return inputEvent;
  }

  @Override
  public InputEvent addParameter(String key, Object value) {
    DefaultInputEvent inputEvent = new DefaultInputEvent(this);
    inputEvent.parameters.put(key, valueAsTypedValue(value));
    return inputEvent;
  }

  @Override
  public InputEvent addParameter(String key, Object value, DataType dataType) {
    DefaultInputEvent inputEvent = new DefaultInputEvent(this);
    inputEvent.parameters.put(key, new TypedValue<>(value, dataType));
    return inputEvent;
  }

  @Override
  public InputEvent error(Error error) {
    DefaultInputEvent inputEvent = new DefaultInputEvent(this);
    inputEvent.error = error;
    return inputEvent;
  }

  @Override
  public Map<String, TypedValue<?>> getVariables() {
    return unmodifiableMap(variables);
  }

  @Override
  public Map<String, TypedValue<?>> getParameters() {
    return unmodifiableMap(parameters);
  }

  @Override
  public Map<String, TypedValue<?>> getProperties() {
    return unmodifiableMap(properties);
  }

  @Override
  public Message getMessage() {
    return message;
  }

  @Override
  public Optional<Error> getError() {
    return ofNullable(error);
  }

}
