/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.ioc;

import org.mule.runtime.api.component.Component;

import java.util.Map;
import java.util.Optional;

/**
 * Interface meant to be implemented by components that will provide objects that may be referenced from mule configuration files.
 *
 * @since 1.0
 */
public interface ObjectProvider extends Component {

  /**
   * Finds an object by name
   * 
   * @param name the object name
   * @return the object if there's one, empty otherwise.
   */
  Optional<Object> getObject(String name);

  /**
   * Finds an object by type
   * 
   * @param objectType the object type.
   * @return the object if there's one, empty otherwise. In case there are many, then it will fail unless there's a preferred one.
   *         How the preferred one is defined is up to the {@link ObjectProvider} implementation.
   */
  Optional<Object> getObjectByType(Class<?> objectType);

  /**
   * Finds all objects matching the given type
   *
   * @param type the object type class object
   * @param <T>  type parameter for the object
   * @return a map where the keys are the object names and the values the objects associated with the key
   */
  <T> Map<String, T> getObjectsByType(Class<T> type);

  /**
   * @param name object name
   * @return true if the object is a singleton, false if it's not, empty if there's no an object with that name.
   */
  Optional<Boolean> isObjectSingleton(String name);

  /**
   * @param name object name
   * @return true if the provider contains a bean with such name, false otherwise.
   */
  boolean containsObject(String name);
}
