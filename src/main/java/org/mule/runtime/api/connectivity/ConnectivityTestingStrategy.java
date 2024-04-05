/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.connectivity;

import static java.util.ServiceLoader.load;
import static java.util.stream.StreamSupport.stream;

import org.mule.api.annotation.NoImplement;
import org.mule.runtime.api.connection.ConnectionValidationResult;

import java.util.stream.Stream;

/**
 * An strategy for doing connectivity testing.
 * <p>
 * Instances of {@code ConnectivityTestingStrategy} will be discovered through SPI.
 * <p>
 * It's responsible to discover a mule component over the one connectivity testing can be done. Only one mule component for
 * connectivity testing must exists for the strategy to work.
 *
 * @since 1.0
 */
@NoImplement
public interface ConnectivityTestingStrategy {

  /**
   * Looks up implementations of {@link ConnectivityTestingStrategy} with the provided classloader.
   *
   * @param classLoader class loader from where to load the {@link ServiceConfigurator}s.
   * @return the discovered {@link ConnectivityTestingStrategy}.
   * @since 1.7
   */
  static Stream<ConnectivityTestingStrategy> lookupConnectivityTestingStrategies(ClassLoader classLoader) {
    return stream(((Iterable<ConnectivityTestingStrategy>) () -> load(ConnectivityTestingStrategy.class, classLoader).iterator())
        .spliterator(), false);
  }

  /**
   * Does connectivity validation over the provided mule component.
   *
   * @return a {@code ConnectionValidationResult} describing the test connectivity result.
   * @param connectivityTestingObject object over the one connectivity testing must be done
   */
  ConnectionValidationResult testConnectivity(Object connectivityTestingObject);

  /**
   * Determines if this {@code ConnectivityTestingStrategy} must be applied over the provided object.
   *
   * @param connectivityTestingObject object over the one connectivity testing must be done
   * @return true if this strategy can do connectivity testing over the provided component, false otherwise.
   */
  boolean accepts(Object connectivityTestingObject);

}
