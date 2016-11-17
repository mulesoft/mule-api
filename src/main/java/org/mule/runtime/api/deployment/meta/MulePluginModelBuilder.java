/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.deployment.meta;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.mule.runtime.api.util.Preconditions.checkArgument;
import org.mule.runtime.api.meta.model.ExtensionModel;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * A builder to create instances of {@link MulePluginModel}.
 *
 * @since 1.0
 */
public class MulePluginModelBuilder {

  private String name;
  private String minMuleVersion;
  private Optional<MulePluginPropertyBuilder> extensionModelDescriptorBuilder = empty();

  /**
   * Sets the describer's name
   *
   * @param name the name the describer will have
   * @return {@code this} builder
   */
  public MulePluginModelBuilder setName(String name) {
    this.name = name;
    return this;
  }

  /**
   * Sets the describer's minimum Mule Runtime version that requires to work correctly
   *
   * @param muleVersion of the describer
   * @return {@code this} builder
   */
  public MulePluginModelBuilder setMinMuleVersion(String muleVersion) {
    this.minMuleVersion = muleVersion;
    return this;
  }

  /**
   * @return a {@link MulePluginPropertyBuilder} to populate the {@link ExtensionModel} describer with the ID and
   * any additional attributes
   */
  public MulePluginPropertyBuilder withExtensionModelDescriber() {
    if (!extensionModelDescriptorBuilder.isPresent()) {
      extensionModelDescriptorBuilder = of(new MulePluginPropertyBuilder());
    }
    return extensionModelDescriptorBuilder.get();
  }

  /**
   * @return a well formed {@link MulePluginModel}
   */
  public MulePluginModel build() {
    checkArgument(!isBlank(name), "name cannot be a blank");
    checkArgument(minMuleVersion != null, "minMuleVersion cannot be null");
    return new MulePluginModel(name, minMuleVersion, extensionModelDescriptorBuilder.isPresent()
        ? extensionModelDescriptorBuilder.get().build() : null);
  }

  /**
   * A builder to create instances of {@link MulePluginProperty}.
   * <p>
   * A new instance of this class should be used per each manifest to be
   * created. The created instances will be immutable.
   *
   * @since 4.0
   */
  public static final class MulePluginPropertyBuilder {

    private String id;
    private final Map<String, Object> properties = new HashMap<>();

    /**
     * Sets the describer's ID
     *
     * @param id the ID to be set
     * @return {@code this} builder
     */
    public MulePluginPropertyBuilder setId(String id) {
      this.id = id;
      return this;
    }

    /**
     * Sets the given property on the describer.
     * <p>
     * If a value is already associated with the {@code key}, then
     * it is overridden with the new {@code value}
     *
     * @param key   the property's key
     * @param value the property's value
     * @return {@code this} builder
     */
    public MulePluginPropertyBuilder addProperty(String key, Object value) {
      checkArgument(!isBlank(key), "key cannot be a blank key");
      properties.put(key, value);
      return this;
    }

    /**
     * Creates and returns a new {@link MulePluginProperty} according to the values set
     *
     * @return a {@link MulePluginProperty}
     */
    private MulePluginProperty build() {
      checkArgument(!isBlank(id), "ID cannot be a blank");
      return new MulePluginProperty(id, properties);
    }
  }
}
