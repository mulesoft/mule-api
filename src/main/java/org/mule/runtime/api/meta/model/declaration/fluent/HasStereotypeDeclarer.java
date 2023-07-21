/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
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
