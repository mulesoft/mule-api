/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.component;

import org.mule.runtime.api.message.Message;

/**
 * Descriptor for a component in the configuration along with it's {@link ComponentType}.
 * 
 * @since 1.0
 */
public interface TypedComponentIdentifier {

  /**
   * Declares different types of component based on its common characteristics.
   */
  enum ComponentType {

    /**
     * Flow component type
     */
    FLOW,

    /**
     * Receives something from an external system, transforms it into a {@link Message} and vice-versa.
     */
    SOURCE,

    /**
     * Executes an operation defined in an extension.
     */
    OPERATION,

    /**
     * Executes one or many nested component chains.
     */
    ROUTER,

    /**
     * Wraps the next defined component, controlling its invocation.
     */
    INTERCEPTING,

    /**
     * Regular component that doesn't match any of the other criterias.
     */
    PROCESSOR,

    /**
     * Error handler component type
     */
    ERROR_HANDLER,

    /**
     * Error handler on error child component type
     */
    ON_ERROR,

    /**
     * Component type for components that cannot be categorized in the other component types
     */
    UNKNOWN
  }

  /**
   * @return the type that represents the kind of the identified component.
   */
  ComponentType getType();

  /**
   * @return the component identifier
   */
  ComponentIdentifier getIdentifier();

  static Builder builder() {
    return new DefaultTypedComponentIdentifier.Builder();
  }

  /**
   * Builder interface for {@link TypedComponentIdentifier}.
   * 
   * @since 1.0
   */
  interface Builder {

    /**
     * @param componentIdentifier the identifier of the component
     * @return {@code this} builder
     */
    Builder withIdentifier(ComponentIdentifier componentIdentifier);

    /**
     * @param componentType the type of the component
     * @return {@code this} builder
     */
    Builder withType(ComponentType componentType);

    /**
     * @return a new instance of {@link TypedComponentIdentifier}
     */
    TypedComponentIdentifier build();

  }
}
