/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model.error;

/**
 * Builder pattern implementation to build {@link ErrorModel} instances.
 *
 * @since 1.0
 */
public final class ErrorModelBuilder {

  private final String name;
  private ErrorModel parent;
  private String namespace;

  private ErrorModelBuilder(String name, String namespace) {
    this.name = name;
    this.namespace = namespace;
  }

  /**
   * Creates a builder to be able to create {@link ErrorModel} instances
   *
   * @param typeName  The type of the {@link ErrorModel} to create.
   * @param namespace adds a namespace to the {@link ErrorModel} that is being built
   * @return An {@link ErrorModelBuilder} initialized with the {@code typeName}
   */
  public static ErrorModelBuilder newError(String typeName, String namespace) {
    return new ErrorModelBuilder(typeName, namespace);
  }

  /**
   * @param parent {@link ErrorModel} of the one that is being built
   * @return the contributed {@link ErrorModelBuilder}
   */
  public ErrorModelBuilder withParent(ErrorModel parent) {
    this.parent = parent;
    return this;
  }

  /**
   * @return a new {@link ErrorModel} instance
   */
  public ErrorModel build() {
    return new ImmutableErrorModel(name, namespace, parent);
  }
}
