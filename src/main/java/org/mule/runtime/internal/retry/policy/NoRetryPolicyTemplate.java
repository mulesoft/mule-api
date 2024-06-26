/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.internal.retry.policy;

import org.mule.runtime.api.retry.policy.RetryPolicyTemplate;
import org.mule.runtime.api.scheduler.Scheduler;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * This policy is basically a placeholder. It does not attempt to retry at all.
 */
public final class NoRetryPolicyTemplate implements RetryPolicyTemplate {

  @Override
  public <T> CompletableFuture<T> applyPolicy(Supplier<CompletableFuture<T>> completableFutureSupplier,
                                              Predicate<Throwable> shouldRetry, Consumer<Throwable> onRetry,
                                              Consumer<Throwable> onExhausted, Function<Throwable, Throwable> errorFunction,
                                              Scheduler retryScheduler) {
    return completableFutureSupplier.get();
  }

  @Override
  public boolean isEnabled() {
    return false;
  }

  @Override
  public String toString() {
    return "NoRetryPolicy{}";
  }
}
