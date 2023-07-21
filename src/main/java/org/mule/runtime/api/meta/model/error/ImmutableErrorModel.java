/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.meta.model.error;

import static java.util.Objects.hash;
import static java.util.Optional.ofNullable;

import java.util.Objects;
import java.util.Optional;

/**
 * Default and immutable implementation of {@link ErrorModel}
 *
 * @since 1.0
 */
public final class ImmutableErrorModel implements ErrorModel {

  private final String type;
  private final String namespace;
  private final boolean handleable;
  private final ErrorModel parent;

  /**
   * Creates a new handleable error
   *
   * @param type      the error type
   * @param namespace the error namespace
   * @param parent    the error's parent
   * @deprecated This constructor is deprecated and will be removed in Mule 5. Use
   *             {@link #ImmutableErrorModel(String, String, ErrorModel)} instead
   */
  @Deprecated
  public ImmutableErrorModel(String type, String namespace, ErrorModel parent) {
    this(type, namespace, true, parent);
  }

  /**
   * Creates a new instance
   *
   * @param type       the error type
   * @param namespace  the error namespace
   * @param handleable whether the error can be handled through an error handler or not
   * @param parent     the error's parent
   * @since 1.1
   */
  public ImmutableErrorModel(String type, String namespace, boolean handleable, ErrorModel parent) {
    this.type = type;
    this.namespace = namespace;
    this.handleable = handleable;
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
  public boolean isHandleable() {
    return handleable;
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
    return hash(type, namespace, handleable, parent);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null || !obj.getClass().equals(ImmutableErrorModel.class)) {
      return false;
    }

    ImmutableErrorModel that = (ImmutableErrorModel) obj;
    return Objects.equals(parent, that.parent)
        && Objects.equals(type, that.type)
        && Objects.equals(namespace, that.namespace)
        && Objects.equals(handleable, that.handleable);
  }

  @Override
  public String toString() {
    return namespace + ":" + type;
  }
}
