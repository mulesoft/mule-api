/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.runtime.api.profiling;

import org.mule.api.annotation.Experimental;
import org.mule.runtime.api.profiling.type.ProfilingEventType;

import java.util.Set;
import java.util.function.Predicate;

/**
 * A consumer of profiling data represented by a {@link ProfilingEventContext}.
 * <p>
 * The implementation of this interface will be discovered by the Mule Runtime through a
 * {@link ProfilingDataConsumerDiscoveryStrategy}. Every time a profiling event included in the {@link #getProfilingEventTypes()}
 * is triggered, {@link #onProfilingEvent(ProfilingEventType, ProfilingEventContext)} will be invoked if
 * {@link #getEventContextFilter} returns true.
 * <p>
 * This is the class that should be implemented for consuming profiler data.
 *
 * @see ProfilingDataConsumerDiscoveryStrategy
 * @since 1.4
 */
@Experimental
public interface ProfilingDataConsumer<T extends ProfilingEventContext> {

  /**
   * Consumes a profiling event.
   *
   * @param profilingEventType    the {@link ProfilingEventType}.
   * @param profilingEventContext the profiler event context.
   */
  void onProfilingEvent(ProfilingEventType<T> profilingEventType, T profilingEventContext);

  /**
   * @return the {@link ProfilingEventType}'s the consumer will listen to.
   */
  Set<ProfilingEventType<T>> getProfilingEventTypes();

  /**
   * @return a selector that can be used to indicate a filter that will be applied to all the produced profiling data. If the
   *         predicate returns false, the profiling event will not be notified.
   *         ({@link #onProfilingEvent(ProfilingEventType, ProfilingEventContext)})
   */
  Predicate<T> getEventContextFilter();

}
