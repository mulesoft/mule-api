/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.app.declaration;

import static java.util.Collections.unmodifiableMap;
import static org.mule.runtime.api.util.Preconditions.checkArgument;
import org.mule.runtime.api.meta.model.parameter.ParameterModel;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * A programmatic descriptor of a {@link ParameterModel} configuration.
 *
 * @since 1.0
 */
public final class ParameterElementDeclaration extends ElementDeclaration implements MetadataPropertiesAwareElementDeclaration {

  private ParameterValue value;
  private Map<String, Serializable> properties = new HashMap<>();

  public ParameterElementDeclaration() {}

  public ParameterElementDeclaration(String name) {
    setName(name);
  }

  /**
   * Associates a {@link ParameterValue} as part of {@code this} parameter configuration declaration
   *
   * @param value the {@link ParameterValue} to associate with {@code this} parameter configuration
   */
  public void setValue(ParameterValue value) {
    checkArgument(value != null, "The value of the parameter cannot be null");
    this.value = value;
  }

  /**
   * @return the {@link ParameterValue} configured for {@code this} parameter declaration
   */
  public ParameterValue getValue() {
    return value;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof ParameterElementDeclaration)) {
      return false;
    }

    ParameterElementDeclaration that = (ParameterElementDeclaration) o;
    return name.equals(that.name) && value.equals(that.value);
  }

  @Override
  public int hashCode() {
    int result = name.hashCode();
    result = 31 * result + value.hashCode();
    return result;
  }

  /**
   * {@inheritDoc}
   */
  public Optional<Serializable> getMetadataProperty(String name) {
    return Optional.ofNullable(properties.get(name));
  }

  /**
   * {@inheritDoc}
   */
  public Map<String, Serializable> getMetadataProperties() {
    return unmodifiableMap(properties);
  }

  /**
   * {@inheritDoc}
   */
  public void addMetadataProperty(String name, Serializable value) {
    properties.put(name, value);
  }
}
