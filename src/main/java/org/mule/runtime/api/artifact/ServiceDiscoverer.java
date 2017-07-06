/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.artifact;

import java.util.Collection;
import java.util.Optional;

/**
 * Interface to access artifact services.
 * <p>
 * {@link javax.inject.Inject} must should be the preferred option to access artifact services like
 * {@link org.mule.runtime.api.store.ObjectStoreManager}, {@link org.mule.runtime.api.serialization.ObjectSerializer}, etc. but
 * since {@link javax.inject.Inject} fails if there's a dependency that may not always be present then this mechanism is required
 * instead.
 * 
 * @since 1.0
 */
public interface ServiceDiscoverer {

  /**
   * Looks up for a service within the artifact.
   * <p>
   * If there are multiple services registered then an exception will be raised. If that could be the case then
   * {@code {@link #lookupAll(Class)}} should be used instead.
   * 
   * @param serviceType the service type
   * @param <T> the type of the service
   * @return the service if a single implementation was found, empty if no implementation was found. If more than one
   *         implementation is found then it will fail.
   */
  <T> Optional<T> lookup(Class<T> serviceType);

  /**
   * Lookups for a service within the artifact by it's name.
   *
   * @param name the service identifier
   * @param <T> the type of the service
   * @return the service if a single implementation was found, empty if no implementation was found.
   */
  <T> Optional<T> lookupByName(String name);

  /**
   * Lookups for a set of service within the artifact.
   *
   * @param serviceType the service type
   * @param <T> the type of the service
   * @return the set of services found, empty collection if no implementation was found.
   */
  <T> Collection<T> lookupAll(Class<T> serviceType);

}
