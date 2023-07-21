/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.deployment.meta;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.mule.runtime.api.util.Preconditions.checkArgument;

import java.util.HashMap;
import java.util.Map;

/**
 * A builder to create instances of {@link MuleArtifactLoaderDescriptor}.
 * <p>
 * A new instance of this class should be used per each manifest to be created. The created instances will be immutable.
 *
 * @since 1.0
 */
public final class MuleArtifactLoaderDescriptorBuilder {

  private String id;
  private final Map<String, Object> properties = new HashMap<>();

  /**
   * Sets the describer's ID
   *
   * @param id the ID to be set
   * @return {@code this} builder
   */
  public MuleArtifactLoaderDescriptorBuilder setId(String id) {
    this.id = id;
    return this;
  }

  /**
   * Sets the given property on the describer.
   * <p>
   * If a value is already associated with the {@code key}, then it is overridden with the new {@code value}
   *
   * @param key   the property's key
   * @param value the property's value
   * @return {@code this} builder
   */
  public MuleArtifactLoaderDescriptorBuilder addProperty(String key, Object value) {
    checkArgument(!isBlank(key), "key cannot be a blank key");
    properties.put(key, value);
    return this;
  }

  /**
   * Creates and returns a new {@link MuleArtifactLoaderDescriptor} according to the values set
   *
   * @return a {@link MuleArtifactLoaderDescriptor}
   */
  public MuleArtifactLoaderDescriptor build() {
    checkArgument(!isBlank(id), "ID cannot be a blank");
    return new MuleArtifactLoaderDescriptor(id, properties);
  }
}
