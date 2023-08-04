/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.meta.model.declaration.fluent;

import org.mule.api.annotation.NoImplement;
import org.mule.runtime.api.meta.MuleVersion;

import java.util.Optional;

/**
 * Interface for a declaration which declares minimum mule version for its usage.
 *
 * @since 1.5
 */
@NoImplement
public interface WithMinMuleVersionDeclaration {

  /**
   * @return an {@link Optional} of a {@link MuleVersion} which is the minimum required for the usage of the declaration, or
   *         {@link Optional#empty()} if none is required.
   */
  Optional<MuleVersion> getMinMuleVersion();

  /**
   * Use this method to specify a {@link MuleVersion} which is the minimum required for the usage of the declaration
   *
   * @param minMuleVersion the minimum mule version for the declaration
   */
  void withMinMuleVersion(MuleVersion minMuleVersion);

}
