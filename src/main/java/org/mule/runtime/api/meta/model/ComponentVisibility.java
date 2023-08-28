/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model;

/**
 * Defines the visibility of a given component.
 *
 * @since 1.5
 */
public enum ComponentVisibility {

  /**
   * The {@link ComponentModel} will be visible for third parties
   */
  PUBLIC,
  /**
   * The {@link ComponentModel} will not be visible for third parties
   */
  PRIVATE

}
