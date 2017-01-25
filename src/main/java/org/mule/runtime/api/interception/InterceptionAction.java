/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.runtime.api.interception;

/**
 * Allows the implementations of {@link InterceptionHandler#before(String, java.util.Map, InterceptionEvent, InterceptionAction)}
 * to control the result of the interception.
 * 
 * @since 1.0
 */
public interface InterceptionAction {

  /**
   * Only operation components or similar may be skipped. Routing or intercepting components may not be intercepted.
   * 
   * @return {@code true} if the intercepted components may be {@link #skip() skipped}.
   */
  boolean isSkippable();

  /**
   * Interrupts the current interception chain, effectively skipping the remaining {@link InterceptionHandler handlers} and the
   * intercepted components.
   * <p>
   * In the case an {@link InterceptionHandler} calls this method, the following handlers in the chain nor the components will be
   * run. Only the {@link InterceptionHandler#after(InterceptionEventResult)}s for the calling {@link InterceptionHandler handler}
   * and those for which {@link InterceptionHandler#before(String, java.util.Map, InterceptionEvent, InterceptionAction)} was
   * called will be invoked.
   */
  void skip();

}
