/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.config.custom;

import org.mule.api.annotation.NoImplement;
import org.mule.runtime.api.lifecycle.Lifecycle;

import java.util.function.Consumer;

/**
 * Interface that allows to customize the set of services provided by the {@code MuleContext}.
 * <p>
 * It's possible to add new services or replace default implementation for services specifying a service implementation or a
 * services class.
 * <p>
 * For replacing an existent service, the service identifier must be used. Make sure to use the same constants as the runtime.
 *
 * @since 1.0
 */
@NoImplement
public interface CustomizationService {

  /**
   * Allows to override a service provided by default on a mule context. The provided implementation will be used instead of the
   * default one if it's replacing an existent service.
   * <p>
   * The service implementation can be annotated with @Inject and implement methods from {@link Lifecycle}.
   * <p>
   * The service identifier can be used to locate the service in the mule registry.
   *
   * @param serviceId   identifier of the services implementation to customize.
   * @param serviceImpl the service implementation instance
   * @param <T>         the service type
   * @deprecated since 4.6, use {@link #overrideDefaultServiceImpl(String, Consumer)}.
   */
  <T> void overrideDefaultServiceImpl(String serviceId, T serviceImpl);

  /**
   * Allows to override a service provided by default on a mule context.
   * <p>
   * The new service implementation can be annotated with {@code @Inject} and implement methods from {@link Lifecycle}.
   * <p>
   * The service identifier can be used to locate the service in the mule registry.
   *
   * @param serviceId        identifier of the service implementation to override.
   * @param serviceOverrider a {@link Consumer} that will use a {@link ServiceOverrider} to override the default service
   *                         implementation.
   * @since 4.6
   */
  void overrideDefaultServiceImpl(String serviceId, Consumer<ServiceOverrider> serviceOverrider);

  /**
   * Handles the override of a service implementation, which means either using a different one or flagging it to be removed.
   *
   * @since 4.6
   */
  interface ServiceOverrider {

    /**
     * @return the default service implementation that is going to be overridden.
     */
    Object getOverridee();

    /**
     * Sets the new service implementation that overrides the default one.
     * 
     * @param overrider the new service implementation.
     */
    void override(Object overrider);

    /**
     * Flags that no implementation of the current service must be added to the registry.
     */
    void remove();

  }

  /**
   * Allows to override a service provided by default on a mule context. The provided class will be used to instantiate the
   * service that replaces the default one if it's replacing an existent service.
   * <p>
   * The service class can be annotated with {@code javax.inject.Inject} and implement methods from {@link Lifecycle}.
   *
   * @param serviceId    identifier of the services implementation to customize.
   * @param serviceClass the service class
   * @param <T>          the service type
   */
  <T> void overrideDefaultServiceClass(String serviceId, Class<T> serviceClass);

  /**
   * Allows to define a custom service on a mule context.
   * <p>
   * The service implementation can be annotated with @Inject and implement methods from {@link Lifecycle}.
   * <p>
   * The service identifier can be used to locate the service in the mule registry.
   *
   * @param serviceId   identifier of the services implementation to register. Non empty.
   * @param serviceImpl the service implementation instance. Non null.
   * @param <T>         the service type
   */
  <T> void registerCustomServiceImpl(String serviceId, T serviceImpl);


  /**
   * Allows to define a custom service on a mule context. The provided class will be used to instantiate the service that replaces
   * the default one if it's replacing an existent service.
   * <p>
   * The service class can be annotated with {@code javax.inject.Inject} and implement methods from {@link Lifecycle}.
   *
   * @param serviceId    identifier of the services implementation to register. Non empty.
   * @param serviceClass the service class. Non null.
   * @param <T>          the service type
   */
  <T> void registerCustomServiceClass(String serviceId, Class<T> serviceClass);

}
