/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.el;

import static java.util.Collections.emptyMap;
import static java.util.Collections.emptySet;
import static java.util.Objects.requireNonNull;
import static java.util.Optional.empty;
import static org.mule.runtime.api.metadata.DataType.STRING;
import static org.mule.runtime.api.metadata.DataType.fromType;
import static org.slf4j.LoggerFactory.getLogger;

import org.mule.runtime.api.component.location.ComponentLocation;
import org.mule.runtime.api.event.Event;
import org.mule.runtime.api.exception.DefaultMuleException;
import org.mule.runtime.api.exception.MuleException;
import org.mule.runtime.api.message.Error;
import org.mule.runtime.api.message.Message;
import org.mule.runtime.api.metadata.DataType;
import org.mule.runtime.api.metadata.TypedValue;
import org.mule.runtime.api.security.Authentication;
import org.mule.runtime.api.util.LazyValue;
import org.mule.runtime.internal.event.ItemSequenceInfoBindingWrapper;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

import org.slf4j.Logger;

/**
 * Provides a reusable way for creating {@link BindingContext}s.
 *
 * @since 1.0
 */
public class BindingContextUtils {

  private static final Logger LOGGER = getLogger(BindingContextUtils.class);

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

  /**
   * Key for the event parameters binding
   * 
   * @since 1.5.0
   */
  public static final String PARAMS = "params";

  public static final BindingContext NULL_BINDING_CONTEXT = new BindingContext() {

    @Override
    public Collection<ExpressionModule> modules() {
      return emptySet();
    }

    @Override
    public Optional<TypedValue> lookup(String identifier) {
      return empty();
    }

    @Override
    public Collection<String> identifiers() {
      return emptySet();
    }

    @Override
    public Collection<Binding> bindings() {
      return emptySet();
    }
  };

  private static final DataType VARS_DATA_TYPE = DataType.builder()
      .mapType(Map.class)
      .keyType(String.class)
      .valueType(TypedValue.class)
      .build();

  /**
   * {@link DataType} for the event parameters binding
   * 
   * @since 1.5.0
   */
  private static final DataType PARAMS_DATA_TYPE = VARS_DATA_TYPE;
  private static final DataType ITEM_SEQUENCE_INFO_DATA_TYPE = fromType(ItemSequenceInfoBindingWrapper.class);
  private static final DataType MESAGE_DATA_TYPE = fromType(Message.class);
  private static final DataType DATA_TYPE_DATA_TYPE = fromType(DataType.class);
  private static final DataType ERROR_DATA_TYPE = fromType(Error.class);
  private static final DataType AUTH_DATA_TYPE = fromType(Authentication.class);
  private static final DataType FLOW_DATA_TYPE = fromType(FlowVariablesAccessor.class);

  public final static TypedValue EMPTY_VARS = new TypedValue<>(emptyMap(), VARS_DATA_TYPE);
  private final static Supplier<TypedValue> EMPTY_VARS_SUPPLIER = () -> EMPTY_VARS;

  /**
   * {@link TypedValue} to use in the absence of event params.
   * 
   * @since 1.5.0
   */
  public final static TypedValue EMPTY_PARAMS = new TypedValue<>(emptyMap(), PARAMS_DATA_TYPE);

  private final static Supplier<TypedValue> EMPTY_PARAMS_SUPPLIER = () -> EMPTY_PARAMS;

  public static final TypedValue NULL_TYPED_VALUE = new TypedValue<>(null, DataType.OBJECT);
  private static final Supplier<TypedValue> NULL_TYPED_VALUE_SUPPLIER = () -> NULL_TYPED_VALUE;

  private BindingContextUtils() {
    // Nothing to do
  }

