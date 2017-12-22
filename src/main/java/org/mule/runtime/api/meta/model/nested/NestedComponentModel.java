/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model.nested;

import org.mule.runtime.api.meta.model.ComponentModel;
import org.mule.runtime.api.meta.model.stereotype.StereotypeModel;

import java.util.Set;

/**
 * Represents a {@link NestableElementModel} that makes reference to a single {@link ComponentModel}.
 * <p>
 * Do not create custom implementations of this interface. The Mule Runtime should be
 * the only one providing implementations of it.
 *
 * @since 1.0
 */
public interface NestedComponentModel extends NestableElementModel {

  /**
   * @return a {@link Set} with the {@link StereotypeModel}s that can be assigned to this nested element.
   */
  Set<StereotypeModel> getAllowedStereotypes();

  /**
   * Accepts a {@link NestableElementModelVisitor}
   *
   * @param visitor a {@link NestableElementModelVisitor}
   */
  void accept(NestableElementModelVisitor visitor);
}
