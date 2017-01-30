/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.lifecycle;

/**
 * {@code Initialisable} is a lifecycle interface that gets called at the initialise lifecycle
 * stage of the implementing service.
 *
 * @since 1.0
 */
public interface Initialisable {

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
}
