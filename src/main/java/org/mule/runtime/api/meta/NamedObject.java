/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
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
