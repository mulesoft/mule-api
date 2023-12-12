/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.config.custom;

import org.mule.api.annotation.NoImplement;
import org.mule.runtime.api.lifecycle.Lifecycle;

import java.util.Optional;
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
   * @deprecated since 4.6, use {@link #interceptDefaultServiceImpl(String, Consumer)}.
   */
  <T> void overrideDefaultServiceImpl(String serviceId, T serviceImpl);

  /**
   * Allows to intercept a service provided by default on a mule context.
   * <p>
   * The new service implementation can be annotated with {@code @Inject} and implement methods from {@link Lifecycle}.
   * <p>
   * The service identifier can be used to locate the service in the mule registry.
   *
   * @param serviceId          identifier of the service implementation to override.
   * @param serviceInterceptor a {@link Consumer} that will use a {@link ServiceInterceptor} to intercept the default service
   *                           implementation.
   * @since 4.6
   */
  void interceptDefaultServiceImpl(String serviceId, Consumer<ServiceInterceptor> serviceInterceptor);

  /**
   * Handles the interception of a service implementation, allowing to customize it, using a different one or flagging it to be
   * removed.
   *
   * @since 4.6
   */
  interface ServiceInterceptor {

    /**
     * @return the default service implementation that is going to be overridden.
     */
    Optional<Object> getDefaultServiceImpl();

    /**
     * Sets the new service implementation that overrides the default one.
     * 
     * @param newServiceImpl the new service implementation.
     */
    void newServiceImpl(Object newServiceImpl);

    /**
     * Flags that the implementation of the current service must not be added to the registry.
     */
    void skip();

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
