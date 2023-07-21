/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.interception;

import org.mule.api.annotation.NoImplement;
import org.mule.runtime.api.message.ErrorType;

import java.util.concurrent.CompletableFuture;

/**
 * Allows the implementations of {@link ProcessorInterceptor#around(java.util.Map, InterceptionEvent, InterceptionAction) around}
 * and {@link FlowInterceptor#around(String, InterceptionEvent, InterceptionAction) around} to control the execution of the
 * interception chain.
 *
 * @since 1.0
 */
@NoImplement
public interface InterceptionAction {

  /**
   * Indicates that the current interception chain must continue, proceeding with the next {@link ProcessorInterceptor handlers}
   * in the chain (if any) and the intercepted component or flow.
   *
   * @return a {@link CompletableFuture} using the {@link InterceptionEvent event} that results of the remaining
   *         {@link ProcessorInterceptor handlers} of the chain and the intercepted processor.
   */
  CompletableFuture<InterceptionEvent> proceed();

  /**
   * Interrupts the current interception chain, skipping the next {@link ProcessorInterceptor interceptors} in the chain and the
   * intercepted component.
   * <p>
   * If called for a <b>router</b> component or a <b>flow</b>, the whole route or flow (and all the components contained in it)
   * will be skipped.
   * <p>
   * If called for an intercepting component (i.e. a {@code splitter}), the listener components (i.e. what follows the
   * {@code splitter} up to an {@code aggregator}) are skipped as well.
   *
   * @return a {@link CompletableFuture} using the same {@link InterceptionEvent event} passed to the
   *         {@link ProcessorInterceptor#around(java.util.Map, InterceptionEvent, InterceptionAction) around()} or
   *         {@link FlowInterceptor#around(String, InterceptionEvent, InterceptionAction) around()} method.
   */
  CompletableFuture<InterceptionEvent> skip();


  /**
   * Interrupts the current interception chain, not running the next {@link ProcessorInterceptor interceptors} in the chain and
   * the intercepted component.
   * <p>
   * A future is returned which in turn it will fail with an exception wrapping the provided cause
   * <p>
   * If called for a <b>router</b> component or a <b>flow</b>, the whole route or flow (and all the components contained in it)
   * will be skipped.
   * <p>
   * If called for an intercepting component (i.e. a {@code splitter}), the listener components (i.e. what follows the
   * {@code splitter} up to an {@code aggregator}) are skipped as well.
   *
   * @return a {@link CompletableFuture} using the same {@link InterceptionEvent event} passed to the
   *         {@link ProcessorInterceptor#around(java.util.Map, InterceptionEvent, InterceptionAction) around()} or
   *         {@link FlowInterceptor#around(String, InterceptionEvent, InterceptionAction) around()} method.
   */
  CompletableFuture<InterceptionEvent> fail(Throwable cause);

  /**
   * Interrupts the current interception chain, not running the next {@link ProcessorInterceptor interceptors} in the chain and
   * the intercepted component.
   * <p>
   * A future is returned which in turn it will fail with an exception that maps the provided error type.
   * <p>
   * If called for a <b>router</b> component or a <b>flow</b>, the whole route or flow (and all the components contained in it)
   * will be skipped.
   * <p>
   * If called for an intercepting component (i.e. a {@code splitter}), the listener components (i.e. what follows the
   * {@code splitter} up to an {@code aggregator}) are skipped as well.
   *
   * @return a {@link CompletableFuture} using the same {@link InterceptionEvent event} passed to the
   *         {@link ProcessorInterceptor#around(java.util.Map, InterceptionEvent, InterceptionAction) around()} or
   *         {@link FlowInterceptor#around(String, InterceptionEvent, InterceptionAction) around()} method.
   */
  CompletableFuture<InterceptionEvent> fail(ErrorType errorType);

  /**
   * Interrupts the current interception chain, not running the next {@link ProcessorInterceptor interceptors} in the chain and
   * the intercepted component.
   * <p>
   * A future is returned which in turn it will fail with an exception that maps the provided error type, with the error message
   * provided.
   * <p>
   * If called for a <b>router</b> component or a <b>flow</b>, the whole route or flow (and all the components contained in it)
   * will be skipped.
   * <p>
   * If called for an intercepting component (i.e. a {@code splitter}), the listener components (i.e. what follows the
   * {@code splitter} up to an {@code aggregator}) are skipped as well.
   *
   * @return a {@link CompletableFuture} using the same {@link InterceptionEvent event} passed to the
   *         {@link ProcessorInterceptor#around(java.util.Map, InterceptionEvent, InterceptionAction) around()} or
   *         {@link FlowInterceptor#around(String, InterceptionEvent, InterceptionAction) around()} method.
   *
   * @since 1.2
   */
  CompletableFuture<InterceptionEvent> fail(ErrorType errorType, String msg);
}
