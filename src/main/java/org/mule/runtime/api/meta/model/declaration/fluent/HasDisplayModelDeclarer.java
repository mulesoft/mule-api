/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.meta.model.declaration.fluent;


import org.mule.api.annotation.NoImplement;
import org.mule.runtime.api.meta.model.display.DisplayModel;

/**
 * Contract interface for a declarer in which it's possible to add a {@link DisplayModel}
 *
 * @since 1.0
 */
@NoImplement
public interface HasDisplayModelDeclarer<T> {

  /**
   * Sets the given {@code displayModel}
   *
   * @param displayModel a {@link DisplayModel}
   * @return {@code this} declarer
   */
  T withDisplayModel(DisplayModel displayModel);
}
