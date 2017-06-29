/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model;

import java.util.Set;

/**
 * Interface for a model which contains {@link ExternalDependencyModel external dependencies}
 *
 * @since 1.0
 */
public interface HasExternalDependencies {

  /**
   * @return A {@link Set} of {@link ExternalDependencyModel} which represent the extension's external dependencies
   */
  Set<ExternalDependencyModel> getExternalDependenciesModels();
}
