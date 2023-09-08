/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.el;

import org.mule.api.annotation.NoImplement;
import org.mule.runtime.api.service.Service;

/**
 * Provides a factory for the default Expression language that is going to be used by the {@link MuleExpressionLanguage}
 */
@NoImplement
public interface DefaultExpressionLanguageFactoryService extends Service {

  /**
   * Create the expression language
   *
   * @return The expression language
   */
  ExpressionLanguage create();

  /**
   * Create the expression language considering a specific configuration
   *
   * @param configuration the {@link ExpressionLanguageConfiguration} to use
   * @return The expression language
   * @since 1.2
   */
  ExpressionLanguage create(ExpressionLanguageConfiguration configuration);
}
