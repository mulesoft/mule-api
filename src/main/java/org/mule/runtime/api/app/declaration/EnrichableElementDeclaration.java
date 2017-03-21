/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.app.declaration;

import static java.util.Collections.unmodifiableMap;

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
public abstract class EnrichableElementDeclaration extends ElementDeclaration {

  private List<ParameterElementDeclaration> customParameters = new LinkedList<>();
  private Map<String, Object> properties = new HashMap<>();

  public EnrichableElementDeclaration() {}

  /**
   * @return the {@link List} of {@link ParameterElementDeclaration parameters} associated with
   * {@code this} 
   */
  public List<ParameterElementDeclaration> getCustomParameters() {
    return customParameters;
  }

  /**
   * Adds a {@link ParameterElementDeclaration custom parameter} to {@code this} enrichable element declaration.
   * This {@code customParameter} represents an additional parameter to the ones exposed by the actual model
   * associated to this {@link ElementDeclaration element}.
   * No validation of any kind will be performed over this {@code customParameter} and its value.
   *
   * @param customParameter the {@link ParameterElementDeclaration} to associate to {@code this} element declaration
   */
  public void addCustomParameter(ParameterElementDeclaration customParameter) {
    this.customParameters.add(customParameter);
  }

  /**
   * @param name the name of the property
   * @return the property for the given name, or {@link Optional#empty()} if none was found.
   */
  public Optional<Object> getProperty(String name) {
    return Optional.ofNullable(properties.get(name));
  }

  /**
   * @return the metadata properties associated to this {@link EnrichableElementDeclaration}
   */
  public Map<String, Object> getProperties() {
    return unmodifiableMap(properties);
  }


  /**
   * Adds a property to the {@link ElementDeclaration}.
   * This property is meant to hold only metadata of the declaration.
   *
   * @param name custom attribute name.
   * @param value custom attribute value.
   * @return the builder.
   */
  public void addProperty(String name, Object value) {
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
