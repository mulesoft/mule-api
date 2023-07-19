/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.meta;

/**
 * Adds {@link #getName} method to an object
 *
 * @since 1.0
 */
public interface NamedObject {

  /**
   * Gets the name of the object
   *
   * @return the name of the object
   */
  String getName();
}
