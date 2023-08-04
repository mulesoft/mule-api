/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
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
