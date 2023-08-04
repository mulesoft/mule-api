/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.lifecycle;

/**
 * {@code Initialisable} is a lifecycle interface that gets called at the initialise lifecycle stage of the implementing service.
 *
 * @since 1.0
 */
public interface Initialisable {

  String PHASE_NAME = "initialise";

  /**
   * Method used to perform any initialisation work. If a fatal error occurs during initialisation an
   * {@code InitialisationException} should be thrown.
   * <p/>
   * In case that the {@link #initialise()} method execution fails then mule will call the {@link Disposable#dispose()} method if
   * the class also implements {@link Disposable} allowing the object to dispose any allocated resource during
   * {@link #initialise()}
   * 
   * @throws InitialisationException if a fatal error occurs causing the Mule instance to shutdown
   * 
   * @see Disposable
   */
  void initialise() throws InitialisationException;
}
