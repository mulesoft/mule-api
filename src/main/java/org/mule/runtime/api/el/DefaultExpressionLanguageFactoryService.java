/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.el;


import org.mule.runtime.api.service.Service;

/**
 * Provides a factory for the default Expression language that is going to be used by the {@link MuleExpressionLanguage}
 */
public interface DefaultExpressionLanguageFactoryService extends Service {

  /**
   * Create the expression language
   *
   * @return The expression language
   */
  ExpressionLanguage create();
}
