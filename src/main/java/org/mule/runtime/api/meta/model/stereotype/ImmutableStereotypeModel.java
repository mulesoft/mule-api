/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.meta.model.stereotype;

import static java.util.Optional.ofNullable;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.mule.runtime.api.util.NameUtils.sanitizeName;
import static org.mule.runtime.api.util.NameUtils.underscorize;
import static org.mule.runtime.api.util.Preconditions.checkArgument;

import java.util.Objects;
import java.util.Optional;

/**
 * Default and immutable implementation of {@link StereotypeModel}
 *
 * @since 1.0
 */
public final class ImmutableStereotypeModel implements StereotypeModel {

  private String type;
  private String namespace;
  private StereotypeModel parent;

  public ImmutableStereotypeModel(String name, String namespace, StereotypeModel parent) {
    checkArgument(isNotBlank(name), "An stereotype name is required");
    checkArgument(isNotBlank(namespace), "An stereotype namespace is required");

    this.type = normalize(name);
    this.namespace = normalize(namespace);
    this.parent = parent;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getType() {
    return type;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getNamespace() {
    return namespace;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Optional<StereotypeModel> getParent() {
    return ofNullable(parent);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isAssignableTo(StereotypeModel other) {
    checkArgument(other != null, "Null is not a valid stereotype");
    boolean assignable = this.equals(other);

    if (!assignable && parent != null) {
      assignable = parent.isAssignableTo(other);
    }

    return assignable;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null || !obj.getClass().equals(ImmutableStereotypeModel.class)) {
      return false;
    }

    ImmutableStereotypeModel that = (ImmutableStereotypeModel) obj;
    return Objects.equals(parent, that.parent)
        && Objects.equals(type, that.type)
        && Objects.equals(namespace, that.namespace);
  }

  @Override
  public int hashCode() {
    return Objects.hash(type, namespace, parent);
  }

  @Override
  public String toString() {
    return namespace + ":" + type + (parent == null ? "" : "(" + parent.toString() + ")");
  }

  private String normalize(String name) {
    return sanitizeName(underscorize(name)).toUpperCase();
  }
}
