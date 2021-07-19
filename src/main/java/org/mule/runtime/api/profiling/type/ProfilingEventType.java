/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.runtime.api.profiling.type;

import org.mule.api.annotation.Experimental;
import org.mule.runtime.api.profiling.ProfilingDataProducer;
import org.mule.runtime.api.profiling.ProfilingEventContext;

/**
 * The profiling event type.
 * <p>
 * A component (a connector, the Runtime itself) may register this profiling event type by invoking
 * {@link org.mule.runtime.api.profiling.ProfilingService#register(ProfilingEventType, ProfilingDataProducer)}.
 * <p>
 * Once registered, it can be obtained by invoking
 * {@link org.mule.runtime.api.profiling.ProfilingService#getProfilingDataProducer(ProfilingEventType)}.
 * <p>
 * New profiling data can be produced by invoking
 * {@link org.mule.runtime.api.profiling.ProfilingDataProducer#event(ProfilingEventContext)}.
 *
 * @param <T> the {@link ProfilingEventContext} associated to the type.
 * @since 1.4
 */
@Experimental
public interface ProfilingEventType<T extends ProfilingEventContext> {

  /**
   * @return the profiling event type name.
   */
  String getProfilingEventTypeName();

  /**
   * @return the profiler event type namespace.
   */
  String getProfilerEventTypeNamespace();

  /**
   * @return the numeric id for the profiling event type.
   */
  long getProfilingEventTypeId();

}
