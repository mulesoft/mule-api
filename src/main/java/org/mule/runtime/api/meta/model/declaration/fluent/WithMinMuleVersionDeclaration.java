/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model.declaration.fluent;

import org.mule.api.annotation.NoImplement;
import org.mule.runtime.api.meta.MuleVersion;
import org.mule.runtime.api.meta.version.MuleMinorVersion;

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
   * @deprecated since 1.10 use {@link #withMinMuleVersion(MuleMinorVersion)} instead.
   */
  @Deprecated
  void withMinMuleVersion(MuleVersion minMuleVersion);

  /**
   * Use this method to specify a {@link MuleMinorVersion} which is the minimum required for the usage of the declaration
   *
   * @param minMuleVersion the minimum mule version for the declaration
   */
  void withMinMuleVersion(MuleMinorVersion minMuleVersion);

}
