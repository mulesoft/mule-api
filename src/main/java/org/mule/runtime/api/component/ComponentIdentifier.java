/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.component;

import org.mule.runtime.api.message.Message;

/**
 * Unique identifier for a configuration option. Every configuration option has a namespace and an identifier.
 * <p>
 * The namespace is a short name of the extension that defines the component. Even core configuration have a namespace even though
 * they have namespace in the declaration in the configuration files.
 *
 * @since 1.0
 */
public interface ComponentIdentifier {

  /**
   * Declares different types of component based on its common characteristics.
   */
  public enum ComponentType {
    /**
     * Receives something from an external system, transforms it into a {@link Message} and vice-versa.  
     */
    SOURCE,

    /**
     * Executes an operation defined in an extension.
     */
    OPERATION,
    
    /**
     * Executes one or many nested component chains. 
     */
    ROUTER,
    
    /**
     * Wraps the next defined component, controlling its invocation.
     */
    INTERCEPTING,
    
    /**
     * Regular component that doesn't match any of the other criterias.
     */
    PROCESSOR;
  }

  /**
   * The namespace is a short name of the extension that defines the component.
   * 
   * @return the unique identifier namespace
   */
  String getNamespace();

  /**
   * @return the unique identifier configuration name
   */
  String getName();
  
  /**
   * @return the type that represents the kind of the identified component.
   */
  ComponentType getComponentType();
}
