/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.runtime.api.profiling;

import org.mule.api.annotation.Experimental;
import org.mule.runtime.api.profiling.type.ProfilingEventType;

/**
 * A producer of profiling data represented by a {@link ProfilingEventContext}.
 * <p>
 * A {@link ProfilingDataProducer} must be firstly registered via
 * {@link ProfilingService#registerProfilingDataProducer(ProfilingEventType, ProfilingDataProducer)} and then can be obtained via
 * {@link ProfilingService#getProfilingDataProducer(ProfilingEventType)}. Once it is obtained, it can be used to emit profiling
 * data by invoking {@link #event(ProfilingEventContext)}.
 *
 * @param <T> the class that encapsulates the data for the profiling event context.
 * @see ProfilingService
 * @since 1.4
 */
@Experimental
public interface ProfilingDataProducer<T extends ProfilingEventContext> {

  /**
   * Triggers a profiling event. Implementations must either be stateless or designed to support concurrent invocations.
   *
   * @param profilerEventContext the {@link ProfilingEventContext} for the emitted event.
   */
  void event(T profilerEventContext);

}
