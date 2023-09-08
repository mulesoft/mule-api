/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.el;

import org.mule.runtime.api.service.Service;

import java.util.List;
import java.util.Set;

/**
 * This service allows to query the expression language for current supported capabilities.
 *
 * @since 1.2.0
 */
public interface ExpressionLanguageCapabilitiesService extends Service {

  /**
   * Returns the list of supported DataFormat
   *
   * @return The list of supported data formats
   */
  Set<DataFormat> getSupportedDataFormats();

}
