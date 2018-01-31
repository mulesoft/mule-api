/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model.construct;

import org.mule.api.annotation.NoImplement;
import org.mule.runtime.api.meta.model.ComponentModel;
import org.mule.runtime.api.meta.model.ComponentModelVisitor;
import org.mule.runtime.api.meta.model.ConnectableComponentModel;

/**
 * An specialization of a {@link ComponentModel} that is not executable as an standalone component and depends
 * on the composition with {@link ConnectableComponentModel}s in order to act in an application.
 *
 * @since 1.0
 */
@NoImplement
public interface ConstructModel extends ComponentModel {

  /**
   * @return whether or not {@code this} model can be declared as a root component in the application.
   */
  boolean allowsTopLevelDeclaration();

  /**
   * Accepts a {@link ComponentModelVisitor}
   *
   * @param visitor a {@link ComponentModelVisitor}
   */
  default void accept(ComponentModelVisitor visitor) {
    visitor.visit(this);
  }
}
