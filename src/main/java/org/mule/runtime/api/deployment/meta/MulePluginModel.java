/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.deployment.meta;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static java.util.Optional.ofNullable;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.mule.runtime.api.util.Preconditions.checkArgument;
import org.mule.runtime.api.meta.model.ExtensionModel;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * This object matches the mule-plugin.json element within a plugin. The describer holds information that has being
 * picked up from the JSON file (and the pom.xml when implemented). There's no extra logic, such as calculating the
 * URLs needed to feed a class loader or the {@link ExtensionModel} for the current plugin.
 * <p/>
 * The idea behind this object is to just load some bits of information that later each "loader" will consume to
 * generate things like {@link ClassLoader}, {@link ExtensionModel}, etc.
 *
 * @since 1.0
 */
public class MulePluginModel {

  private final String name;
  private final String minMuleVersion;
  private final MulePluginLoaderDescriptor extensionModelLoaderDescriptor;
  private final MulePluginLoaderDescriptor classLoaderModelLoaderDescriptor;

  private MulePluginModel(String name, String minMuleVersion,
                          MulePluginLoaderDescriptor classLoaderModelLoaderDescriptor,
                          MulePluginLoaderDescriptor extensionModelLoaderDescriptor) {
    this.name = name;
    this.minMuleVersion = minMuleVersion;
    this.extensionModelLoaderDescriptor = extensionModelLoaderDescriptor;
    this.classLoaderModelLoaderDescriptor = classLoaderModelLoaderDescriptor;
  }

  public String getName() {
    return name;
  }

  public String getMinMuleVersion() {
    return minMuleVersion;
  }

  public Optional<MulePluginLoaderDescriptor> getClassLoaderModelLoaderDescriptor() {
    return ofNullable(classLoaderModelLoaderDescriptor);
  }

  public Optional<MulePluginLoaderDescriptor> getExtensionModelLoaderDescriptor() {
    return ofNullable(extensionModelLoaderDescriptor);
  }

  /**
   * A builder to create instances of {@link MulePluginModel}.
   *
   * @since 1.0
   */
  public static class MulePluginModelBuilder {

    private String name;
    private String minMuleVersion;
    private Optional<MulePluginPropertyBuilder> classLoaderDescriptorBuilder = empty();
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
     * @return a {@link MulePluginPropertyBuilder} to populate the {@link ClassLoader} describer with the ID and
     * any additional attributes
     */
    public MulePluginPropertyBuilder withClassLoaderModelDescriber() {
      if (!classLoaderDescriptorBuilder.isPresent()) {
        classLoaderDescriptorBuilder = of(new MulePluginPropertyBuilder());
      }
      return classLoaderDescriptorBuilder.get();
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
      return new MulePluginModel(name, minMuleVersion,
                                 classLoaderDescriptorBuilder.isPresent() ? classLoaderDescriptorBuilder.get().build() : null,
                                 extensionModelDescriptorBuilder.isPresent() ? extensionModelDescriptorBuilder.get().build()
                                     : null);
    }

    /**
     * A builder to create instances of {@link MulePluginLoaderDescriptor}.
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
       * Creates and returns a new {@link MulePluginLoaderDescriptor} according to the values set
       *
       * @return a {@link MulePluginLoaderDescriptor}
       */
      private MulePluginLoaderDescriptor build() {
        checkArgument(!isBlank(id), "ID cannot be a blank");
        return new MulePluginLoaderDescriptor(id, properties);
      }
    }
  }
}
