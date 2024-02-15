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

public interface RetryPolicyTemplate {

  boolean isEnabled();

  <T> CompletableFuture<T> applyPolicy(Supplier<CompletableFuture<T>> futureSupplier,
                                       Predicate<Throwable> shouldRetry,
                                       Consumer<Throwable> onRetry,
                                       Consumer<Throwable> onExhausted,
                                       Function<Throwable, Throwable> errorFunction,
                                       Scheduler retryScheduler);

}
