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
 * Provides a way to hook behavior around a {@link org.mule.runtime.api.component.TypedComponentIdentifier.ComponentType#FLOW
 * flow}. Implementations may implement the {@link #before(ComponentLocation, Map, InterceptionEvent) before} method, the
 * {@link #around(ComponentLocation, Map, InterceptionEvent, InterceptionAction) around} method and the
 * {@link #after(ComponentLocation, InterceptionEvent, Optional) after} method (by default,
 * {@link #before(ComponentLocation, Map, InterceptionEvent) before} and
 * {@link #after(ComponentLocation, InterceptionEvent, Optional) after} do nothing and
 * {@link #around(ComponentLocation, Map, InterceptionEvent, InterceptionAction) around} just calls
 * {@link InterceptionAction#proceed() proceed} on the action).
 * <p>
 * Each flow for which a {@link FlowInterceptor} is applicable will have its own instance of that {@link FlowInterceptor}. For
 * instance, assuming there is a class {@code MyInterceptor} that implements {@link FlowInterceptor} and 2 flows for which
 * {@code MyInterceptor} is applicable, there will be 2 different instances of {@code MyInterceptor}, one intercepting each of the
 * flows. Since different executions on the same flow will use the same interceptor, it is advisable for implementations to be
 * stateless. State can be simulated by keeping method local variables in the
 * {@link #around(ComponentLocation, Map, InterceptionEvent, InterceptionAction) around} method if required.
 * <p>
 * A flow may have more than one interceptor applied. In that case, all will be applied in a predetermined order, calling the
 * {@link #before(ComponentLocation, Map, InterceptionEvent) before} methods of each,
 * {@link #around(ComponentLocation, Map, InterceptionEvent, InterceptionAction) around} methods which may proceed to the next
 * interceptor or the flow itself and finally the {@link #after(ComponentLocation, InterceptionEvent, Optional) after} methods.
 *
 * @since 1.4
 */
public interface FlowInterceptor {

  /**
   * This method is called before an event is dispached to the intercepted flow. It may modify the event to be dispached via the
   * given {@code event}.
   *
   * @param flowName the name of the flow being intercepted.
   * @param event    an object that contains the state of the event to be dispatched to the flow. It may be modified by calling
   *                 its mutator methods.
   */
  default void before(String flowName, InterceptionEvent event) {};

  /**
   * This method is called between {@link #before(String, InterceptionEvent) before} and
   * {@link #after(String, InterceptionEvent, Optional) after}.
   * <p>
   * If implemented, only by calling {@code action}#{@link InterceptionAction#proceed() proceed()} will the interception chain
   * continue and eventually dispatch the event to the intercepted flow. Otherwise, by calling {@link InterceptionAction#skip()
   * skip()} the interception chain execution will be interrupted and {@link #after(String, InterceptionEvent, Optional) after}
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
   * <li>The flow execution has to be skipped.</li>
   * <li>To perform non-blocking operations in the interception.</li>
   * </ul>
   *
   * @param flowName the name of the flow being intercepted.
   * @param event    an object that contains the state of the event to be dispatched to the flow. It may be modified by calling
   *                 its mutator methods.
   * @param action   when something other than continuing the interception is desired, the corresponding method on this object
   *                 must be called. The methods on this object return a {@link CompletableFuture} that may be used to return from
   *                 this method.
   * @return a non-null {@link CompletableFuture} for modifying the intercepted {@link InterceptionEvent event} after this method
   *         returns.
   */
  default CompletableFuture<InterceptionEvent> around(String flowName, InterceptionEvent event, InterceptionAction action) {
    return action.proceed();
  };

  /**
   * This method is called after the main branch of the flow has run. It may modify the event to be dispatched to the source for
   * creating a response via the given {@code event}.
   * <p>
   * This method will be called after the {@link #around(ComponentLocation, Map, InterceptionEvent, InterceptionAction) around}
   * method has been called.
   * <p>
   * If the flow finishes in error, the {@link #after(ComponentLocation, InterceptionEvent, Optional) after} methods will still be
   * called, with the thrown exception provided in the {@code thrown} parameter.
   * <p>
   * If {@link #before(String, InterceptionEvent) before} throws an {@link Exception}, the interception will be called there, but
   * the {@link #after(String, InterceptionEvent, Optional) afters} of the already called handlers will still be called.
   *
   * @param flowName the name of the flow being intercepted.
   * @param event    the result of the component.
   * @param thrown   the exception thrown by the intercepted component, if any.
   */
  default void after(String flowName, InterceptionEvent event, Optional<Throwable> thrown) {}
}
