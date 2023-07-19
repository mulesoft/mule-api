/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.component;

import static java.util.Collections.emptyMap;
import static java.util.Collections.unmodifiableMap;
import static org.mule.runtime.api.component.Component.Annotations.REPRESENTATION_ANNOTATION_KEY;
import static org.mule.runtime.api.component.Component.Annotations.SOURCE_ELEMENT_ANNOTATION_KEY;

import org.mule.runtime.api.component.location.ComponentLocation;
import org.mule.runtime.api.component.location.Location;

import java.util.HashMap;
import java.util.Map;

import javax.xml.namespace.QName;

/**
 * Base implementation for {@link Component}
 */
public abstract class AbstractComponent implements Component {

  public static final QName LOCATION_KEY = new QName(NS_MULE_PARSER_METADATA, "COMPONENT_LOCATION");
  public static final QName ROOT_CONTAINER_NAME_KEY = new QName(NS_MULE_PARSER_METADATA, "ROOT_CONTAINER_NAME");
  public static final QName ANNOTATION_NAME = new QName("config", "componentIdentifier");

  private volatile Map<QName, Object> annotations = emptyMap();

  private ComponentLocation location;
  private Location rootContainerLocation;
  private ComponentIdentifier identifier;
  private String representation;
  private String dslSource;

  @Override
  public Object getAnnotation(QName qName) {
    return annotations.get(qName);
  }

  @Override
  public Map<QName, Object> getAnnotations() {
    return unmodifiableMap(annotations);
  }

  @Override
  public void setAnnotations(Map<QName, Object> newAnnotations) {
    annotations = new HashMap<>(newAnnotations);
    location = (ComponentLocation) getAnnotation(LOCATION_KEY);
    rootContainerLocation = initRootContainerName();
    identifier = (ComponentIdentifier) getAnnotation(ANNOTATION_NAME);
    representation = (String) getAnnotation(REPRESENTATION_ANNOTATION_KEY);
    dslSource = (String) getAnnotation(SOURCE_ELEMENT_ANNOTATION_KEY);
  }

  protected Location initRootContainerName() {
    String rootContainerName = (String) getAnnotation(ROOT_CONTAINER_NAME_KEY);
    if (rootContainerName == null && getLocation() != null) {
      rootContainerName = getLocation().getRootContainerName();
    }
    return rootContainerName == null ? null : Location.builder().globalName(rootContainerName).build();
  }

  @Override
  public ComponentLocation getLocation() {
    return location;
  }

  @Override
  public Location getRootContainerLocation() {
    return rootContainerLocation;
  }

  @Override
  public ComponentIdentifier getIdentifier() {
    return identifier;
  }

  @Override
  public String getRepresentation() {
    return representation;
  }

  @Override
  public String getDslSource() {
    return dslSource;
  }
}
