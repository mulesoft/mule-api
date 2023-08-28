/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
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
