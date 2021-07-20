/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.runtime.api.profiling;

import org.mule.api.annotation.Experimental;

/**
 * A producer of profiling data.
 * <p>
 * This is a resource that can be obtained from a {@link ProfilingService}. Once it is acquired, it can be used to emit profiling
 * data by invoking {@link #event(ProfilingEventContext)}.
 *
 * @param <T> the class that encapsulates the data for the profiling event context.
 * @see ProfilingService
 * @since 1.4
 */
@Experimental
public interface ProfilingDataProducer<T extends ProfilingEventContext> {

  /**
   * Triggers a profiling event.
   *
   * @param profilerEventContext the {@link ProfilingEventContext} for the emitted event.
   */
  void event(T profilerEventContext);

}
