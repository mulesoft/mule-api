/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.metadata.descriptor;

import org.mule.runtime.api.meta.model.ComponentModel;
import org.mule.runtime.api.metadata.MetadataAttributes;

/**
 * Immutable concrete implementation of {@link ComponentMetadataDescriptor}
 *
 * @since 1.0
 */
public final class ComponentMetadataDescriptor<T extends ComponentModel> {

  private final T model;
  private final MetadataAttributes metadataAttributes;

  private ComponentMetadataDescriptor(T model, MetadataAttributes metadataAttributes) {
    this.model = model;
    this.metadataAttributes = metadataAttributes;
  }

  public static <T extends ComponentModel> ComponentMetadataDescriptorBuilder<T> builder(T model) {
    return new ComponentMetadataDescriptorBuilder<>(model);
  }

  public T getModel() {
    return model;
  }

  public MetadataAttributes getMetadataAttributes() {
    return metadataAttributes;
  }

  /**
   * Implementation of the builder design pattern to create instances of {@link ComponentMetadataDescriptor}
   *
   * @since 1.0
   */
  public static class ComponentMetadataDescriptorBuilder<T extends ComponentModel> {

    private T model;
    private MetadataAttributes metadataAttributes;

    /**
     * Creates a new instance of {@link ComponentMetadataDescriptorBuilder} with the name of the component to describe
     *
     * @param model {@link ComponentModel} to describe its metadata.
     */
    private ComponentMetadataDescriptorBuilder(T model) {
      this.model = model;
    }

    public ComponentMetadataDescriptorBuilder<T> withAttributes(MetadataAttributes metadataAttributes) {
      this.metadataAttributes = metadataAttributes;
      return this;
    }

    /**
     * @return a {@link ComponentMetadataDescriptor} instance with the metadata description for the content, output, and type of
     *         each of the parameters of the Component
     * @throws IllegalArgumentException if the {@link OutputMetadataDescriptor} was not set during building
     */
    public ComponentMetadataDescriptor<T> build() {
      return new ComponentMetadataDescriptor<>(model, metadataAttributes);
    }
  }
}
