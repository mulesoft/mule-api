/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model.stereotype;

import static java.util.Optional.ofNullable;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;
import static org.mule.runtime.api.util.Preconditions.checkArgument;

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

  public ImmutableStereotypeModel() {}

  ImmutableStereotypeModel(String name, String namespace, StereotypeModel parent) {
    checkArgument(isNotBlank(name), "An stereotype name is required");
    checkArgument(isNotBlank(namespace), "An stereotype namespace is required");

    this.type = name;
    this.namespace = namespace;
    this.parent = parent;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getName() {
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

  @Override
  public boolean equals(Object o) {
    return reflectionEquals(this, o);
  }

  @Override
  public int hashCode() {
    return reflectionHashCode(this);
  }

  @Override
  public String toString() {
    return namespace + ":" + type + (parent == null ? "" : "(" + parent.toString() + ")");
  }
}
