/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.healthcheck;

import static java.util.Optional.empty;

import java.util.Optional;

/**
 * Encapsulates the information regarding the ready status of an application.
 */
public interface ReadyStatus {

  /**
   * @return true if the application is ready to receive traffic false otherwise
   */
  boolean isReady();

  /**
   * @return A human-readable description of the current status of the application. In case of errors, expect a brief summary of
   *         what is wrong.
   */
  default Optional<String> statusDescription() {
    return empty();
  }

}
