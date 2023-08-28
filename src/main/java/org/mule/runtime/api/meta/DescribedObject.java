/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta;

import org.mule.api.annotation.NoImplement;

/**
 * A generic contract for a class that has a description.
 *
 * @since 1.0
 */
@NoImplement
public interface DescribedObject {

  /**
   * Returns the component's description
   *
   * @return a non blank {@link java.lang.String}
   */
  String getDescription();
}
