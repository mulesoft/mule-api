/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.app.declaration.fluent;

import org.mule.runtime.api.app.declaration.FlowElementDeclaration;

/**
 * Allows configuring an {@link FlowElementDeclaration} through a fluent API
 *
 * @since 1.0
 */
public final class FlowElementDeclarer extends ScopeElementDeclarer<FlowElementDeclarer, FlowElementDeclaration> {

  FlowElementDeclarer(FlowElementDeclaration declaration) {
    super(declaration);
  }

}
