/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.runtime.api.profiling;

import org.mule.api.annotation.Experimental;
import org.mule.api.annotation.NoImplement;

/**
 * A producer of profiling data. This should be implemented by the components that produce profiling data.
 *
 * @param <T> the class that encapsulates the data for the profiling event context.
 * @since 1.4
 */
@Experimental
public interface ProfilingDataProducer<T extends ProfilingEventContext> {

  /**
   * Notifies a profiling event.
   *
   * @param profilerEventContext the {@link ProfilingEventContext} for the emitted event.
   */
  void event(T profilerEventContext);

}
