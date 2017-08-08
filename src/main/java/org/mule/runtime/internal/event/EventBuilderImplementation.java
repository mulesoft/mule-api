/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.internal.event;

import static java.util.Collections.unmodifiableMap;
import static java.util.Optional.ofNullable;
import static org.mule.runtime.api.util.Preconditions.checkNotNull;
import static org.mule.runtime.internal.el.BindingContextUtils.NULL_BINDING_CONTEXT;
import org.mule.runtime.api.el.BindingContext;
import org.mule.runtime.api.event.Event;
import org.mule.runtime.api.event.EventContext;
import org.mule.runtime.api.message.Error;
import org.mule.runtime.api.message.Message;
import org.mule.runtime.api.metadata.DataType;
import org.mule.runtime.api.metadata.TypedValue;
import org.mule.runtime.api.security.SecurityContext;
import org.mule.runtime.internal.el.BindingContextUtils;

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
  private Map<String, TypedValue<?>> parameters = new HashMap<>();
  private Map<String, TypedValue<?>> properties = new HashMap<>();
  private Error error;
  private EventContext eventContext;
  private SecurityContext securityContext;

  public EventBuilderImplementation() {}

  public EventBuilderImplementation(Event event) {
    this.message = event.getMessage();
    this.variables = event.getVariables();
    this.parameters = event.getParameters();
    this.properties = event.getProperties();
    this.error = event.getError().orElse(null);
    this.eventContext = event.getContext();
    this.securityContext = event.getSecurityContext();
    this.correlationId = event.getCorrelationId();
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
  public Event.Builder addVariable(String key, Object value, DataType dataType) {
    variables.put(key, new TypedValue<>(value, dataType));
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

  /**
   * Internal implementation of {@link Event}
   *
   * @since 1.0
   */
  private static class EventImplementation implements Event {

    private String correlationId;
    private Message message;
    private Map<String, TypedValue<?>> variables;
    private Map<String, TypedValue<?>> parameters;
    private Map<String, TypedValue<?>> properties;
    private Error error;
    private EventContext eventContext;
    private SecurityContext securityContext;

    EventImplementation(Message message, Map<String, TypedValue<?>> variables,
                        Map<String, TypedValue<?>> parameters, Map<String, TypedValue<?>> properties,
                        Optional<Error> error, EventContext eventContext, SecurityContext securityContext,
                        String correlationId) {
      checkNotNull(variables != null, "variables cannot be null");
      checkNotNull(parameters != null, "parameters cannot be null");
      checkNotNull(properties != null, "properties cannot be null");

      this.message = message;
      this.variables = variables;
      this.parameters = parameters;
      this.properties = properties;
      this.error = error.orElse(null);
      this.eventContext = eventContext;
      this.securityContext = securityContext;
      this.correlationId = correlationId;
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

    @Override
    public EventContext getContext() {
      return eventContext;
    }

    @Override
    public SecurityContext getSecurityContext() {
      return securityContext;
    }

    @Override
    public String getCorrelationId() {
      return correlationId;
    }

    @Override
    public BindingContext asBindingContext() {
      return BindingContextUtils.addEventBindings(this, NULL_BINDING_CONTEXT);
    }

  }
}
