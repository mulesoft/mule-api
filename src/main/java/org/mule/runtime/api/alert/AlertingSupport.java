/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.alert;

import org.mule.api.annotation.Experimental;
import org.mule.api.annotation.NoImplement;

/**
 * Alerts are triggered events that may happen over time. Analysis of the frequency and time between alerts may hint to an issue
 * and aid in troubleshooting an anomalous situation within the Mule Runtime.
 *
 * @since 1.10
 */
@NoImplement
@Experimental
public interface AlertingSupport {

  /**
   * Triggers an alert with no additional data.
   *
   * @param alertName the unique name to identify the alert being raised.
   */
  void triggerAlert(String alertName);

  /**
   * Triggers an alert with the given additional data.
   *
   * @param alertName the unique name to identify the alert being raised.
   * @param alertData additional data related to the alert being triggered.
   */
  <T> void triggerAlert(String alertName, T alertData);

}
