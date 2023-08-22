/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.service;

import static java.util.Objects.requireNonNull;

/**
 * Defines a service implementation for a given {@link Service} interface
 *
 * @since 1.0
 */
public final class ServiceDefinition {

  private final Class<? extends Service> serviceClass;
  private final Service service;

  /**
   * Defines a new service instance.
   *
   * @param serviceInterface interface defining the service. Non null.
   * @param service          implementation of the {@code serviceInterface}. Non null.
   */
  public ServiceDefinition(Class<? extends Service> serviceInterface, Service service) {
    requireNonNull(serviceInterface, "ServiceClass cannot be null");
    requireNonNull(service, "Service cannot be null");

    if (!serviceInterface.isInterface()) {
      throw new IllegalArgumentException("ServiceClass must be an interface");
    }
    if (!serviceInterface.isAssignableFrom(service.getClass())) {
      throw new IllegalArgumentException("Service must be instance of " + serviceInterface + " but is " + service.getClass());
    }

    this.serviceClass = serviceInterface;
    this.service = service;
  }

  public Class<? extends Service> getServiceClass() {
    return serviceClass;
  }

  public Service getService() {
    return service;
  }
}
