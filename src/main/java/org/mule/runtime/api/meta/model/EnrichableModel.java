/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model;

import org.mule.api.annotation.NoImplement;

import java.util.Optional;
import java.util.Set;

/**
 * A model which can be augmented with custom pieces of information that are not part of the canonical introspection model. We
 * call these pieces {@link ModelProperty model properties}
 * <p>
 * This is useful for pieces of metadata which might not apply to all extensions or might be specific to particular
 * implementations.
 * <p>
 * Examples of a model property are the namespace URI and schema version for extensions that support XML configuration, vendor
 * specific information, custom policies, etc.
 * <p>
 * The values added as model properties must be of implementations of the {@link ModelProperty} interface. As explained on the
 * {@link ModelProperty} javadoc, those objects should be immutable. This is because if a model definition keeps changing the
 * runtime behaviour could become inconsistent. They should also be thread-safe since several threads should be able to query the
 * model safely.
 *
 * @since 1.0
 */
@NoImplement
public interface EnrichableModel {

  /**
   * Returns a registered model property of type {@code propertyType}.
   * <p>
   * This search considers hierarchies. If no explicit value exists for the given {@code propertyType}, then it will look for
   * properties which extend the given type or, in the case of an interface, implement it.
   *
   * @param propertyType the {@link Class} of the {@link ModelProperty} which is being queried
   * @param <T>          the generic type of the return value
   * @return an {@link Optional} {@link ModelProperty}
   * @throws IllegalArgumentException if {@code propertyType} is {@code null}
   */
  <T extends ModelProperty> Optional<T> getModelProperty(Class<T> propertyType);

  /**
   * Returns all the model properties registered for this model
   *
   * @return an immutable {@link Set} containing all the model properties
   */
  Set<ModelProperty> getModelProperties();
}
