/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.retry.policy;

import org.mule.runtime.api.scheduler.Scheduler;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * A {@code RetryPolicyTemplate} creates a new policy instance each time the retry goes into effect, thereby resetting any state
 * the policy may have (counters, etc.)
 *
 * @since 4.7
 */
public interface RetryPolicyTemplate {

  /**
   * Indicates if this policy is currently enabled or not.
   *
   * @return Whether {@code this} policy is enabled or not.
   */
  boolean isEnabled();

  /**
   * Applies the retry policy in a non-blocking manner by transforming the given {@link CompletableFuture} into one configured to
   * apply the retry logic.
   *
   * @param futureSupplier a {@link Supplier} of a {@link CompletableFuture} with the items which might fail.
   * @param shouldRetry    a predicate which evaluates each item to know if it should be retried or not.
   * @param onExhausted    an action to perform when the retry action has been exhausted.
   * @param errorFunction  function used to map cause exception to exception emitted by retry policy.
   * @param retryScheduler the scheduler to use when retrying. If empty, an internal Scheduler will be used.
   * @param <T>            the generic type of the {@link CompletableFuture}'s content.
   * @return a {@link CompletableFuture} configured with the retry policy.
   */
  <T> CompletableFuture<T> applyPolicy(Supplier<CompletableFuture<T>> futureSupplier,
                                       Predicate<Throwable> shouldRetry,
                                       Consumer<Throwable> onRetry,
                                       Consumer<Throwable> onExhausted,
                                       Function<Throwable, Throwable> errorFunction,
                                       Scheduler retryScheduler);

}
