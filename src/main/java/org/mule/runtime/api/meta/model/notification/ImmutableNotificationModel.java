/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model.notification;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;
import org.mule.metadata.api.model.MetadataType;

/**
 * Default and immutable implementation of {@link NotificationModel}
 *
 * @since 1.1
 */
public class ImmutableNotificationModel implements NotificationModel {

  private final String namespace;
  private final String identifier;
  private final MetadataType metadataType;

  public ImmutableNotificationModel(String namespace, String identifier, MetadataType metadataType) {
    this.namespace = namespace;
    this.identifier = identifier;
    this.metadataType = metadataType;
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
  public String getIdentifier() {
    return identifier;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public MetadataType getDataType() {
    return metadataType;
  }

  @Override
  public boolean equals(Object o) {
    return reflectionEquals(this, o);
  }

  @Override
  public int hashCode() {
    return reflectionHashCode(this);
  }
}
