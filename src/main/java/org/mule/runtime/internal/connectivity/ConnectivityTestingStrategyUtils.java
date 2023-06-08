/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.internal.connectivity;

import static java.util.ServiceLoader.load;
import static java.util.stream.StreamSupport.stream;

import org.mule.runtime.api.connectivity.ConnectivityTestingStrategy;

import java.util.stream.Stream;

/**
 * Provides utilities for the Runtime to discover {@link ConnectivityTestingStrategy} implementations.
 * 
 * @since 1.5.0
 */
public class ConnectivityTestingStrategyUtils {

  private ConnectivityTestingStrategyUtils() {
    // Nothing to do
  }

  /**
   * Looks up implementations of {@link ConnectivityTestingStrategy} with the provided classloader.
   * 
   * @param classLoader the classlaoder to use for loading the services through SPI.
   * @return the discovered {@link ConnectivityTestingStrategy}.
   */
  public static Stream<ConnectivityTestingStrategy> lookupConnectivityTestingStrategies(ClassLoader classLoader) {
    return stream(((Iterable<ConnectivityTestingStrategy>) () -> load(ConnectivityTestingStrategy.class, classLoader)
        .iterator()).spliterator(),
                  false);
  }

}
