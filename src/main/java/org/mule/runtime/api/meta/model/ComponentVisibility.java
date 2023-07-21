/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
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
