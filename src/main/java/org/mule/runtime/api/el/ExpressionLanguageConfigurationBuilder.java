/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.el;

import static java.nio.charset.Charset.defaultCharset;

import org.mule.runtime.api.config.FeatureFlaggingService;

import java.nio.charset.Charset;

public class ExpressionLanguageConfigurationBuilder {

  private Charset charset = defaultCharset();

  private FeatureFlaggingService featureFlaggingService;

  ExpressionLanguageConfigurationBuilder() {}

  public ExpressionLanguageConfigurationBuilder defaultEncoding(Charset charset) {
    this.charset = charset;
    return this;
  }

  public ExpressionLanguageConfigurationBuilder featureFlaggingService(FeatureFlaggingService featureFlaggingService) {
    this.featureFlaggingService = featureFlaggingService;
    return this;
  }

  public ExpressionLanguageConfiguration build() {
    return new DefaultExpressionLanguageConfiguration(charset, featureFlaggingService);
  }

}
