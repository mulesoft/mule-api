/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.runtime.api.interception;

import java.util.concurrent.CompletableFuture;

/**
 * Allows the implementations of {@link InterceptionHandler#around(java.util.Map, InterceptionEvent, InterceptionAction) around}
 * to control the execution of the interception chain.
 * 
 * @since 1.0
 */
public interface InterceptionAction {

  /**
   * Indicates that the current interception chain must continue, proceeding with the next {@link InterceptionHandler handlers} in
   * the chain (if any) and the intercepted component.
   * 
   * @return a {@link CompletableFuture} with the {@link InterceptionEvent event} that results of the intercepted processor.
   */
  CompletableFuture<InterceptionEvent> proceed();

  /**
   * Interrupts the current interception chain, skipping the next {@link InterceptionHandler interceptors} in the chain and the
   * intercepted component.
   * <p>
   * If called for a <b>router</b> component, the whole route (and all the components contained in it) will be skipped.
   * <p>
   * If called for an intercepting component (i.e. a {@code splitter}), the listener components (i.e. what follows the
   * {@code splitter} up to an {@code aggregator}) are skipped as well.
   * 
   * @return a {@link CompletableFuture} with the same {@link InterceptionEvent event} as it was at the time this method was
   *         called.
   */
  CompletableFuture<InterceptionEvent> skip();
}
