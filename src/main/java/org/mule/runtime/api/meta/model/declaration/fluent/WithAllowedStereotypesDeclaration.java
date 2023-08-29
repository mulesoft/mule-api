/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model.declaration.fluent;

import org.mule.api.annotation.NoImplement;
import org.mule.runtime.api.meta.model.stereotype.StereotypeModel;

import java.util.Set;

/**
 * Contract interface for a {@link BaseDeclaration} in which it's possible to add/get {@link StereotypeModel} objects
 *
 * @since 1.0
 */
@NoImplement
public interface WithAllowedStereotypesDeclaration<T> {

  /**
   * @return a {@link Set} with the {@link StereotypeModel}s that can be assigned to this nested element.
   */
  Set<StereotypeModel> getAllowedStereotypes();

  /**
   * @param stereotype an {@link StereotypeModel} that is allowed to exist as part of {@code this} component
   */
  T addAllowedStereotype(StereotypeModel stereotype);

}
