/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.healthcheck;

/**
 * Encapsulates the information regarding the ready status of an application.
 */
public interface ReadyStatus {

  /**
   * @return true if the application is ready to receive traffic
   *  false otherwise
   */
  boolean isReady();

}
