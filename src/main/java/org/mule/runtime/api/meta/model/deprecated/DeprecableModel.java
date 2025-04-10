/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model.deprecated;

import java.util.Optional;

/**
 * Interface that indicates that this part of an extension can be deprecated
 *
 * @since 1.2
 */
public interface DeprecableModel {

  /**
   * @return an {@link Optional} of a {@link DeprecationModel} that fully describes the deprecation of a {@link DeprecableModel},
   *         if the model is not deprecated, it will return {@link Optional#empty()}.
   */
  Optional<DeprecationModel> getDeprecationModel();

  /**
   * @return whether a part of the extension is deprecated or not.
   */
  default boolean isDeprecated() {
    return getDeprecationModel().isPresent();
  }


}
