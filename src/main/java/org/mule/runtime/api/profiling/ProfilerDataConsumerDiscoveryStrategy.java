/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.runtime.api.profiling;

import org.mule.api.annotation.Experimental;
import org.mule.api.annotation.NoImplement;

import java.util.Set;

/**
 * A strategy for discovering the available instances of {@link ProfilingDataConsumer}. The implementations of this interface
 * defines different ways of discovery by the runtime.
 *
 * @since 4.4
 */
@Experimental
@NoImplement
public interface ProfilerDataConsumerDiscoveryStrategy {

  /**
   * Discovers the {@link ProfilingDataProducer} visible for the runtime.
   *
   * @param <S> the type for the {@link ProfilingDataConsumer}
   * @param <T> the type for the event context which will have the profiling event context data.
   * @return the discovered {@link ProfilingDataProducer}'s.
   */
  <S extends ProfilingDataConsumer<T>, T extends ProfilingEventContext> Set<S> discover();

}
