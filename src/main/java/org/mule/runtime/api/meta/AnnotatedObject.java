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
 * <p/>
 * Annotations are handled by the runtime and must not be tampered by custom implementations. The runtime will put values and
 * remove values from the annotations and that behaviour should not be changed by any custom implementation.
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
   * @return the location properties of this component in the mule app configuration, or {@code null} if this object was generated
   *         programmatically and not from a configuration element.
   */
  ComponentLocation getLocation();

  /**
   * The actual root container component name. Some components may belong to a template component in the configuration such as a
   * subflow or a module operation. Since those are template that end up being part of another root container component, like a
   * flow, then the root container name may differ from the one on {@link ComponentLocation#getRootContainerName()}.
   * 
   * @return the root container component name.
   */
  String getRootContainerName();
}
