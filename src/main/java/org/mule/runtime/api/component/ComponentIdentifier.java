/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.component;

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
   * The namespace is a short name of the extension that defines the component.
   * 
   * @return the unique identifier namespace
   */
  String getNamespace();

  /**
   * @return the unique identifier configuration name
   */
  String getName();
}
