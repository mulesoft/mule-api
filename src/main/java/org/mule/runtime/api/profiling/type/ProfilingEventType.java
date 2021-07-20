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
import org.mule.runtime.api.profiling.ProfilingService;

/**
 * The profiling event type. A {@link ProfilingEventType} along with a {@link ProfilingEventContext} represents a profiling event.
 *
 * @param <T> the {@link ProfilingEventContext} associated to the type.
 * @see ProfilingService
 * @since 1.4
 */
@Experimental
public interface ProfilingEventType<T extends ProfilingEventContext> {

  /**
   * @return the identifier for the profiling event type.
   */
  String getProfilingEventTypeName();

  /**
   * @return the namespace of the module where the profiling event type is defined. For instance, for runtime profiling events the
   *         namespace is core.
   */
  String getProfilerEventTypeNamespace();

}
