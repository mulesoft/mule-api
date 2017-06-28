/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model.declaration.fluent;

import org.mule.runtime.api.meta.model.ExternalDependencyModel;

/**
 * Common interface for {@link Declarer declarers} which support
 * adding {@link ExternalDependencyModel} instances
 *
 * @param <T> the generic type of the {@link Declarer} which implements this interface
 */
public interface DeclaresExternalDependencies<T extends Declarer> {

  /**
   * Adds the given {@code externalDependency}
   *
   * @param externalDependency the {@link ExternalDependencyModel} to be referenced
   * @return {@code this} instance
   */
  T withExternalDependency(ExternalDependencyModel externalDependency);

}
