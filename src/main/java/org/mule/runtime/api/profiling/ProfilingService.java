/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.runtime.api.profiling;

import org.mule.api.annotation.Experimental;
import org.mule.api.annotation.NoImplement;
import org.mule.runtime.api.profiling.type.ProfilingEventType;

/**
 * Service that provides resources for profiling.
 * This is implemented by the runtime.
 *
 * @since 4.4
 */
@Experimental
@NoImplement
public interface ProfilingService {

  /**
   * Returns a data producer to notify events for a profiler event type.
   *
   * @param profilingEventType the {@link ProfilingEventType} for the {@link ProfilingDataProducer}
   * @return the corresponding {@link ProfilingDataProducer}
   */
  <T extends ProfilingEventContext> ProfilingDataProducer<T> getProfilingDataProducer(ProfilingEventType<T> profilingEventType);

  /**
   * register a {@link ProfilingEventType} associated to a {@link ProfilingDataProducer}
   *
   * @param profilingEventType the {@link ProfilingEventType} to be registered.
   * @param dataProducer       the {@link ProfilingDataProducer} to be associated.
   */
  <T extends ProfilingEventContext> void register(ProfilingEventType<T> profilingEventType,
                                                  ProfilingDataProducer<T> dataProducer);

}
