/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.app.declaration;

import static org.mule.runtime.api.component.location.Location.CONNECTION;
import static org.mule.runtime.api.util.Preconditions.checkArgument;
import org.mule.runtime.api.component.location.Location;
import org.mule.runtime.api.meta.model.config.ConfigurationModel;

import java.util.List;
import java.util.Optional;

/**
 * A programmatic descriptor of a {@link ConfigurationModel} configuration.
 *
 * @since 1.0
 */
public final class ConfigurationElementDeclaration extends ParameterizedElementDeclaration
    implements GlobalElementDeclaration {

  private ConnectionElementDeclaration connection;
  private String elementName;

  public ConfigurationElementDeclaration() {}

  public ConfigurationElementDeclaration(String extension, String name) {
    setDeclaringExtension(extension);
    setName(name);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getRefName() {
    return elementName;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setRefName(String referableName) {
    checkArgument(referableName != null && !referableName.trim().isEmpty(),
                  "Configuration referableName cannot be blank");
    this.elementName = referableName;
  }

  /**
   * @return the {@link ConnectionElementDeclaration} of {@code this} configurat
   */
  public Optional<ConnectionElementDeclaration> getConnection() {
    return Optional.ofNullable(connection);
  }

  public void setConnection(ConnectionElementDeclaration connection) {
    this.connection = connection;
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

    if (parts.get(0).equals(CONNECTION)) {
      return Optional.ofNullable((T) connection);
    }

    return super.findElement(parts);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (!(o instanceof ConfigurationElementDeclaration) || !super.equals(o)) {
      return false;
    }

    ConfigurationElementDeclaration that = (ConfigurationElementDeclaration) o;
    return (connection != null ? connection.equals(that.connection) : that.connection == null) &&
        elementName.equals(that.elementName);
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + (connection != null ? connection.hashCode() : 0);
    result = 31 * result + elementName.hashCode();
    return result;
  }

  @Override
  public void accept(GlobalElementDeclarationVisitor visitor) {
    visitor.visit(this);
  }
}
