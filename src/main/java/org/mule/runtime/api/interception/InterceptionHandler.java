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

/**
 * Provides a way to hook behavior around a component. Implementations must implement the
 * {@link #before(String, Map, InterceptionEvent, InterceptionAction) before} method and may or may not implement
 * {@link #after(InterceptionEventResult) after} (by default, {@link #after(InterceptionEventResult) after} does nothing).
 * <p>
 * Interceptable components are those that are defined by a configuration element and have a flowPath.
 * <p>
 * A component may have more than one interceptor applied. In that case, all will be applied in a predetermined order, calling the
 * {@link #before(String, Map, InterceptionEvent, InterceptionAction) before} methods of each, then the component itself and
 * finally the {@link #after(InterceptionEventResult) after} methods in inverse order.
 * <p>
 * When initialized, all fields in the implementation with @{@link javax.inject.Inject} will be set with an object obtained from
 * the registry by type.
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
   * This method is called before the intercepted component has run. It may call a method on {@code action} to do anything other
   * than continue with the interception chain, modify the event to be used down in the chain and the component, or both.
   * 
   * @param parameters the parameters of the component as defined in the configuration. Parameters that contain expressions will
   *        be resolved when passed to this method.
   * @param event an object that contains the state of the event to be sent to the component. It may be modified by calling its
   *        mutator methods.
   * @param action when something other than continuing the interception is desired, the corresponding method on this object must
   *        be called.
   */
  default void before(Map<String, Object> parameters, InterceptionEvent event, InterceptionAction action) {};

  /**
   * Used only for notification, this method will be called after the intercepted component has run, or after it was skipped in
   * the {@link #before(String, Map, InterceptionEvent, InterceptionAction) before} method.
   * <p>
   * If the intercepted component throws an {@link Exception}, the {@link #after(InterceptionEventResult) after} methods will
   * still be called, with the passed {@link InterceptionEventResult} returning the appropriate {@link Error} on
   * {@link InterceptionEventResult#getError()}.
   * <p>
   * If {@link #before(String, Map, InterceptionEvent, InterceptionAction) before} throws an {@link Exception}, the interception
   * will be called there, but the {@link #after(InterceptionEventResult) afters} of the already called handlers will still be
   * called.
   * 
   * @param event the result of the component.
   */
  default void after(InterceptionEventResult event) {}
}
