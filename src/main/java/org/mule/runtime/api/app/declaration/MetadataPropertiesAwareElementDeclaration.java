/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.app.declaration;

import java.io.Serializable;
import java.util.Map;
import java.util.Optional;

/**
 * A declaration which can be augmented with metadata
 * regarding the represented element model.
 * <p>
 * This is useful for extending the information over a particular data element
 * of the declared artifact. Examples of this can go from the fact that the data has
 * to be escaped as a CDATA element, all the way to actually storing the origin element.
 * <p>
 *
 * @since 1.0
 */
public interface MetadataPropertiesAwareElementDeclaration {

  /**
   * Retrieves a {@code metadataProperty} to the {@link ElementDeclaration}.
   * This property is meant to hold only metadata of the declaration,
   * related to how the declaration has to be represented but not affecting nor containing
   * information related to the model configured with this declaration.
   *
   * @param name the name of the property
   * @return the property for the given name, or {@link Optional#empty()} if none was found.
   */
  Optional<Serializable> getMetadataProperty(String name);

  /**
   * @return the metadata properties associated to this {@link EnrichableElementDeclaration}
   */
  Map<String, Serializable> getMetadataProperties();

  /**
   * Adds a {@code metadataProperty} to the {@link ElementDeclaration}.
   * This property is meant to hold only metadata of the declaration,
   * related to how the declaration has to be represented but not affecting nor containing
   * information related to the model configured with this declaration.
   *
   * This property may contain information regarding things like transformations required for
   * the persistence of a given element, or propagating particular metadata of how the
   * declaration was originally declared before deserialization.
   *
   * An example for this would be having an {@code xmlns} prefix declared different than
   * the one declared by the extension. So in order to represent:
   * {@code <my-http-alias:listener-config>} we will declare an {@code http:listener-config}
   * adding also a {@code metadataProperty} with the custom prefix {@code my-http-alias}.
   * This way, we can honour the original {@code XML} representation, but keep
   * the {@link ElementDeclaration} closer the the model it represents.
   *
   * @param name custom attribute name.
   * @param value custom attribute value.
   */
  void addMetadataProperty(String name, Serializable value);

}
