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
 * Contains all the binding definitions required by the EL.
 *
 * @since 1.0
 */
public interface BindingContext {

  /**
   * Provides a builder to create {@link BindingContext} objects.
   *
   * @return a new {@link BindingContext.Builder}.
   */
  static Builder builder() {
    return AbstractBindingContextBuilderFactory.getDefaultFactory().create();
  }

  /**
   * Provides a builder to create {@link BindingContext} objects, based on an already existing one.
   *
   * @return a new {@link BindingContext.Builder}.
   */
  static Builder builder(BindingContext context) {
    return AbstractBindingContextBuilderFactory.getDefaultFactory().create(context);
  }

  /**
   * Returns all bindings found.
   *
   * @return a {@link Collection} of all {@link Binding}s in the context
   */
  Collection<Binding> bindings();

  /**
   * Returns all identifiers found.
   *
   * @return a {@link Collection} of all binding identifiers in the context
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
   * Returns all modules
   * @return a {@link Collection} af all modules
   */
  Collection<ExpressionModule> modules();

  interface Builder {

    /**
     * Will create a binding for the specified identifier and value.
     *
     * @param value the value to bind
     * @param identifier the keyword to use in the EL to access the {@code value}
     */
    Builder addBinding(String identifier, TypedValue value);

    /**
     * Will include all bindings in the given {@link BindingContext}.
     *
     * @param context a context whose bindings to add
     */
    Builder addAll(BindingContext context);

    /**
     * Will add a new module to this binding
     * @param expressionModule The module to be added
     */
    Builder addModule(ExpressionModule expressionModule);

    BindingContext build();
  }

}
