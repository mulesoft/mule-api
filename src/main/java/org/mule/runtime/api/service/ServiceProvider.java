/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.service;

/**
 * Provides a {@link ServiceDefinition}
 *
 * @since 1.2
 */
public interface ServiceProvider {

  /**
   * Provides a service instance.
   * <p/>
   * A service provider can return different service definitions depending on the execution environment.
   *
   * @return the service definition provided by this instance. Non null.
   */
  ServiceDefinition getServiceDefinition();
}
