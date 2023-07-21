/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.el;

import org.mule.api.annotation.NoImplement;
import org.mule.runtime.api.config.FeatureFlaggingService;
import org.mule.runtime.api.meta.MuleVersion;

import java.nio.charset.Charset;
import java.util.Optional;

/**
 * Determines all configuration for a {@link ExpressionLanguage} allowing customization.
 *
 * @since 1.2
 */
@NoImplement
public interface ExpressionLanguageConfiguration {

  static ExpressionLanguageConfigurationBuilder builder() {
    return new ExpressionLanguageConfigurationBuilder();
  }

  /**
   * @return the encoding that should be use by default by the expression language
   */
  Charset getDefaultEncoding();

  /**
   * Returns an instance of FeatureFlaggingService with per deployment configured {@link org.mule.runtime.api.config.Feature}s
   * 
   * @return
   * @since 1.4.0, 1.3.1
   */
  FeatureFlaggingService getFeatureFlaggingService();

  /**
   * @return The application ID.
   *
   * @since 1.5.0
   */
  String getAppId();


  /**
   * @return The {@link MuleVersion} that the application has configured.
   *
   * @since 1.5.0
   */
  Optional<MuleVersion> getMinMuleVersion();

}
