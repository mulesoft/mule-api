/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.el;

import java.nio.charset.Charset;

/**
 * Determines all configuration for a {@link ExpressionLanguage} allowing customization.
 *
 * @since 1.2
 */
public interface ExpressionLanguageConfiguration {

  static ExpressionLanguageConfigurationBuilder builder() {
    return new ExpressionLanguageConfigurationBuilder();
  }

  /**
   * @return the encoding that should be use by default by the expression language
   */
  Charset getDefaultEncoding();

}
