/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
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
