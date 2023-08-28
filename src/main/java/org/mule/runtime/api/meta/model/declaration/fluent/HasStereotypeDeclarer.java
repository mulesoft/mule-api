/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model.declaration.fluent;

import org.mule.api.annotation.NoImplement;
import org.mule.runtime.api.meta.model.stereotype.StereotypeModel;

/**
 * Contract interface for a declarer in which it's possible to add {@link StereotypeModel}
 *
 * @since 1.0
 */
@NoImplement
public interface HasStereotypeDeclarer<T> {

  /**
   * Adds the given {@code stereotype}
   *
   * @param stereotype a {@link org.mule.runtime.api.meta.model.stereotype.StereotypeModel}
   * @return {@code this} declarer
   */
  T withStereotype(StereotypeModel stereotype);

}
