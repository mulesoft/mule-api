/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.healthcheck;

/**
 * HealthCheck validator contract to be implemented by any component that needs to report on the ready status of an application.
 *
 * The validator has to be added to the application registry in order to be discoverable.
 *
 */
public interface HealthCheckValidator {

  /**
   * @return a {@link ReadyStatus} instance that informs if the application is ready to receive traffic or not
   */
  ReadyStatus ready();
}
