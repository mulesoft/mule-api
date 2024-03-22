/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.config.custom;

import static java.util.ServiceLoader.load;
import static java.util.stream.StreamSupport.stream;

import org.mule.api.annotation.NoImplement;

import java.util.stream.Stream;

@NoImplement
public interface ServiceConfigurator {

  /**
   * Looks up implementations of {@link ServiceConfigurator}.
   *
   * @return the discovered {@link ServiceConfigurator}s.
   */
  static Stream<ServiceConfigurator> lookupServiceConfigurators() {
    return stream(((Iterable<ServiceConfigurator>) () -> load(ServiceConfigurator.class,
                                                              ServiceConfigurator.class.getClassLoader())
                                                                  .iterator()).spliterator(),
                  false);
  }

  void configure(CustomizationService customizationService);
}
