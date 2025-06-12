/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.deployment.meta;

import org.mule.runtime.api.meta.model.ExtensionModel;

import java.util.Collections;
import java.util.Map;

/**
 * Generic descriptor that will be used to describe parametrization to construct {@link ExtensionModel}, {@link ClassLoader} and
 * any other descriptor that may arise in a future of {@link MulePluginModel}.
 * <p/>
 * Each {@link MuleArtifactLoaderDescriptor} will have an ID that will be used to discover any loader that's responsible of
 * working with the current set of attributes. It's up to each loader to validate the types, size and all that matters around the
 * attributes.
 *
 * @since 1.0
 */
public final class MuleArtifactLoaderDescriptor {

  private String id;
  private Map<String, Object> attributes;

  private MuleArtifactLoaderDescriptor() {
    // Nothing to do, this is just for allowing instantiation to GSON
  }

  /**
   * Creates an immutable implementation of {@link MuleArtifactLoaderDescriptor}
   *
   * @param id         ID of the descriptor
   * @param attributes collection of attributes
   */
  public MuleArtifactLoaderDescriptor(String id, Map<String, Object> attributes) {
    this.id = id;
    this.attributes = Collections.unmodifiableMap(attributes);
  }

  /**
   * @return descriptor's ID that will be used to discover any object that matches with this ID.
   */
  public String getId() {
    return id;
  }

  /**
   * @return attributes that will be feed to the loader found through {@link #getId()}, where it's up to the loader's
   *         responsibilities to determine if the current structure of the values ({@link Object}) does match with the expected
   *         types.
   *         <p>
   *         That implies each loader must evaluate on the attributes' values to be 100% sure it were formed correctly.
   */
  public Map<String, Object> getAttributes() {
    return attributes;
  }
}
