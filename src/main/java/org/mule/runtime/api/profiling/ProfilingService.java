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
 * Service that provides profiling resources. This is implemented by the Runtime. Different components can inject this service and
 * register a {@link ProfilingEventContext} or obtain a {@link ProfilingDataProducer} to begin emitting profiling data. For
 * example:
 *
 * <code>
 *    &#64;Inject
 *    private ProfilingService profilingService;
 *
 *     // register a profiling event type
 *     profilingService.register(TEST_PROFILING_TYPE, new TestProfilingDataProducer());
 *
 *     // ....
 *
 *     // trigger e profiling event type
 *     profilingService.getProfilingDataProducer(TEST_PROFILING_TYPE).event(data);
 * </code>
 *
 * @see ProfilingDataProducer
 * @see ProfilingEventType
 * @since 1.4
 */
@Experimental
@NoImplement
public interface ProfilingService {

  /**
   * Returns a previously registered {@link ProfilingDataProducer} that can be used to emit profiling events of a certain
   * {@link ProfilingEventType}.
   *
   * @param profilingEventType the {@link ProfilingEventType} that will be emitted.
   * @return the corresponding {@link ProfilingDataProducer}
   */
  <T extends ProfilingEventContext> ProfilingDataProducer<T> getProfilingDataProducer(ProfilingEventType<T> profilingEventType);

  /**
   * Registers a {@link ProfilingDataProducer} that can be obtained to emit profiling events of a certain
   * {@link ProfilingEventType}.
   *
   * @param profilingEventType    the {@link ProfilingEventType} that the {@link ProfilingDataProducer} will emit.
   * @param profilingDataProducer the {@link ProfilingDataProducer} that will be registered.
   */
  <T extends ProfilingEventContext> void registerProfilingDataProducer(ProfilingEventType<T> profilingEventType,
                                                                       ProfilingDataProducer<T> profilingDataProducer);

}
