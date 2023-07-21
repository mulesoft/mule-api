/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
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
