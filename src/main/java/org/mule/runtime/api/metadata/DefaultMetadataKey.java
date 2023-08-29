/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.metadata;

import static java.util.Collections.unmodifiableMap;
import static java.util.Collections.unmodifiableSet;
import static java.util.Objects.hash;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toMap;

import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * Default immutable implementation for the {@link MetadataKey}.
 *
 * @since 1.0
 */
public final class DefaultMetadataKey implements MetadataKey {

  private static final String LIST_SEPARATOR = ", ";

  private final String id;
  private final String displayName;
  private final String partName;
  private final Map<Class<? extends MetadataProperty>, MetadataProperty> properties;
  private final Set<MetadataKey> childs;

  protected DefaultMetadataKey(String id, String displayName, Set<MetadataProperty> properties, Set<MetadataKey> childs,
                               String partName) {
    this.id = id;
    this.displayName = displayName;
    this.childs = childs;
    this.properties = unmodifiableMap(properties.stream().collect(toMap(MetadataProperty::getClass, p -> p)));
    this.partName = partName;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getId() {
    return id;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getDisplayName() {
    return displayName;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Set<MetadataKey> getChilds() {
    return unmodifiableSet(childs);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getPartName() {
    return partName;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <T extends MetadataProperty> Optional<T> getMetadataProperty(Class<T> propertyType) {
    return ofNullable((T) properties.get(propertyType));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Set<MetadataProperty> getProperties() {
    return unmodifiableSet(new HashSet<>(properties.values()));
  }

  @Override
  public int hashCode() {
    return hash(id, displayName, partName, properties, childs);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null || !obj.getClass().equals(DefaultMetadataKey.class)) {
      return false;
    }

    DefaultMetadataKey that = (DefaultMetadataKey) obj;
    return Objects.equals(id, that.id)
        && Objects.equals(displayName, that.displayName)
        && Objects.equals(partName, that.partName)
        && Objects.equals(properties, that.properties)
        && Objects.equals(childs, that.childs);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    return sb
        .append("{")
        .append("id: ").append(id).append(LIST_SEPARATOR)
        .append("displayName: ").append(displayName).append(LIST_SEPARATOR)
        .append("partName: ").append(partName).append(LIST_SEPARATOR)
        .append("childs: ").append(childs).append(LIST_SEPARATOR)
        .append("properties: ")
        .append(properties.values().stream().map(MetadataProperty::getName).collect(joining(LIST_SEPARATOR)))
        .append("}")
        .toString();
  }
}
