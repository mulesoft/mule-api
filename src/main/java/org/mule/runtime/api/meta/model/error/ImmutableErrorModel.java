/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model.error;

import static java.util.Optional.ofNullable;
import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;

import java.util.Optional;

/**
 * Default and immutable implementation of {@link ErrorModel}
 *
 * @since 1.0
 */
public final class ImmutableErrorModel implements ErrorModel {

  private final String type;
  private final String namespace;
  private final ErrorModel parent;

  public ImmutableErrorModel(String type, String namespace, ErrorModel parent) {
    this.type = type;
    this.namespace = namespace;
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
  public Optional<ErrorModel> getParent() {
    return ofNullable(parent);
  }

  @Override
  public int hashCode() {
    return reflectionHashCode(this);
  }

  @Override
  public boolean equals(Object obj) {
    return reflectionEquals(this, obj);
  }

  @Override
  public String toString() {
    return namespace + ":" + type;
  }
}
