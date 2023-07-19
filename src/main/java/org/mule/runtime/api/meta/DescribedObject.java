/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
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
