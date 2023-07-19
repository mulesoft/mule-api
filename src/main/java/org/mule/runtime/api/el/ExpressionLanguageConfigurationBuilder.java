/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.el;

import static java.nio.charset.Charset.defaultCharset;

import org.mule.runtime.api.config.FeatureFlaggingService;
import org.mule.runtime.api.meta.MuleVersion;

import java.nio.charset.Charset;
import java.util.Optional;

public class ExpressionLanguageConfigurationBuilder {

  private Charset charset = defaultCharset();

  private FeatureFlaggingService featureFlaggingService;

  private String appId;

  private Optional<MuleVersion> minMuleVersion;

  ExpressionLanguageConfigurationBuilder() {}

  public ExpressionLanguageConfigurationBuilder defaultEncoding(Charset charset) {
    this.charset = charset;
    return this;
  }

  public ExpressionLanguageConfigurationBuilder featureFlaggingService(FeatureFlaggingService featureFlaggingService) {
    this.featureFlaggingService = featureFlaggingService;
    return this;
  }

  public ExpressionLanguageConfigurationBuilder appId(String id) {
    this.appId = id;
    return this;
  }

  public ExpressionLanguageConfigurationBuilder minMuleVersion(Optional<MuleVersion> minMuleVersion) {
    this.minMuleVersion = minMuleVersion;
    return this;
  }

  public ExpressionLanguageConfiguration build() {
    return new DefaultExpressionLanguageConfiguration(charset, featureFlaggingService, appId, minMuleVersion);
  }

}
