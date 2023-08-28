/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.interception;

import org.mule.runtime.api.component.location.ComponentLocation;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * Provides a way to hook behavior around a component that is not a
 * {@link org.mule.runtime.api.component.TypedComponentIdentifier.ComponentType#SOURCE SOURCE}. Implementations may implement the
 * {@link #before(ComponentLocation, Map, InterceptionEvent) before} method, the
 * {@link #around(ComponentLocation, Map, InterceptionEvent, InterceptionAction) around} method and the
 * {@link #after(ComponentLocation, InterceptionEvent, Optional) after} method (by default,
 * {@link #before(ComponentLocation, Map, InterceptionEvent) before} and
 * {@link #after(ComponentLocation, InterceptionEvent, Optional) after} do nothing and
 * {@link #around(ComponentLocation, Map, InterceptionEvent, InterceptionAction) around} just calls
 * {@link InterceptionAction#proceed() proceed} on the action).
 * <p>
 * Interceptable components are those that are defined by a configuration element and have a {@link ComponentLocation}.
 * <p>
 * Each component for which a {@link ProcessorInterceptor} is applicable will have its own instance of that
 * {@link ProcessorInterceptor}. For instance, assuming there is a class {@code MyInterceptor} that implements
 * {@link ProcessorInterceptor} and 2 different components for which {@code MyInterceptor} is applicable, there will be 2
 * different instances of {@code MyInterceptor}, one intercepting each of the component. Since different executions on the same
 * processor will use the same interceptor, it is advisable for implementations to be stateless. State can be simulated by keeping
 * method local variables in the {@link #around(ComponentLocation, Map, InterceptionEvent, InterceptionAction) around} method if
 * required.
 * <p>
 * A component may have more than one interceptor applied. In that case, all will be applied in a predetermined order, calling the
 * {@link #before(ComponentLocation, Map, InterceptionEvent) before} methods of each,
 * {@link #around(ComponentLocation, Map, InterceptionEvent, InterceptionAction) around} methods which may proceed to the next
 * interceptor or the component itself and finally the {@link #after(ComponentLocation, InterceptionEvent, Optional) after}
 * methods.
 *
 * @since 1.0
 */
public interface ProcessorInterceptor {

  /**
   * This method is called before the intercepted component has run. It may modify the event to be used down in the chain and the
   * component via the given {@code event}.
   *
   * @param location   the location and identification properties of the intercepted component in the mule app configuration.
   * @param parameters the parameters of the component as defined in the configuration. All the values are lazily evaluated so
   *                   they will be calculated when {@link ProcessorParameterValue#resolveValue()} method gets invoked.
   * @param event      an object that contains the state of the event to be sent to the component. It may be modified by calling
   *                   its mutator methods.
   */
  default void before(ComponentLocation location, Map<String, ProcessorParameterValue> parameters, InterceptionEvent event) {};

  /**
   * This method is called between {@link #before(ComponentLocation, Map, InterceptionEvent) before} and
   * {@link #after(ComponentLocation, InterceptionEvent, Optional) after}.
   * <p>
   * If implemented, only by calling {@code action}#{@link InterceptionAction#proceed() proceed()} will the interception chain
   * continue and eventually call the intercepted component. Otherwise, by calling {@link InterceptionAction#skip() skip()} the
   * interception chain execution will be interrupted and {@link #after(ComponentLocation, InterceptionEvent, Optional) after}
   * method called immediately. ({@link InterceptionAction#skip() skip()} may not be called at all, but it is convenient that it
   * already returns a {@link CompletableFuture} to return in this method.)
   * <p>
   * Calling an implementation with this method will be less efficient than calling just
   * {@link #before(ComponentLocation, Map, InterceptionEvent) before} and
   * {@link #after(ComponentLocation, InterceptionEvent, Optional) after}. So,
   * {@link #around(ComponentLocation, Map, InterceptionEvent, InterceptionAction) around} should only be implemented for cases
   * that cannot be done just with {@link #before(ComponentLocation, Map, InterceptionEvent) before} and/or
   * {@link #after(ComponentLocation, InterceptionEvent, Optional) after}. Some scenarios where implementing this method is needed
   * are:
   * <ul>
   * <li>The rest of the chain and the component have to be skipped.</li>
   * <li>To perform non-blocking operations in the interception.</li>
   * </ul>
   *
   * @param location   the location and identification properties of the intercepted component in the mule app configuration.
   * @param parameters the parameters of the component as defined in the configuration. All the values are lazily evaluated so
   *                   they will be calculated when {@link ProcessorParameterValue#resolveValue()} method gets invoked.
   * @param event      an object that contains the state of the event to be sent to the component. It may be modified by calling
   *                   its mutator methods.
   * @param action     when something other than continuing the interception is desired, the corresponding method on this object
   *                   must be called. The methods on this object return a {@link CompletableFuture} that may be used to return
   *                   from this method.
   * @return a non-null {@link CompletableFuture} for modifying the intercepted {@link InterceptionEvent event} after this method
   *         returns.
   */
  default CompletableFuture<InterceptionEvent> around(ComponentLocation location, Map<String, ProcessorParameterValue> parameters,
                                                      InterceptionEvent event, InterceptionAction action) {
    return action.proceed();
  };

  /**
   * This method is called after the intercepted component has run. It may modify the event to be used down in the chain and the
   * component via the given {@code event}.
   * <p>
   * This method will be called after the {@link #around(ComponentLocation, Map, InterceptionEvent, InterceptionAction) around}
   * method has been called.
   * <p>
   * If the intercepted component throws an {@link Exception}, the {@link #after(ComponentLocation, InterceptionEvent, Optional)
   * after} methods will still be called, with the thrown exception provided in the {@code thrown} parameter.
   * <p>
   * If {@link #before(ComponentLocation, Map, InterceptionEvent) before} throws an {@link Exception}, the interception will be
   * called there, but the {@link #after(ComponentLocation, InterceptionEvent, Optional) afters} of the already called handlers
   * will still be called.
   *
   * @param location the location and identification properties of the intercepted component in the mule app configuration.
   * @param event    the result of the component.
   * @param thrown   the exception thrown by the intercepted component, if any.
   */
  default void after(ComponentLocation location, InterceptionEvent event, Optional<Throwable> thrown) {}

  /**
   * Determines if the intercepted component requires the error mapping to be performed.
   *
   * @param location the location and identification properties of the intercepted component in the mule app configuration.
   * @return {@code true} if the error mapping is required, {@code false} otherwise.
   */
  default boolean isErrorMappingRequired(ComponentLocation location) {
    return false;
  }
}
