/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.runtime.api.interception;

import java.util.Map;

/**
 * Provides a way to hook behavior around a Processor. Implementations must implement the
 * {@link #before(String, Map, InterceptionEvent, InterceptionAction) before} method and may or may not implement
 * {@link #after(InterceptionEventResult) after} (by default, {@link #after(InterceptionEventResult) after} does nothing).
 * <p>
 * Interceptable processors are those that are defined by a configuration element and have a flowPath.
 * <p>
 * A processor may have more than one interceptor applied. In that case, all will be applied in a predetermined order, calling the
 * {@link #before(String, Map, InterceptionEvent, InterceptionAction) before} methods of each, then the processor itself and
 * finally the {@link #after(InterceptionEventResult) after} methods in inverse order.
 * <p>
 * When initialized, all fields in the implementation with @{@link javax.inject.Inject} will be set with an object obtained from
 * the registry by type.
 * 
 * @since 1.0
 */
public interface InterceptionHandler {

  /**
   * This method is called before the intercepted processor has run. It may call a method on {@code action} to do anything other
   * than continue with the interception chain, modify the event to be used down in the chain and the processor, or both.
   * 
   * @param flowPath the path of the intercepted processor in the mule app configuration.
   * @param parameters the parameters of the processor as defined in the configuration. Parameters that contain expressions will
   *        be resolved when passed to this method.
   * @param event an object that contains the state of the event to be sent to the processor. It may be modified by calling its
   *        mutator methods.
   * @param action when something other than continuing the interception is desired, the corresponding method on this object must
   *        be called.
   */
  void before(String flowPath, Map<String, Object> parameters, InterceptionEvent event, InterceptionAction action);

  /**
   * Used only for notification, this method will be called after the intercepted processor has run, or after it was skipped in
   * the {@link #before(String, Map, InterceptionEvent, InterceptionAction) before} method.
   * 
   * @param event the result of the processor.
   */
  default void after(InterceptionEventResult event) {}
}
