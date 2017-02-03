/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta;

import org.mule.runtime.api.component.location.ComponentLocation;

import java.util.Map;

import javax.xml.namespace.QName;

/**
 * Maps annotations defined in an XML element to the corresponding Java object
 *
 * @since 1.0
 */
public interface AnnotatedObject {

  String PROPERTY_NAME = "annotations";

  /**
   * Gets the value of specified annotation.
   *
   * @return the value of specified annotation
   */
  Object getAnnotation(QName name);

  /**
   * Gets all annotations.
   *
   * @return all annotation
   */
  Map<QName, Object> getAnnotations();

  /**
   * Sets annotations to the object.
   */
  void setAnnotations(Map<QName, Object> annotations);

  /**
   * @return the location properties of this component in the mule app configuration.
   */
  ComponentLocation getLocation();
}
