/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.lifecycle;

import org.mule.runtime.api.component.location.ComponentLocation;
import org.mule.runtime.api.meta.AnnotatedObject;

import java.util.Map;

import javax.xml.namespace.QName;

/**
 * {@code Initialisable} is a lifecycle interface that gets called at the initialise lifecycle
 * stage of the implementing service.
 *
 * @since 1.0
 */
public interface Initialisable extends AnnotatedObject {

  String PHASE_NAME = "initialise";

  /**
   * Method used to perform any initialisation work. If a fatal error occurs during initialisation an
   * {@code InitialisationException} should be thrown, causing the Mule instance to shutdown. If the error is recoverable,
   * say by retrying to connect, a {@code RecoverableException} should be thrown. There is no guarantee that by throwing a
   * Recoverable exception that the Mule instance will not shut down.
   *
   * @throws InitialisationException if a fatal error occurs causing the Mule instance to shutdown
   */
  void initialise() throws InitialisationException;

  //
  // Initialisable's are passed to InitialisationException, which being a LocatedMuleExceotion will query the annotations from the
  // object
  //

  @Override
  default Object getAnnotation(QName name) {
    return null;
  }

  @Override
  default Map<QName, Object> getAnnotations() {
    return null;
  }

  @Override
  default void setAnnotations(Map<QName, Object> annotations) {
    // Nothing to do
  }

  @Override
  default String getRootContainerName() {
    return null;
  }

  @Override
  default ComponentLocation getLocation() {
    return null;
  }

}
