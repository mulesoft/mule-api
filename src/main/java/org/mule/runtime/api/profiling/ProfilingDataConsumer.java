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
 * The implementation of this interface will be discovered by the Mule Runtime. Every time a profiling event included in the
 * {@link #getProfilingEventTypes()} is triggered, the {@link #onProfilingEvent(String, ProfilingEventContext)} callback will be
 * invoked if {@link #getEventContextFilter} returns true.
 * <p>
 * This is the class that should be implemented for consuming profiler data.
 *
 * @since 1.4
 */
@Experimental
public interface ProfilingDataConsumer<T extends ProfilingEventContext> {

  /**
   * Callback for consuming the profiling events.
   *
   * @param profilingEventIdentifier the profiling event id.
   * @param profilingEventContext    the profiler event context.
   */
  void onProfilingEvent(String profilingEventIdentifier, T profilingEventContext);

  /**
   * @return the {@link ProfilingEventType}'s the consumer will listen to.
   */
  Set<ProfilingEventType<T>> getProfilingEventTypes();

  /**
   * @return a selector that can be used to indicate a filter that will be applied to all the produced profiling data. If the
   *         predicate returns false, the profiling event will not be notified.
   *         ({@link #onProfilingEvent(String, ProfilingEventContext)})
   */
  Predicate<T> getEventContextFilter();

}
