/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.config;

import org.mule.api.annotation.NoImplement;

/**
 * This service exposes the features that were flagged based on the configurations registered. These configurations will be
 * evaluated when an application is deployed, which means that each application will have its own set of flags independently of
 * the rest of the applications deployed in a given runtime.
 * 
 * @since 1.4.0, 1.3.1, 1.2.4
 */
@NoImplement
public interface FeatureFlaggingService {

  String FEATURE_FLAGGING_SERVICE_KEY = "core.featureFlaggingService";

  /**
   * Inform if a given @{link feature} is enabled for the current context.
   * 
   * @see FeatureFlaggingService
   * 
   * @param feature The name of the feature being queried, as per was registered at configuration time.
   * 
   * @return a boolean indicating if the features is enabled for the current execution context
   */
  boolean isEnabled(Feature feature);
}
