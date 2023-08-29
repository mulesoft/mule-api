/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.component;

import static org.mule.runtime.api.component.AbstractComponent.ANNOTATION_NAME;
import static org.mule.runtime.api.component.Component.Annotations.REPRESENTATION_ANNOTATION_KEY;
import static org.mule.runtime.api.component.Component.Annotations.SOURCE_ELEMENT_ANNOTATION_KEY;

import org.mule.runtime.api.component.location.ComponentLocation;
import org.mule.runtime.api.component.location.Location;

import java.util.Map;

import javax.xml.namespace.QName;

/**
 * This interface is the most abstracted representation of a configuration element in a mule artifact. It contains the minimal
 * required contract for components.
 * <p/>
 * Annotations are handled by the runtime and must not be tampered by custom implementations. The runtime will put values and
 * remove values from the annotations and that behaviour should not be changed by any custom implementation.
 *
 * @since 1.0
 */
public interface Component {

  String NS_MULE_DOCUMENTATION = "http://www.mulesoft.org/schema/mule/documentation";

  String NS_MULE_PARSER_METADATA = "http://www.mulesoft.org/schema/mule/parser-metadata";

  /**
   * Constants related to annotations placed over components in the configuration files.
   */
  interface Annotations {

    /**
     * Annotation placed over source code components so the user can provide a meaningful name.
     */
    QName NAME_ANNOTATION_KEY = new QName(NS_MULE_DOCUMENTATION, "name");

    /**
     * Annotation that defines the source code for the element.
     */
    QName SOURCE_ELEMENT_ANNOTATION_KEY = new QName(NS_MULE_PARSER_METADATA, "sourceElement");

    /**
     * Annotation that defines the representation for the element to be used in error reporting.
     */
    QName REPRESENTATION_ANNOTATION_KEY = new QName(NS_MULE_PARSER_METADATA, "representation");
  }

  /**
   * Property name required by implementations of this class for holding annotations.
   */
  String ANNOTATIONS_PROPERTY_NAME = "annotations";

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
   * The actual root container component location. Some components may belong to a template component in the configuration such as
   * a subflow or a module operation. Since those are template that end up being part of another root container component, like a
   * flow, then the root container may differ from the one on {@link ComponentLocation#getRootContainerLocation()}.
   *
   * @return the root container component location.
   */
  Location getRootContainerLocation();

  /**
   * @return the type identifier for this component.
   */
  default ComponentIdentifier getIdentifier() {
    return (ComponentIdentifier) getAnnotation(ANNOTATION_NAME);
  }

  /**
   * @return a representation of a flow element to be logged in a standard way.
   */
  default String getRepresentation() {
    return (String) getAnnotation(REPRESENTATION_ANNOTATION_KEY);
  }

  /**
   * @return the source code of this element in the DSL.
   */
  default String getDslSource() {
    return (String) getAnnotation(SOURCE_ELEMENT_ANNOTATION_KEY);
  }
}
