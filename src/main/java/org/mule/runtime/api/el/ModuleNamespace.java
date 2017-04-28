/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.el;

import java.util.Arrays;

import static java.util.Arrays.stream;

/**
 * Represents the Namespace for a given module
 *
 * @since 1.0
 */
public class ModuleNamespace {

  private String[] elements;

  public ModuleNamespace(String... elements) {
    this.elements = elements;
  }

  /**
   * Returns the array of elements that forms the namespace name
   *
   * @return The array of elements
   */
  public String[] getElements() {
    return elements;
  }

  @Override
  public String toString() {
    return stream(elements)
        .reduce((identity, accumulator) -> identity + "::" + accumulator)
        .orElse("");
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    ModuleNamespace that = (ModuleNamespace) o;

    return Arrays.equals(elements, that.elements);
  }

  @Override
  public int hashCode() {
    return Arrays.hashCode(elements);
  }
}
