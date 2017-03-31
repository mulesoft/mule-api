/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.app.declaration;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * A declaration which can be augmented with custom pieces of information
 * that are not part of the actual element model.
 * <p>
 * This is useful for pieces of metadata that might be specific to particular
 * representations of the declared artifact, like custom XML properties or
 * conventions that are not related to the artifact model but necessary for
 * not loosing information during its serialization/deserialization.
 *
 * @since 1.0
 */
public interface EnrichableDeclaration {

  /**
   * @return the {@link List} of {@link ParameterElementDeclaration parameters} associated with
   * {@code this}
   */
  List<ParameterElementDeclaration> getCustomParameters();

  /**
   * Adds a {@link ParameterElementDeclaration custom parameter} to {@code this} enrichable element declaration.
   * This {@code customParameter} represents an additional parameter to the ones exposed by the actual model
   * associated to this {@link ElementDeclaration element}.
   * No validation of any kind will be performed over this {@code customParameter} and its value.
   *
   * @param customParameter the {@link ParameterElementDeclaration} to associate to {@code this} element declaration
   */
  void addCustomParameter(ParameterElementDeclaration customParameter);

  /**
   * @param name the name of the property
   * @return the property for the given name, or {@link Optional#empty()} if none was found.
   */
  Optional<Object> getProperty(String name);

  /**
   * @return the metadata properties associated to this {@link EnrichableElementDeclaration}
   */
  Map<String, Object> getProperties();


  /**
   * Adds a property to the {@link ElementDeclaration}.
   * This property is meant to hold only metadata of the declaration.
   *
   * @param name custom attribute name.
   * @param value custom attribute value.
   */
  void addProperty(String name, Object value);

}
