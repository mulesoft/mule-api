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
 * A consumer of profiler data. This should be implemented for creating profiling data consumers.
 *
 * @since 1.4
 */
@Experimental
public interface ProfilingDataConsumer<T extends ProfilingEventContext> {

  /**
   * Callback for consuming the profiling event.
   *
   * @param profilerEventIdentifier the profiling event id.
   * @param profilerEventContext    the profiler event context.
   */
  void onProfilingEvent(String profilerEventIdentifier, T profilerEventContext);

  /**
   * @return the {@link ProfilingEventType} {@link ProfilingDataConsumer} will listen to.
   */
  Set<ProfilingEventType<T>> profilerEventTypes();

  /**
   * @return the selector to indicate another filter for the events the data consumer will consume.
   */
  Predicate<T> selector();

}
