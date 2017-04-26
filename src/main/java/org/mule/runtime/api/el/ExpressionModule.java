/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.el;


import org.mule.runtime.api.metadata.TypedValue;

import java.util.Collection;
import java.util.Optional;

/**
 * Represents a collection of bindings under a given namespace.
 *
 * @since 1.0
 */
public interface ExpressionModule {

  /**
   * Provides a builder to create {@link ExpressionModule} objects.
   *
   * @param namespace The namespace of the module to be built.
   * @return a new {@link ExpressionModule.Builder}.
   */
  static ExpressionModule.Builder builder(ModuleNamespace namespace) {
    return AbstractExpressionModuleBuilderFactory.getDefaultFactory().create(namespace);
  }

  /**
   * Returns all bindings found.
   *
   * @return a {@link Collection} of all {@link Binding}s in the module
   */
  Collection<Binding> bindings();

  /**
   * Returns all identifiers found.
   *
   * @return a {@link Collection} of all binding identifiers in the module
   */
  Collection<String> identifiers();

  /**
   * Allows searching for a specific binding by its identifier.
   *
   * @param identifier the variable or function name to lookup
   * @return an {@link Optional} of the associated {@link TypedValue} found or an empty one.
   */
  Optional<TypedValue> lookup(String identifier);

  /**
   * Returns the namespace of this module
   * @return The namespace
   */
  ModuleNamespace namespace();


  interface Builder {

    /**
     * Will create a binding for the specified identifier and value.
     *
     * @param value      the value to bind
     * @param identifier the keyword to use in the EL to access the {@code value}
     */
    ExpressionModule.Builder addBinding(String identifier, TypedValue<?> value);

    ExpressionModule build();
  }
}
