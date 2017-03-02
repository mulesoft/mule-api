/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.runtime.api.meta;

import static java.util.Collections.unmodifiableMap;
import org.mule.runtime.api.component.location.ComponentLocation;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.xml.namespace.QName;

/**
 * Base implementation for {@link AnnotatedObject}
 */
public abstract class AbstractAnnotatedObject implements AnnotatedObject {

  public static QName LOCATION_KEY = new QName("mule", "COMPONENT_LOCATION");

  private final Map<QName, Object> annotations = new ConcurrentHashMap<>();

  @Override
  public Object getAnnotation(QName qName) {
    return annotations.get(qName);
  }

  @Override
  public Map<QName, Object> getAnnotations() {
    return unmodifiableMap(annotations);
  }

  @Override
  public synchronized void setAnnotations(Map<QName, Object> newAnnotations) {
    annotations.clear();
    annotations.putAll(newAnnotations);
  }

  @Override
  public ComponentLocation getLocation() {
    return (ComponentLocation) getAnnotation(LOCATION_KEY);
  }
}
