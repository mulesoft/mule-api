/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.values;

import static java.util.Collections.unmodifiableSet;
import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;
import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;
import static org.apache.commons.lang3.builder.ToStringStyle.JSON_STYLE;

import java.util.Set;

/**
 * Default implementation of {@link Value}
 *
 * @since 1.0
 */
public class DefaultValue implements Value {

  private final String id;
  private final String displayName;
  private final String partName;
  private final Set<Value> childs;

  DefaultValue(String id, String displayName, Set<Value> childs,
               String partName) {
    this.id = id;
    this.displayName = displayName;
    this.childs = childs;
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
  public Set<Value> getChilds() {
    return unmodifiableSet(childs);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getPartName() {
    return partName;
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
    return reflectionToString(this, JSON_STYLE);
  }
}
