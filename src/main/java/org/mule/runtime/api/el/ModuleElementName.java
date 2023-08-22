/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.el;

import java.util.Objects;

/**
 * Represents the name of an Element in a given module
 *
 * @since 1.3
 */
public class ModuleElementName {

  private ModuleNamespace namespace;
  private String localName;

  public ModuleElementName(ModuleNamespace namespace, String localName) {
    this.namespace = namespace;
    this.localName = localName;
  }


  public ModuleNamespace getNamespace() {
    return namespace;
  }

  public String getLocalName() {
    return localName;
  }

  @Override
  public String toString() {
    return namespace + "::" + localName;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    ModuleElementName that = (ModuleElementName) o;
    return Objects.equals(namespace, that.namespace) &&
        Objects.equals(localName, that.localName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(namespace, localName);
  }
}
