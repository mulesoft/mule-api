/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.component.location;

import org.mule.api.annotation.NoImplement;
import org.mule.runtime.api.component.Component;
import org.mule.runtime.api.component.ComponentIdentifier;

import java.util.List;
import java.util.Optional;

/**
 * Locator to access runtime objects created from the configuration of the artifact.
 * <p>
 * The location can be composed by many parts, each part separated by an slash.
 * <p>
 * The first part must be the name of the global element that contains the location. Location "myflow" indicates the global
 * element with name myFlow.
 * <p>
 * Global elements that do not have a name cannot be referenced.
 * <p>
 * The following parts must be components contained within the global element. Location "myFlow/processors" indicates the
 * processors part of the global element with name "myFlow"
 * <p>
 * Each part must be contained in the preceded component in the location. Location "myFlow/errorHandler/onErrors" indicates the
 * onErrors components that are part of the errorHandler component which is also part of the "myFlow" global element.
 * <p>
 * When a component part has a collection of components, each component can be referenced individually with an index. THe first
 * index is 0. Location "myFlow/processors/4" refers to the fifth processors inside the flow with name "myFlow"
 * <p>
 * When working with configuration templates such subflows or global error handlers there's no way to access the message
 * processors that belong to them through this interface.
 *
 * @since 4.0
 */
@NoImplement
public interface ConfigurationComponentLocator {

  String REGISTRY_KEY = "_muleConfigurationComponentLocator";

  /**
   * Finds a component in the configuration with the specified location. Only simple objects locations are accepted.
   *
   * @param location the location of the component.
   * @return the found component or empty if there's no such component.
   */
  Optional<Component> find(Location location);

  /**
   * Finds all the components with the given identifier within the artifact.
   *
   * @param componentIdentifier the component identifier
   * @return the list of found components. If no component is found an empty list will be returned.
   */
  List<Component> find(ComponentIdentifier componentIdentifier);

  /**
   * Finds the locations of every component within the artifact.
   * <p>
   * This method may return locations for which there are no {@link Component} found with {@link #find(ComponentIdentifier)} or
   * {@link #find(Location)}. This happens in the case when the parser knows a certain location exists but the component in that
   * location is not available (i.e.: is lazy)
   *
   * @return the list of {@link ComponentLocation}s within the artifact.
   */
  List<ComponentLocation> findAllLocations();

}
