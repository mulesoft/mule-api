/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.runtime.api.profiling;

import org.mule.api.annotation.Experimental;

import java.util.Set;

/**
 * A strategy for discovering the available instances of {@link ProfilingDataConsumer}. The implementations of this interface
 * defines different ways for the Mule Runtime to discover the {@link ProfilingDataConsumer}'s available in the environment.
 * <p>
 * Different environments can use different discovery strategies.
 *
 * @since 1.4
 */
@Experimental
public interface ProfilingDataConsumerDiscoveryStrategy {

  /**
   * Discovers the {@link ProfilingDataProducer}'s.
   *
   * @param <S> the type for the {@link ProfilingDataConsumer}
   * @param <T> the type for the event context which will have the profiling event context data.
   * @return the discovered {@link ProfilingDataProducer}'s.
   */
  Set<ProfilingDataConsumer<?>> discover();

}
