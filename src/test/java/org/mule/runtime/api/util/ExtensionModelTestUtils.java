/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.util;

import static org.mockito.Mockito.doAnswer;
import org.mule.runtime.api.meta.model.ComponentModel;
import org.mule.runtime.api.meta.model.ExtensionModel;
import org.mule.runtime.api.meta.model.operation.OperationModel;
import org.mule.runtime.api.meta.model.operation.RouterModel;
import org.mule.runtime.api.meta.model.operation.ScopeModel;
import org.mule.runtime.api.meta.model.source.SourceModel;
import org.mule.runtime.api.meta.model.util.ComponentModelVisitor;

import org.mockito.Mockito;

/**
 * Utility methods for test cases which revolver around a {@link ExtensionModel}
 *
 * @since 1.0
 */
public final class ExtensionModelTestUtils {

  /**
   * Makes the {@link ComponentModel#accept(ComponentModelVisitor)} method work
   * on the given {@code components} mocks
   *
   * @param components an array of mock {@link ComponentModel components}
   */
  public static void visitableMock(ComponentModel... components) {
    for (ComponentModel component : components) {
      doAnswer(invocation -> {
        ComponentModelVisitor visitor = (ComponentModelVisitor) invocation.getArguments()[0];
        if (component instanceof ScopeModel) {
          visitor.visit((ScopeModel) component);
        } else if (component instanceof RouterModel) {
          visitor.visit((RouterModel) component);
        } else if (component instanceof OperationModel) {
          visitor.visit((OperationModel) component);
        } else if (component instanceof SourceModel) {
          visitor.visit((SourceModel) component);
        } else {
          throw new IllegalArgumentException("Unsupported visitable mock of class " + component.getClass().getName());
        }
        return null;
      }).when(component).accept(Mockito.any(ComponentModelVisitor.class));
    }
  }

  private ExtensionModelTestUtils() {}
}
