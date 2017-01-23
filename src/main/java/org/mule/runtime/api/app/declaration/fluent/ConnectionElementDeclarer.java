/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.app.declaration.fluent;

import org.mule.runtime.api.app.declaration.ConnectionElementDeclaration;

/**
 * Allows configuring an {@link ConnectionElementDeclaration} through a fluent API
 *
 * @since 1.0
 */
public final class ConnectionElementDeclarer
    extends ParameterizedElementDeclarer<ConnectionElementDeclarer, ConnectionElementDeclaration> {

  /**
   * Creates a new instance
   */
  ConnectionElementDeclarer(ConnectionElementDeclaration declaration) {
    super(declaration);
  }

}
