/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.runtime.api.meta.model.parameter;


/**
 * Represents a reference to an Extension Configuration for {@link ParameterizedModel}s.
 *
 * @since 1.0
 */
public class ElementReference {

  private String namespace;
  private String name;
  private ElementType type;

  public ElementReference(String namespace, String elementName, ElementType type) {
    this.namespace = namespace;
    this.name = elementName;
    this.type = type;
  }

  /**
   * @return the name of the element name that the {@link ParameterizedModel} accepts.
   */
  public String getElementName() {
    return name;
  }

  /**
   * @return the name of the namespace that contains the {@code elementName}
   */
  public String getNamespace() {
    return namespace;
  }

  /**
   * @return the reference {@link ElementType}.
   */
  public ElementType getType() {
    return type;
  }

  /**
   * Represents an element in the mule ecosystem.
   */
  public enum ElementType {

    /**
     * Represents an extension configuration.
     */
    CONFIG,

    /**
     * Represents a mule flow.
     */
    FLOW
  }
}
