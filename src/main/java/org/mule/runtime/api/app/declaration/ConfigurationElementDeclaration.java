/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.app.declaration;

import static org.mule.runtime.api.util.Preconditions.checkArgument;
import org.mule.runtime.api.meta.model.config.ConfigurationModel;

/**
 * A programmatic descriptor of a {@link ConfigurationModel} configuration.
 *
 * @since 1.0
 */
public final class ConfigurationElementDeclaration extends ParameterizedElementDeclaration
    implements ReferableElementDeclaration, GlobalElementDeclaration {

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

  public ConnectionElementDeclaration getConnection() {
    return connection;
  }

  public void setConnection(ConnectionElementDeclaration connection) {
    this.connection = connection;
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
