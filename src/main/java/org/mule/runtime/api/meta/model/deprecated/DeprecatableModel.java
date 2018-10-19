/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model.deprecated;

import java.util.Optional;

/**
 * ADD JAVA DOC
 * 
 * @since 1.2
 */
public interface DeprecatableModel {

  /**
   * ADD JAVA DOC
   * 
   * @return
   */
  Optional<DeprecatedModel> getDeprecatedModel();

  /**
   * ADD JAVA DOC
   * 
   * @return
   */
  default boolean isDeprecated() {
    return getDeprecatedModel().isPresent();
  }


}
