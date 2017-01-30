/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.runtime.api.interception;

import org.mule.runtime.api.component.ComponentIdentifier;
import org.mule.runtime.api.component.ComponentLocation;
import org.mule.runtime.api.message.Error;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * Provides a way to hook behavior around a component. Implementations may implement the {@link #before(Map, InterceptionEvent)
 * before} method, the {@link #around(Map, InterceptionEvent, InterceptionAction) around} method and the
 * {@link #after(InterceptionEvent) after} method (by default, {@link #before(Map, InterceptionEvent) before} and
 * {@link #after(InterceptionEvent) after} do nothing and {@link #around(Map, InterceptionEvent, InterceptionAction) around} just
 * calls {@link InterceptionAction#proceed() proceed}).
 * <p>
 * Interceptable components are those that are defined by a configuration element and have a {@link ComponentLocation}.
 * <p>
 * A component may have more than one interceptor applied. In that case, all will be applied in a predetermined order, calling the
 * {@link #before(Map, InterceptionEvent) before} methods of each, {@link #around(Map, InterceptionEvent, InterceptionAction)
 * around} methods which may proceed to the next interceptor or the component itself and finally the
 * {@link #after(InterceptionEvent) after} methods.
 *
 * @since 1.0
 */
public interface InterceptionHandler {

  /**
   * Determines if this handler must be applied to a component based on some of its attributes.
   * 
   * @param identifier the identification properties of the to-be intercepted component.
   * @param location the location properties of the to-be intercepted component in the mule app configuration.
   * @return {@code true} if this handler must be applied to the component with the provided parameters, {@code false} otherwise.
   */
  default boolean intercept(ComponentIdentifier identifier, ComponentLocation location) {
    return true;
  }

  /**
   * This method is called before the intercepted component has run. It may modify the event to be used down in the chain and the
   * component via the given {@code event}.
   * 
   * @param parameters the parameters of the component as defined in the configuration. Parameters that contain expressions will
   *        be resolved when passed to this method.
   * @param event an object that contains the state of the event to be sent to the component. It may be modified by calling its
   *        mutator methods.
   */
  default void before(Map<String, Object> parameters, InterceptionEvent event) {};

  /**
   * This method is called between {@link #before(Map, InterceptionEvent) before} and {@link #after(InterceptionEvent) after}.
   * <p>
   * If implemented, only by calling {@code action} {@link InterceptionAction#proceed() proceed()} will the interception chain
   * continue and eventually call the intercepted component. Otherwise, by calling {@link InterceptionAction#skip() skip()} the
   * interception chain execution will be interrupted and {@link #after(InterceptionEvent) after} method called immediately.
   * ({@link InterceptionAction#skip() skip()} may not be called at all, but it is convenient that it already returns a
   * {@link CompletableFuture} to return in this method.)
   * <p>
   * Calling an implementation with this method will be less efficient than calling just {@link #before(Map, InterceptionEvent)
   * before} and {@link #after(InterceptionEvent) after}. So, {@link #around(Map, InterceptionEvent, InterceptionAction) around}
   * should only be implemented for cases that cannot be done just with {@link #before(Map, InterceptionEvent) before} and/or
   * {@link #after(InterceptionEvent) after}.
   *
   * @param parameters the parameters of the component as defined in the configuration. Parameters that contain expressions will
   *        be resolved when passed to this method.
   * @param event an object that contains the state of the event to be sent to the component. It may be modified by calling its
   *        mutator methods.
   * @param action when something other than continuing the interception is desired, the corresponding method on this object must
   *        be called. The methods on this object return a {@link CompletableFuture} that may be used to return from this method.
   * @return a non-null {@link CompletableFuture} for modifying the intercepted {@link InterceptionEvent event} after this method
   *         returns.
   */
  default CompletableFuture<InterceptionEvent> around(Map<String, Object> parameters, InterceptionEvent event,
                                                      InterceptionAction action) {
    return action.proceed();
  };

  /**
   * This method is called after the intercepted component has run. It may modify the event to be used down in the chain and the
   * component via the given {@code event}.
   * <p>
   * This method will be called after the {@link #around(Map, InterceptionEvent, InterceptionAction) around} method has been
   * called.
   * <p>
   * If the intercepted component throws an {@link Exception}, the {@link #after(InterceptionEvent) after} methods will still be
   * called, with the passed {@link InterceptionEvent} returning the appropriate {@link Error} on
   * {@link InterceptionEventResult#getError()}.
   * <p>
   * If {@link #before(Map, InterceptionEvent) before} throws an {@link Exception}, the interception will be called there, but the
   * {@link #after(InterceptionEvent) afters} of the already called handlers will still be called.
   * 
   * @param event the result of the component.
   */
  default void after(InterceptionEvent event) {}
}
