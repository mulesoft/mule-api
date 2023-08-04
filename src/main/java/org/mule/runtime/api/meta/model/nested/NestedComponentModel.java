/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.meta.model.nested;

import org.mule.api.annotation.NoImplement;
import org.mule.runtime.api.meta.model.ComponentModel;
import org.mule.runtime.api.meta.model.stereotype.StereotypeModel;

import java.util.Set;

/**
 * Represents a {@link NestableElementModel} that makes reference to a single {@link ComponentModel}.
 *
 * @since 1.0
 */
@NoImplement
public interface NestedComponentModel extends NestableElementModel {

  /**
   * @return whether or not {@code this} element is required for its owner element
   */
  boolean isRequired();

  /**
   * @return a {@link Set} with the {@link StereotypeModel}s that can be assigned to this nested element.
   */
  Set<StereotypeModel> getAllowedStereotypes();
}
