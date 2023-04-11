/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.metadata;

import static java.util.stream.Collectors.toCollection;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;

import org.mule.api.annotation.NoExtend;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * An implementation of the builder design pattern to create a new {@link MetadataKey} instance.
 *
 * @since 1.0
 */
@NoExtend
public class MetadataKeyBuilder {

  private final Set<MetadataProperty> properties = new HashSet<>();
  private final String id;
  private String displayName;
  private final Set<MetadataKey> childs = new LinkedHashSet<>();
  private final Set<MetadataKeyBuilder> unbuiltChilds = new LinkedHashSet<>();
  private String partName = "";

  protected MetadataKeyBuilder(String id) {
    this.id = id;
  }

  /**
   * Creates and returns new instance of a {@link MetadataKeyBuilder}, to help building a new {@link MetadataKey} represented by
   * the given {@param id}
   *
   * @param id of the {@link MetadataKey} to be created
   * @return an initialized instance of {@link MetadataKeyBuilder}
   */
  public static MetadataKeyBuilder newKey(String id) {
    return new MetadataKeyBuilder(id);
  }

  /**
   * Adds a display name to the {@link MetadataKey} that is being built
   *
   * @param displayName of the {@link MetadataKey} to be created
   * @return {@code this} builder with the configured display name
   */
  public MetadataKeyBuilder withDisplayName(String displayName) {
    this.displayName = displayName;
    return this;
  }

  /**
   * Adds a custom {@link MetadataProperty} to the {@link MetadataKey} that is being built
   *
   * @param property The new {@link MetadataProperty} to be added into the {@link MetadataKey}
   * @return {@code this} builder with the new property
   */
  public MetadataKeyBuilder withProperty(MetadataProperty property) {
    if (properties.stream().anyMatch(p -> p.getClass().equals(property.getClass()))) {
      throw new IllegalArgumentException(String.format("The key %s already contains a metadata property of type %s", id,
                                                       property.getClass().getName()));
    }

    properties.add(property);
    return this;
  }

  /**
   * Adds a new {@link MetadataKeyBuilder} child to the {@link MetadataKey} that is being built.
   *
   * @param metadataKeyBuilder the {@link MetadataKeyBuilder} that is used to create the instance of the new child.
   * @return {@code this} builder with a new child.
   */
  public MetadataKeyBuilder withChild(MetadataKeyBuilder metadataKeyBuilder) {
    unbuiltChilds.add(metadataKeyBuilder);
    return this;
  }

  /**
   * Adds a new {@link MetadataKey} child to the {@link MetadataKey} that is being built.
   *
   * @param metadataKey the {@link MetadataKey} that is used to create the instance of the new child.
   * @return {@code this} builder with a new child.
   */
  public MetadataKeyBuilder withChild(MetadataKey metadataKey) {
    childs.add(metadataKey);
    return this;
  }

  /**
   * Adds a new {@link MetadataKeyBuilder} child to the {@link MetadataKey} that is being built.
   *
   * @param partName the {@code partName} that is associated to this key part.
   * @return {@code this} builder with a new child.
   */
  public MetadataKeyBuilder withPartName(String partName) {
    setPartName(partName);
    return this;
  }

  protected void setPartName(String partName) {
    this.partName = partName;
  }

  /**
   * Builds a new instance of {@link MetadataKey}.
   *
   * @return an initialized {@link MetadataKey}
   */
  public MetadataKey build() {
    String name = isNotEmpty(displayName) ? displayName : id;
    childs.addAll(unbuiltChilds.stream().map(MetadataKeyBuilder::build).collect(toCollection(LinkedHashSet::new)));
    return new DefaultMetadataKey(id, name, properties, childs, partName);
  }
}
