/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.meta.model.stereotype;

import org.mule.api.annotation.NoImplement;

/**
 * A model which can be qualified with a set of {@link StereotypeModel}s that correspond to {@code this} model definition.
 *
 * @since 1.0
 */
@NoImplement
public interface HasStereotypeModel {

  /**
   * @return The {@link StereotypeModel stereotypes} which apply to this model. Not null.
   */
  StereotypeModel getStereotype();

}
