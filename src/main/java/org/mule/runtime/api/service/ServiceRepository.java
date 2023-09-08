/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
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
