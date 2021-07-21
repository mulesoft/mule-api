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
 * The general event context to encapsulate profiling data. This should be implemented by the components that produce profiling
 * data. When a profiling event is triggered, an implementation of this class is passed to
 * {@link ProfilingDataConsumer#onProfilingEvent(ProfilingEventType, ProfilingEventContext)}.
 *
 * @since 1.4
 */
@Experimental
public interface ProfilingEventContext {

  /**
   * @return the timestamp that corresponds to the moment in which the event that the context describes happened
   */
  long getTriggerTimestamp();
}
