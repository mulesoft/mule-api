/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model.version;

import org.mule.api.annotation.NoImplement;
import org.mule.runtime.api.meta.MuleVersion;
import org.mule.runtime.api.meta.model.OutputModel;
import org.mule.runtime.api.meta.model.deprecated.DeprecationModel;

import java.util.Optional;

/**
 * Interface for a model which declares minimum mule version for its usage.
 *
 * @since 1.6
 */
@NoImplement
public interface HasMinMuleVersion {

  /**
   * @return an {@link Optional} {@link MuleVersion} which is the minimum required for the usage of the model, or
   *         {@link Optional#empty()} if none is required.
   */
  Optional<MuleVersion> getMinMuleVersion();

}
