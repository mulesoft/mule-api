/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.meta;

/**
 * Adds {@link #getName} and {@link #setName} methods to an object
 *
 * @since 1.0
 */
public interface NameableObject extends NamedObject {

  /**
   * Sets the name of the object
   *
   * @param name the name of the object
   */
  void setName(String name);
}
