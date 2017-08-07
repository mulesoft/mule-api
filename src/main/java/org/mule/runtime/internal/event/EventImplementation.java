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
import org.mule.runtime.api.metadata.TypedValue;
import org.mule.runtime.api.security.SecurityContext;
import org.mule.runtime.internal.el.BindingContextUtils;

import java.util.Map;
import java.util.Optional;

/**
 * Internal implementation of {@link Event}
 * 
 * @since 1.0
 */
public class EventImplementation implements Event {

  private String correlationId;
  private Message message;
  private Map<String, TypedValue<?>> variables;
  private Map<String, TypedValue<?>> parameters;
  private Map<String, TypedValue<?>> properties;
  private Error error;
  private EventContext eventContext;
  private SecurityContext securityContext;

  public EventImplementation(Message message, Map<String, TypedValue<?>> variables,
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
