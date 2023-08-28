/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.el;

import org.mule.runtime.api.config.FeatureFlaggingService;
import org.mule.runtime.api.meta.MuleVersion;

import java.nio.charset.Charset;
import java.util.Optional;

class DefaultExpressionLanguageConfiguration implements ExpressionLanguageConfiguration {

  private final Charset charset;

  private final FeatureFlaggingService featureFlaggingService;

  private final String appId;

  private final Optional<MuleVersion> minMuleVersion;

  DefaultExpressionLanguageConfiguration(Charset charset, FeatureFlaggingService featureFlaggingService, String appId,
                                         Optional<MuleVersion> minMuleVersion) {
    this.charset = charset;
    this.featureFlaggingService = featureFlaggingService;
    this.appId = appId;
    this.minMuleVersion = minMuleVersion;
  }

  @Override
  public Charset getDefaultEncoding() {
    return charset;
  }

  @Override
  public FeatureFlaggingService getFeatureFlaggingService() {
    return featureFlaggingService;
  }

  @Override
  public String getAppId() {
    return appId;
  }

  @Override
  public Optional<MuleVersion> getMinMuleVersion() {
    return minMuleVersion;
  }
}
