/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.meta.model.error;

import org.mule.runtime.api.component.ComponentIdentifier;

/**
 * Builder pattern implementation to build {@link ErrorModel} instances.
 *
 * @since 1.0
 */
public final class ErrorModelBuilder {

  private final String name;
  private ErrorModel parent;
  private boolean handleable = true;
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
   * Creates a builder to be able to create {@link ErrorModel} instances from an error {@link ComponentIdentifier}
   *
   * @param identifier The identifier of the error to create.
   * @return An {@link ErrorModelBuilder} initialized with the identifiers name and namespace
   */
  public static ErrorModelBuilder newError(ComponentIdentifier identifier) {
    return newError(identifier.getName(), identifier.getNamespace());
  }

  /**
   * Specifies if the error can be handled through an error handler or not. If not specified, {@code true} will be assumed
   *
   * @param handleable whether the error can be handled through an error handler or not.
   * @return the contributed {@link ErrorModelBuilder}
   * @since 1.1
   */
  public ErrorModelBuilder handleable(boolean handleable) {
    this.handleable = handleable;
    return this;
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
    return new ImmutableErrorModel(name, namespace, handleable, parent);
  }
}
