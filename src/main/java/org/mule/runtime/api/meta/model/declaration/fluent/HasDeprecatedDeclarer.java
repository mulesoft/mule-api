/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.meta.model.declaration.fluent;

import org.mule.api.annotation.NoImplement;
import org.mule.runtime.api.meta.model.deprecated.DeprecationModel;

/**
 * Contract interface for a declarer in which it's possible to add {@link DeprecationModel}
 *
 * @since 1.2
 */
@NoImplement
public interface HasDeprecatedDeclarer<T> {

  /**
   * Adds the given {@code stereotype}
   *
   * @param deprecation a {@link DeprecationModel}
   * @return {@code this} declarer
   */
  T withDeprecation(DeprecationModel deprecation);

}
