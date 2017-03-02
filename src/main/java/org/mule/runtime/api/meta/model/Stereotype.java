/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model;

import static org.mule.runtime.api.util.Preconditions.checkArgument;
import org.mule.runtime.api.meta.NamedObject;

import java.util.Objects;

/**
 * A widely held but fixed and oversimplified image or idea of the owning model
 *
 * @since 1.0
 */
public final class Stereotype implements NamedObject {

  private final String name;

  /**
   * Creates a new instance
   *
   * @param name the stereotype's name
   */
  public Stereotype(String name) {
    checkArgument(name != null && name.trim().length() > 0, "name cannot be blank");
    this.name = name;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getName() {
    return name;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Stereotype) {
      return Objects.equals(name, ((Stereotype) obj).getName());
    }

    return false;
  }

  @Override
  public int hashCode() {
    return name.hashCode();
  }
}
