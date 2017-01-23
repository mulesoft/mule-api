/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.dsl;

import org.mule.metadata.api.model.ObjectType;
import org.mule.runtime.api.meta.model.ExtensionModel;
import org.mule.runtime.api.meta.type.TypeCatalog;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

/**
 * Context used to discover the {@link ExtensionModel}s required to generate
 * the DSL related views of a given component.
 *
 * @since 1.0
 */
public interface DslResolvingContext {

  /**
   * Provides the default implementation of {@link DslResolvingContext} based on
   * a given {@link Set} of {@link ExtensionModel}
   *
   * @param extensions the {@link Set} of {@link ExtensionModel} available in
   *                         {@code this} {@link DslResolvingContext}
   * @return an instance of a {@link  DslResolvingContext} default implementation
   */
  static DslResolvingContext getDefault(Set<ExtensionModel> extensions) {
    return new DefaultDslResolvingContext(extensions);
  }

  /**
   * Returns an {@link Optional} {@link ExtensionModel} which
   * name equals the provided {@code name}.
   *
   * @param name the name of the extensions you want.
   * @return an {@link Optional}. It will be empty if the {@link ExtensionModel} is not
   * found in the context.
   */
  Optional<ExtensionModel> getExtension(String name);

  /**
   * Returns a {@link Set} containing all the {@link ExtensionModel}s available in the
   * current resolving context.
   * <p>
   * Any {@link ExtensionModel} that can be found using the {@link #getExtension} method
   * should be contained in the returned {@link Set}
   *
   * @return a {@link Set} containing all the {@link ExtensionModel}s available in the
   * current resolving context
   */
  Collection<ExtensionModel> getExtensions();

  /**
   * Returns a {@link TypeCatalog} containing all the {@link ObjectType}s available in the
   * current resolving context.
   * <p>
   * Any {@link ObjectType} that can be found using the {@link ExtensionModel#getTypes} method
   * for any of the {@link ExtensionModel extensions} in the context should available in
   * the provided {@link TypeCatalog}
   *
   * @return a {@link TypeCatalog} containing all the {@link ObjectType}s available in the
   * current resolving context.
   */
  TypeCatalog getTypeCatalog();
}
