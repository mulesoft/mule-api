/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
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
