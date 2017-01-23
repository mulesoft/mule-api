/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.app.declaration;

/**
 * Adds naming methods to an element declaration
 *
 * @since 1.0
 */
public interface NamedElementDeclaration {

  /**
   * @return the name of the element
   */
  String getName();

  /**
   * Set's the name of the element
   *
   * @param name the name of the element
   */
  void setName(String name);

}
