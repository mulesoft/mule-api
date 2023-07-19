/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.interception;

import org.mule.runtime.api.component.location.ComponentLocation;
import org.mule.runtime.api.event.EventContext;

import java.util.Map;
import java.util.Optional;

/**
 * Provides a way to hook behavior around a {@link org.mule.runtime.api.component.TypedComponentIdentifier.ComponentType#SOURCE
 * SOURCE} component. Implementations may implement the {@link #beforeCallback(ComponentLocation, Map, InterceptionEvent)
 * beforeCallback} method and the {@link #afterCallback(ComponentLocation, InterceptionEvent, Optional) afterCallback} method (by
 * default, {@link #beforeCallback(ComponentLocation, Map, InterceptionEvent) beforeCallback} and
 * {@link #afterCallback(ComponentLocation, InterceptionEvent, Optional) afterCallback} do nothing).
 * <p>
 * Interceptable components are those that are defined by a configuration element and have a {@link ComponentLocation}.
 * <p>
 * Each component for which a {@link SourceInterceptor} is applicable will have its own instance of that
 * {@link SourceInterceptor}. For instance, assuming there is a class {@code MyInterceptor} that implements
 * {@link SourceInterceptor} and 2 different components for which {@code MyInterceptor} is applicable, there will be 2 different
 * instances of {@code MyInterceptor}, one intercepting each of the component. Since different executions on the same processor
 * will use the same interceptor, it is advisable for implementations to be stateless.
 * <p>
 * A component may have more than one interceptor applied. In that case, all will be applied in a predetermined order, calling the
 * {@link #beforeCallback(ComponentLocation, Map, InterceptionEvent) beforeCallback} methods of each and finally the
 * {@link #afterCallback(ComponentLocation, InterceptionEvent, Optional) afterCallback} methods.
 *
 * @since 1.2
 */
public interface SourceInterceptor {

  /**
   * This method is called before the intercepted source sends its response after a flow execution is complete. It may modify the
   * event to be used for generating the response via the given {@code event}.
   *
   * @param location   the location and identification properties of the intercepted component in the mule app configuration.
   * @param parameters the parameters of the component as defined in the configuration. All the values are lazily evaluated so
   *                   they will be calculated when {@link ProcessorParameterValue#resolveValue()} method gets invoked.
   * @param event      an object that contains the state of the event to be sent to the component. It may be modified by calling
   *                   its mutator methods.
   */
  default void beforeCallback(ComponentLocation location, Map<String, ProcessorParameterValue> parameters,
                              InterceptionEvent event) {};

  /**
   * This method is called after the intercepted source sends its response after a flow execution is complete.
   * <p>
   * If the intercepted source callback fails, the {@link #afterCallback(ComponentLocation, InterceptionEvent, Optional) after}
   * methods will still be called, with the thrown exception provided in the {@code thrown} parameter.
   * <p>
   * If {@link #beforeCallback(ComponentLocation, Map, InterceptionEvent) before} throws an {@link Exception}, the interception
   * will be called there, but the {@link #afterCallback(ComponentLocation, InterceptionEvent, Optional) afters} of the already
   * called handlers will still be called.
   *
   * @param location the location and identification properties of the intercepted component in the mule app configuration.
   * @param event    the result of the component.
   * @param thrown   the exception thrown by the intercepted component, if any.
   */
  default void afterCallback(ComponentLocation location, InterceptionEvent event, Optional<Throwable> thrown) {}

  /**
   * This method is called when the root {@link EventContext} is terminated (all the event contexts of that event are completed)
   * regardless of whether the events finished successfully, on error, or a combination of both.
   *
   * @param location         the location and identification properties of the intercepted component in the mule app
   *                         configuration.
   * @param rootEventContext of the component result.
   * @since 4.4
   */
  default void afterTerminated(ComponentLocation location, EventContext rootEventContext) {}
}
