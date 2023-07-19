/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.internal.config.custom;

import static java.util.ServiceLoader.load;
import static java.util.stream.StreamSupport.stream;

import org.mule.runtime.api.config.custom.ServiceConfigurator;

import java.util.stream.Stream;

/**
 * Provides utilities for the Runtime to discover {@link ServiceConfigurator} implementations.
 * 
 * @since 1.5.0
 */
public class ServiceConfiguratorUtils {

  private ServiceConfiguratorUtils() {
    // Nothing to do
  }

  /**
   * Looks up implementations of {@link ServiceConfigurator}.
   * 
   * @return the discovered {@link ServiceConfigurator}.
   */
  public static Stream<ServiceConfigurator> lookupServiceConfigurators() {
    return stream(((Iterable<ServiceConfigurator>) () -> load(ServiceConfigurator.class,
                                                              ServiceConfiguratorUtils.class.getClassLoader())
                                                                  .iterator()).spliterator(),
                  false);
  }

}
