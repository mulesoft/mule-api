/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.app.declaration;

import static java.lang.Integer.parseInt;
import static java.util.Optional.empty;
import static org.apache.commons.lang3.StringUtils.isNumeric;
import static org.mule.runtime.api.component.location.Location.PARAMETERS;
import org.mule.runtime.api.component.location.Location;
import org.mule.runtime.api.meta.model.parameter.ParameterizedModel;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * A programmatic descriptor of a {@link ParameterizedModel} configuration.
 *
 * @since 1.0
 */
public final class ParameterGroupElementDeclaration extends EnrichableElementDeclaration
    implements ElementDeclarationContainer {

  private List<ParameterElementDeclaration> parameters = new LinkedList<>();

  public ParameterGroupElementDeclaration() {}

  public ParameterGroupElementDeclaration(String name) {
    setName(name);
  }

  /**
   * @return the {@link List} of {@link ParameterElementDeclaration parameters} associated with
   * {@code this} 
   */
  public List<ParameterElementDeclaration> getParameters() {
    return parameters;
  }

  /**
   * @return the {@link ParameterElementDeclaration parameters} associated with the given {@code name}
   * or {@link Optional#empty()} otherwise
   */
  public Optional<ParameterElementDeclaration> getParameter(String name) {
    return parameters.stream().filter(p -> p.getName().equals(name)).findFirst();
  }

  /**
   * Adds a {@link ParameterElementDeclaration parameter} to {@code this} parametrized element declaration
   *
   * @param parameter the {@link ParameterElementDeclaration} to associate to {@code this} element declaration
   */
  public void addParameter(ParameterElementDeclaration parameter) {
    this.parameters.add(parameter);
  }

  /**
   * Looks for a {@link ParameterElementDeclaration} contained by {@code this} declaration
   * based on the {@code parts} of a {@link Location}.
   *
   * @param parts the {@code parts} of a {@link Location} relative to {@code this} element
   * @return the {@link ElementDeclaration} located in the path created by the {@code parts}
   * or {@link Optional#empty()} if no {@link ElementDeclaration} was found in that location.
   */
  @Override
  public <T extends ElementDeclaration> Optional<T> findElement(List<String> parts) {
    if (parts.isEmpty()) {
      return Optional.of((T) this);
    }

    if (isParameterLocation(parts)) {
      String identifier = parts.get(1);
      if (isNumeric(identifier) && parseInt(identifier) < parameters.size()) {
        return Optional.of((T) parameters.get(parseInt(identifier)));
      } else {
        return (Optional<T>) parameters.stream().filter(p -> p.getName().equals(identifier)).findFirst();
      }
    }

    return empty();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setDeclaringExtension(String declaringExtension) {
    super.setDeclaringExtension(declaringExtension);
    parameters.forEach(p -> p.setDeclaringExtension(declaringExtension));
  }

  private boolean isParameterLocation(List<String> parts) {
    return parts.get(0).equals(PARAMETERS) && parts.size() == 2;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (!(o instanceof ParameterGroupElementDeclaration) || !super.equals(o)) {
      return false;
    }

    ParameterGroupElementDeclaration that = (ParameterGroupElementDeclaration) o;
    return parameters.equals(that.parameters);
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + parameters.hashCode();
    return result;
  }
}
