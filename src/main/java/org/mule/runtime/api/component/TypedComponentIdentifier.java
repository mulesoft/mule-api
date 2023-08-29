/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.component;

import org.mule.api.annotation.NoImplement;
import org.mule.runtime.api.interception.ProcessorInterceptorFactory;
import org.mule.runtime.api.message.Message;

/**
 * Descriptor for a component in the configuration along with it's {@link ComponentType}.
 *
 * @since 1.0
 */
@NoImplement
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
     * Executes a single nested component chains adding common behaviour.
     */
    SCOPE,

    /**
     * Contains different routes and executes them based on a routing strategy.
     */
    ROUTER,

    /**
     * Wraps the next defined component, controlling its invocation.
     *
     * @deprecated Intercepting processors are a Mule 3 feature that is deprecated in Mule 4. Use interception API instead (ref:
     *             {@link ProcessorInterceptorFactory}).
     */
    @Deprecated
    INTERCEPTING,

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
    UNKNOWN,

    /**
     * One of the options to be executed that belong to a {@link ComponentType#ROUTER}
     */
    ROUTE,

    /**
     * The chain contains the processors for the component definition of its parent.
     *
     * @since 1.4
     */
    CHAIN,

    /**
     * A definition to be used by sources and operations.
     *
     * @since 1.5
     */
    CONFIG,

    /**
     * A connection provider within a {@link #CONFIG}.
     *
     * @since 1.5
     */
    CONNECTION,

    /**
     * Definition of an operation though the Mule DSL
     *
     * @since 1.5
     */
    OPERATION_DEF,

    /**
     * The output payload definition of a Mule DSL operation or source
     *
     * @since 1.5
     */
    OUTPUT_PAYLOAD_TYPE,

    /**
     * The output attributes definition of a Mule DSL operation or source
     *
     * @since 1.5
     */
    OUTPUT_ATTRIBUTES_TYPE
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
    Builder identifier(ComponentIdentifier componentIdentifier);

    /**
     * @param componentType the type of the component
     * @return {@code this} builder
     */
    Builder type(ComponentType componentType);

    /**
     * @return a new instance of {@link TypedComponentIdentifier}
     */
    TypedComponentIdentifier build();

  }
}
