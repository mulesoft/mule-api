/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.ioc;

import org.mule.runtime.api.lifecycle.Initialisable;

/**
 * Interface meant to be implemented by components that will provide objects that may be referenced from mule configuration files.
 * <p/>
 * This interface may make use of {@link org.mule.runtime.api.lifecycle.Lifecycle} interfaces. If {@link Initialisable} is
 * implemented then it will be called after all the components in the artifact where initialised. Same with
 * {@link org.mule.runtime.api.lifecycle.Startable}.
 * 
 * @since 1.0
 */
public interface ConfigurableObjectProvider extends ObjectProvider {

  /**
   * Method to be called to prepare the {@link ConfigurableObjectProvider}. It is expected that the provider is ready to be used
   * after calling this method.
   * <p/>
   * This method will be invoked before {@link Initialisable#initialise()} if the implementation implements that interface.
   *
   * @param objectProviderConfiguration configuration for the provider.
   */
  void configure(ObjectProviderConfiguration objectProviderConfiguration);

}
