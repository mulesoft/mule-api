/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
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
