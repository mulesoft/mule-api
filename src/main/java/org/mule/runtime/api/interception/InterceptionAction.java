/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.runtime.api.interception;

import org.mule.runtime.api.message.ErrorType;

import java.util.concurrent.CompletableFuture;

/**
 * Allows the implementations of {@link ProcessorInterceptor#around(java.util.Map, InterceptionEvent, InterceptionAction) around}
 * to control the execution of the interception chain.
 * 
 * @since 1.0
 */
public interface InterceptionAction {

  /**
   * Indicates that the current interception chain must continue, proceeding with the next {@link ProcessorInterceptor handlers}
   * in the chain (if any) and the intercepted component.
   * 
   * @return a {@link CompletableFuture} using the {@link InterceptionEvent event} that results of the remaining
   *         {@link ProcessorInterceptor handlers} of the chain and the intercepted processor.
   */
  CompletableFuture<InterceptionEvent> proceed();

  /**
   * Interrupts the current interception chain, skipping the next {@link ProcessorInterceptor interceptors} in the chain and the
   * intercepted component.
   * <p>
   * If called for a <b>router</b> component, the whole route (and all the components contained in it) will be skipped.
   * <p>
   * If called for an intercepting component (i.e. a {@code splitter}), the listener components (i.e. what follows the
   * {@code splitter} up to an {@code aggregator}) are skipped as well.
   * 
   * @return a {@link CompletableFuture} using the same {@link InterceptionEvent event} passed to the
   *         {@link ProcessorInterceptor#around(java.util.Map, InterceptionEvent, InterceptionAction) around()} method.
   */
  CompletableFuture<InterceptionEvent> skip();


  /**
   * Interrupts the current interception chain, not running the next {@link ProcessorInterceptor interceptors} in the chain and
   * the intercepted component.
   * <p>
   * A future is returned which in turn it will fail with an exception wrapping the provided cause
   * <p>
   * If called for a <b>router</b> component, the whole route (and all the components contained in it) will be skipped.
   * <p>
   * If called for an intercepting component (i.e. a {@code splitter}), the listener components (i.e. what follows the
   * {@code splitter} up to an {@code aggregator}) are skipped as well.
   *
   * @return a {@link CompletableFuture} using the same {@link InterceptionEvent event} passed to the
   *         {@link ProcessorInterceptor#around(java.util.Map, InterceptionEvent, InterceptionAction) around()} method.
   */
  CompletableFuture<InterceptionEvent> fail(Throwable cause);

  /**
   * Interrupts the current interception chain, not running the next {@link ProcessorInterceptor interceptors} in the chain and
   * the intercepted component.
   * <p>
   * A future is returned which in turn it will fail with an exception that maps the provided error type.
   * <p>
   * If called for a <b>router</b> component, the whole route (and all the components contained in it) will be skipped.
   * <p>
   * If called for an intercepting component (i.e. a {@code splitter}), the listener components (i.e. what follows the
   * {@code splitter} up to an {@code aggregator}) are skipped as well.
   *
   * @return a {@link CompletableFuture} using the same {@link InterceptionEvent event} passed to the
   *         {@link ProcessorInterceptor#around(java.util.Map, InterceptionEvent, InterceptionAction) around()} method.
   */
  CompletableFuture<InterceptionEvent> fail(ErrorType errorType);
}
