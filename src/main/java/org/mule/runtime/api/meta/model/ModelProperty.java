/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model;

import org.mule.runtime.api.meta.NamedObject;

import java.io.Serializable;

/**
 * A custom property which augments an {@link EnrichableModel} with
 * non canonical pieces of information.
 * <p>
 * This is useful for pieces of metadata which might not apply to all extensions
 * or might be specific to particular implementations.
 * <p>
 * Examples of a model property are the namespace URI and schema version for extensions
 * that support XML configuration, vendor specific information, custom policies, etc.
 * <p>
 * Implementations of this interface must be immutable. This is because if a model definition
 * keeps changing the runtime behaviour could become inconsistent.
 * They should also be thread-safe since several threads should be able to query the model safely.
 *
 * @since 1.0
 */
public interface ModelProperty extends NamedObject, Serializable {

  /**
   * A unique name which identifies this property. No model
   * should contain two instances with the same name
   *
   * @return a unique name
   */
  @Override
  String getName();

  /**
   * Whether this instance should be included when serializing or sharing
   * the owning {@link EnrichableModel} or if on the contrary, this model property
   * holds information which is proprietary to the runtime and should not
   * communicated.
   * <p>
   * Non public properties should be skipped by serializers and any
   * other kind of sharing mechanisms.
   * <p>
   * <b>BEWARE:</b> Non public model properties are not to be considered API
   * and thus nobody (or anything) should depend on it. Backwards compatibility
   * will not be guaranteed on non public properties.
   *
   * @return whether this property should be communicated or not
   */
  boolean isPublic();
}
