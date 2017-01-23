/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.app.declaration.fluent;

import org.mule.runtime.api.app.declaration.ConfigurationElementDeclaration;
import org.mule.runtime.api.app.declaration.ConnectionElementDeclaration;

/**
 * Allows configuring an {@link ConfigurationElementDeclaration} through a fluent API
 *
 * @since 1.0
 */
public final class ConfigurationElementDeclarer
    extends ParameterizedElementDeclarer<ConfigurationElementDeclarer, ConfigurationElementDeclaration> {

  /**
   * Creates a new instance
   */
  ConfigurationElementDeclarer(ConfigurationElementDeclaration declaration) {
    super(declaration);
  }

  public ConfigurationElementDeclarer withConnection(ConnectionElementDeclaration connection) {
    declaration.setConnection(connection);
    return this;
  }

  public ConfigurationElementDeclarer withRefName(String name) {
    declaration.setRefName(name);
    return this;
  }

}
