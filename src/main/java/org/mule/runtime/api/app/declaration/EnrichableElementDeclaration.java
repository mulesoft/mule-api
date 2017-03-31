/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.app.declaration;

import static java.util.Collections.unmodifiableMap;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * A programmatic descriptor of an {@link ElementDeclaration} that can
 * be enriched with custom properties and parameters.
 *
 * @since 1.0
 */
public abstract class EnrichableElementDeclaration extends ElementDeclaration implements EnrichableDeclaration {

  private List<ParameterElementDeclaration> customParameters = new LinkedList<>();
  private Map<String, Serializable> properties = new HashMap<>();

  public EnrichableElementDeclaration() {}

  /**
   * {@inheritDoc}
   */
  public List<ParameterElementDeclaration> getCustomConfigurationParameters() {
    return customParameters;
  }

  /**
   * {@inheritDoc}
   */
  public void addCustomConfigurationParameter(ParameterElementDeclaration customParameter) {
    this.customParameters.add(customParameter);
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (!(o instanceof EnrichableElementDeclaration) || !super.equals(o)) {
      return false;
    }

    EnrichableElementDeclaration that = (EnrichableElementDeclaration) o;
    return customParameters.equals(that.customParameters) && properties.equals(that.properties);
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + properties.hashCode();
    result = 31 * result + customParameters.hashCode();
    return result;
  }

}