  /**
   * Creates a new {@link BindingContext} that contains the bindings from {@code baseContext} and the bindings that belong to the
   * given {@code event}.
   *
   * @param event       the event to build the new bindings for. Not-null.
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
   * @param event       the event to build the new bindings for. Not-null.
   * @param baseContext the context whose copy the event bindings will be added to. Not-null.
   * @return a new {@link BindingContext.Builder} that contains the bindings from {@code baseContext} and the bindings that belong
   *         to the given {@code event}.
   */
  public static BindingContext.Builder addEventBuindingsToBuilder(Event event, BindingContext baseContext) {
    requireNonNull(event);
    requireNonNull(baseContext);

    BindingContext.Builder contextBuilder =
        baseContext == NULL_BINDING_CONTEXT ? BindingContext.builder() : BindingContext.builder(baseContext);

    if (!event.getVariables().isEmpty()) {
      contextBuilder.addBinding(VARS, new LazyValue<>(() -> new TypedValue<>(event.getVariables(), VARS_DATA_TYPE)));
    } else {
      contextBuilder.addBinding(VARS, EMPTY_VARS_SUPPLIER);
    }

    if (event.getParameters().isEmpty()) {
      contextBuilder.addBinding(PARAMS, EMPTY_PARAMS_SUPPLIER);
    } else {
      contextBuilder.addBinding(PARAMS, new LazyValue<>(() -> new TypedValue<>(event.getParameters(), PARAMS_DATA_TYPE)));
    }

    contextBuilder.addBinding(CORRELATION_ID,
                              new LazyValue<>(() -> new TypedValue<>(event.getContext().getCorrelationId(), STRING)));

    if (event.getItemSequenceInfo().isPresent()) {
      contextBuilder.addBinding(ITEM_SEQUENCE_INFO,
                                new LazyValue<>(() -> new TypedValue<>(new ItemSequenceInfoBindingWrapper(event
                                    .getItemSequenceInfo().get()),
                                                                       ITEM_SEQUENCE_INFO_DATA_TYPE)));
    } else {
      contextBuilder.addBinding(ITEM_SEQUENCE_INFO, NULL_TYPED_VALUE_SUPPLIER);
    }

    Message message = event.getMessage();
    contextBuilder
        .addBinding(MESSAGE,
                    new LazyValue<>(() -> new TypedValue<>(event.getError()
                        .map(error -> new MessageWrapper(message, error.getCause(), error.getFailingComponent(),
                                                         error.getDslSource()))
                        .orElseGet(() -> new MessageWrapper(message, null, null)),
                                                           MESAGE_DATA_TYPE)));
    contextBuilder.addBinding(ATTRIBUTES, message.getAttributes());
    contextBuilder.addBinding(PAYLOAD, message.getPayload());
    contextBuilder.addBinding(DATA_TYPE,
                              new LazyValue<>(() -> new TypedValue<>(message.getPayload().getDataType(), DATA_TYPE_DATA_TYPE)));

    if (event.getError().isPresent()) {
      contextBuilder.addBinding(ERROR,
                                new LazyValue<>(() -> new TypedValue<>(event.getError().get(), ERROR_DATA_TYPE)));
    } else {
      contextBuilder.addBinding(ERROR, NULL_TYPED_VALUE_SUPPLIER);
    }
    if (event.getAuthentication().isPresent()) {
      contextBuilder.addBinding(AUTHENTICATION,
                                new LazyValue<>(() -> new TypedValue<>(event.getAuthentication().get(), AUTH_DATA_TYPE)));
    } else {
      contextBuilder.addBinding(AUTHENTICATION, NULL_TYPED_VALUE_SUPPLIER);
    }

    return contextBuilder;
  }

  public static BindingContext.Builder addFlowNameBindingsToBuilder(ComponentLocation location,
                                                                    BindingContext.Builder contextBuilder) {
    return contextBuilder.addBinding(FLOW, () -> new TypedValue<>(new FlowVariablesAccessor(location.getRootContainerName()),
                                                                  FLOW_DATA_TYPE));
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
        .addBinding(MESSAGE, new LazyValue<>(() -> new TypedValue<>(new MessageWrapper(message, null, null), MESAGE_DATA_TYPE)))
        .addBinding(PAYLOAD, message.getPayload())
        .addBinding(ATTRIBUTES, message.getAttributes()).build();
  }

  private static class FlowVariablesAccessor {

    private final String name;

    public FlowVariablesAccessor(String name) {
      this.name = name;
    }

    public String getName() {
      return name;
    }

  }

  private static class MessageWrapper implements Message {

    private static final long serialVersionUID = -8097230480930728693L;

    private final Message message;

    public MessageWrapper(Message message, Throwable exceptionPayload, String location) {
      this.message = new MessageExceptionInfoWrapper(message, exceptionPayload, location);
    }

    public MessageWrapper(Message message, Throwable exceptionPayload, String location, String dslSource) {
      this.message = new MessageExceptionInfoWrapper(message, exceptionPayload, location, dslSource);
    }

    @Override
    public <T> TypedValue<T> getPayload() {
      return message.getPayload();
    }

    @Override
    public <T> TypedValue<T> getAttributes() {
      return message.getAttributes();
    }

    @Override
    public String toString() {
      return message.toString();
    }

  }

  /**
   * This exists only for maintaining backwards compatibility with some expressions that were possible before 4.3
   */
  private static class MessageExceptionInfoWrapper implements Message {

    private static final long serialVersionUID = 5854772290551915468L;

    private static final LazyValue<String> EXCEPTION_PAYLOAD_WARN = new LazyValue<>(() -> {
      String msg =
          "Use 'error.cause' or 'error.failingComponent' instead of 'message.message.exceptionPayload' to get details from an error.";
      LOGGER.warn(msg);
      return msg;
    });

    private final Message message;
    private transient final MuleException exceptionPayload;

    public MessageExceptionInfoWrapper(Message message, Throwable exceptionPayload, String location) {
      this(message, exceptionPayload, location, "");
    }

    public MessageExceptionInfoWrapper(Message message, Throwable exceptionPayload, String location, String dslSource) {
      this.message = message;

      if (exceptionPayload == null || exceptionPayload instanceof MuleException) {
        this.exceptionPayload = (MuleException) exceptionPayload;
      } else {
        this.exceptionPayload = new DefaultMuleException(exceptionPayload.getMessage(), exceptionPayload);
        this.exceptionPayload.getExceptionInfo().setLocation(location);
        this.exceptionPayload.getExceptionInfo().setDslSource(dslSource);
      }
    }

    @Override
    public <T> TypedValue<T> getPayload() {
      return message.getPayload();
    }

    @Override
    public <T> TypedValue<T> getAttributes() {
      return message.getAttributes();
    }

    /**
     * @deprecated since 1.3, use the `error` binding instead.
     */
    @Deprecated
    public Throwable getExceptionPayload() {
      EXCEPTION_PAYLOAD_WARN.get();
      return exceptionPayload;
    }

    @Override
    public String toString() {
      return message.toString();
    }

  }

}
