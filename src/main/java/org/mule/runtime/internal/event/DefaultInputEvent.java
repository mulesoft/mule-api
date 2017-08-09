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
import org.mule.runtime.api.event.InputEvent;
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

  private Message message;
  private Map<String, TypedValue<?>> variables = new HashMap<>();
  private Map<String, TypedValue<?>> parameters = new HashMap<>();
  private Map<String, TypedValue<?>> properties = new HashMap<>();
  private Error error;

  public DefaultInputEvent() {}

  public DefaultInputEvent(Event event) {
    this.message = event.getMessage();
    this.variables = event.getVariables();
    this.parameters = event.getParameters();
    this.properties = event.getProperties();
    this.error = event.getError().orElse(null);
  }

  public DefaultInputEvent(InputEvent inputEvent) {
    this.message = inputEvent.getMessage();
    this.variables = inputEvent.getVariables();
    this.parameters = inputEvent.getParameters();
    this.properties = inputEvent.getProperties();
    this.error = inputEvent.getError().orElse(null);
  }

  @Override
  public InputEvent message(Message message) {
    this.message = message;
    return new DefaultInputEvent(this);
  }

  @Override
  public InputEvent variables(Map<String, ?> variables) {
    variables.forEach((key, value) -> {
      this.variables.put(key, valueAsTypedValue(value));
    });
    return new DefaultInputEvent(this);
  }

  private TypedValue<?> valueAsTypedValue(Object value) {
    return value instanceof TypedValue ? (TypedValue<?>) value : TypedValue.of(value);
  }

  @Override
  public InputEvent addVariable(String key, Object value) {
    variables.put(key, valueAsTypedValue(value));
    return new DefaultInputEvent(this);
  }

  @Override
  public InputEvent addVariable(String key, Object value, DataType dataType) {
    variables.put(key, new TypedValue<>(value, dataType));
    return new DefaultInputEvent(this);
  }

  @Override
  public InputEvent properties(Map<String, Object> properties) {
    properties.putAll(properties);
    return new DefaultInputEvent(this);
  }

  @Override
  public InputEvent parameters(Map<String, Object> parameters) {
    parameters.putAll(parameters);
    return new DefaultInputEvent(this);
  }

  @Override
  public InputEvent addParameter(String key, Object value) {
    parameters.put(key, valueAsTypedValue(value));
    return new DefaultInputEvent(this);
  }

  @Override
  public InputEvent addParameter(String key, Object value, DataType dataType) {
    parameters.put(key, new TypedValue<>(value, dataType));
    return new DefaultInputEvent(this);
  }

  @Override
  public InputEvent error(Error error) {
    this.error = error;
    return new DefaultInputEvent(this);
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
