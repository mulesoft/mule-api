/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.meta.model.declaration.fluent;

import org.mule.api.annotation.NoImplement;
import org.mule.runtime.api.meta.model.stereotype.StereotypeModel;

/**
 * Contract interface for a {@link BaseDeclaration} in which it's possible to add/get {@link StereotypeModel} objects
 *
 * @since 1.0
 */
@NoImplement
public interface WithStereotypesDeclaration {

  /**
   * @return {@code this} components {@link StereotypeModel}
   */
  StereotypeModel getStereotype();

  /**
   * @param stereotype {@code this} components {@link StereotypeModel}
   */
  void withStereotype(StereotypeModel stereotype);

}
