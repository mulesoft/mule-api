/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.meta.model.declaration.fluent;

import org.mule.api.annotation.NoImplement;
import org.mule.runtime.api.meta.MuleVersion;

/**
 * Interface for a declarer which declares minimum mule version for its usage.
 *
 * @param <T> the type of the implementing type. Used to allow method chaining
 * @since 1.5
 */
@NoImplement
public interface HasMinMuleVersionDeclarer<T> {

  /**
   * Use this method to specify a {@link MuleVersion} which is the minimum required for the usage of the declarer
   *
   * @param minMuleVersion the minimum mule version for the declarer
   * @return {@code this} declarer
   */
  T withMinMuleVersion(MuleVersion minMuleVersion);

}
