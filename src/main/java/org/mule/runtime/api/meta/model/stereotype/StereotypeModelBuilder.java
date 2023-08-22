/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model.stereotype;

/**
 * Builder pattern implementation to build {@link StereotypeModel} instances.
 *
 * @since 1.0
 */
public final class StereotypeModelBuilder {

  private final String name;
  private StereotypeModel parent;
  private String namespace;

  private StereotypeModelBuilder(String name, String namespace) {
    this.name = name;
    this.namespace = namespace;
  }

  /**
   * Creates a builder to be able to create {@link StereotypeModel} instances
   *
   * @param typeName  The type of the {@link StereotypeModel} to create.
   * @param namespace adds a namespace to the {@link StereotypeModel} that is being built
   * @return An {@link StereotypeModelBuilder} initialized with the {@code typeName}
   */
  public static StereotypeModelBuilder newStereotype(String typeName, String namespace) {
    return new StereotypeModelBuilder(typeName, namespace);
  }

  /**
   * @param parent {@link StereotypeModel} of the one that is being built
   * @return the contributed {@link StereotypeModelBuilder}
   */
  public StereotypeModelBuilder withParent(StereotypeModel parent) {
    this.parent = parent;
    return this;
  }

  /**
   * @return a new {@link StereotypeModel} instance
   */
  public StereotypeModel build() {
    return new ImmutableStereotypeModel(name, namespace, parent);
  }

}
