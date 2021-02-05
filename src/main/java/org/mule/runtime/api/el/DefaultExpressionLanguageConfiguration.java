/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.el;

import org.mule.runtime.api.config.FeatureFlaggingService;

import java.nio.charset.Charset;

class DefaultExpressionLanguageConfiguration implements ExpressionLanguageConfiguration {

  private final Charset charset;

  private final FeatureFlaggingService featureFlaggingService;

  DefaultExpressionLanguageConfiguration(Charset charset, FeatureFlaggingService featureFlaggingService) {
    this.charset = charset;
    this.featureFlaggingService = featureFlaggingService;
  }

  @Override
  public Charset getDefaultEncoding() {
    return charset;
  }

  @Override
  public FeatureFlaggingService getFeatureFlaggingService() {
    return featureFlaggingService;
  }
}
