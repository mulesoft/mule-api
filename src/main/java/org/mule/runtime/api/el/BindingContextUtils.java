/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.el;

import static java.util.Collections.unmodifiableMap;
import static java.util.Objects.requireNonNull;
import static org.mule.runtime.api.metadata.DataType.STRING;
import static org.mule.runtime.api.metadata.DataType.fromType;

import org.mule.runtime.api.event.Event;
import org.mule.runtime.api.message.Error;
import org.mule.runtime.api.message.ItemSequenceInfo;
import org.mule.runtime.api.message.Message;
import org.mule.runtime.api.metadata.DataType;
import org.mule.runtime.api.metadata.TypedValue;
import org.mule.runtime.api.security.Authentication;
import org.mule.runtime.api.util.LazyValue;
import org.mule.runtime.internal.event.ItemSequenceInfoBindingWrapper;

import java.util.Map;

/**
 * Provides a reusable way for creating {@link BindingContext}s.
 *
 * @since 1.0
 */
public class BindingContextUtils {

  public static final String MESSAGE = "message";
  public static final String PAYLOAD = "payload";
  public static final String DATA_TYPE = "dataType";
  public static final String ATTRIBUTES = "attributes";
  public static final String ERROR = "error";
  public static final String CORRELATION_ID = "correlationId";
  public static final String VARS = "vars";
  public static final String AUTHENTICATION = "authentication";
  public static final String FLOW = "flow";
  public static final String ITEM_SEQUENCE_INFO = "itemSequenceInfo";

  public static final BindingContext NULL_BINDING_CONTEXT = BindingContext.builder().build();

  private BindingContextUtils() {
    // Nothing to do
  }

  /**
   * Creates a new {@link BindingContext} that contains the bindings from {@code baseContext} and the bindings that belong to the
   * given {@code event}.
   *
   * @param event the event to build the new bindings for. Not-null.
   * @param baseContext the context whose copy the event bindings will be added to. Not-null.
   * @return a new {@link BindingContext} that contains the bindings from {@code baseContext} and the bindings that belong to the
   *         given {@code event}.
   */
  public static BindingContext addEventBindings(Event event, BindingContext baseContext) {
    return addEventBuindingsToBuilder(event, baseContext).build();
  }

  /**
   * Creates a new {@link BindingContext.Builder} that contains the bindings from {@code baseContext} and the bindings that belong
   * to the given {@code event}.
   *
   * @param event the event to build the new bindings for. Not-null.
   * @param baseContext the context whose copy the event bindings will be added to. Not-null.
   * @return a new {@link BindingContext.Builder} that contains the bindings from {@code baseContext} and the bindings that belong
   *         to the given {@code event}.
   */
  public static BindingContext.Builder addEventBuindingsToBuilder(Event event, BindingContext baseContext) {
    requireNonNull(event);
    requireNonNull(baseContext);

    BindingContext.Builder contextBuilder = BindingContext.builder(baseContext);

    contextBuilder.addBinding(VARS, new LazyValue<>(() -> {
      Map<String, TypedValue<?>> flowVars = unmodifiableMap(event.getVariables());
      return new TypedValue<>(flowVars, DataType.builder()
          .mapType(flowVars.getClass())
          .keyType(String.class)
          .valueType(TypedValue.class)
          .build());
    }));

    contextBuilder.addBinding(CORRELATION_ID,
                              new LazyValue<>(() -> new TypedValue<>(event.getContext().getCorrelationId(), STRING)));

    contextBuilder.addBinding(ITEM_SEQUENCE_INFO,
                              new LazyValue<>(() -> new TypedValue<>(event.getItemSequenceInfo().map(ItemSequenceInfoBindingWrapper::new).orElse(null), fromType(ItemSequenceInfoBindingWrapper.class))));

    Message message = event.getMessage();
    contextBuilder.addBinding(MESSAGE, new LazyValue<>(() -> new TypedValue<>(message, fromType(Message.class))));
    contextBuilder.addBinding(ATTRIBUTES, message.getAttributes());
    contextBuilder.addBinding(PAYLOAD, message.getPayload());
    contextBuilder.addBinding(DATA_TYPE,
                              new LazyValue<>(() -> new TypedValue<>(message.getPayload().getDataType(),
                                                                     fromType(DataType.class))));
    contextBuilder.addBinding(ERROR, new LazyValue<>(() -> {
      Error error = event.getError().isPresent() ? event.getError().get() : null;
      return new TypedValue<>(error, fromType(Error.class));
    }));

    contextBuilder.addBinding(AUTHENTICATION, new LazyValue<>(() -> {
      Authentication authentication = event.getAuthentication().orElse(null);
      return new TypedValue<>(authentication, fromType(Authentication.class));
    }));
    return contextBuilder;
  }

  /**
   * Creates a new {@link BindingContext} that only contains the restricted bindings to be used for evaluating the target
   * expression.
   *
   * @param message the message to build the bindings for. Not-null.
   * @return a new {@link BindingContext} that contains the bindings from {@code message}.
   */
  public static BindingContext getTargetBindingContext(Message message) {
    requireNonNull(message);
    return BindingContext.builder()
        .addBinding(MESSAGE, new LazyValue<>(() -> new TypedValue(message, DataType.fromType(Message.class))))
        .addBinding(PAYLOAD, message.getPayload())
        .addBinding(ATTRIBUTES, message.getAttributes()).build();
  }

}
