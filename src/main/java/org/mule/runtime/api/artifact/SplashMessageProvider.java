/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.artifact;

/**
 * Provides a way for an artifact to log a splash message in the log when the artifact is started.
 *
 * @since 1.1
 */
public interface SplashMessageProvider {

  /**
   * Provides a message to show in the splash screen of the Mule Runtime when this is started.
   *
   * @return the message to show in the splash screen
   */
  String getSplashMessage();
}
