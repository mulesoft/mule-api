/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.service;

import org.mule.api.annotation.NoImplement;

import java.util.List;

/**
 * Provides access to the services available in the container.
 *
 * @since 1.1
 */
@NoImplement
public interface ServiceRepository {

  /**
   * Provides access to the services available in the container.
   *
   * @return a non null list of services.
   */
  List<Service> getServices();
}
