/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.deployment.meta;

import static java.util.Optional.empty;
import static java.util.Optional.ofNullable;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.mule.runtime.api.util.Preconditions.checkArgument;

import org.mule.api.annotation.NoExtend;

import java.util.Optional;

/**
 * This object matches the mule-artifact.json element within a server plugin. The describer holds information that has been picked
 * up from the JSON file (and the pom.xml when implemented).
 *
 * @since 1.0
 */
@NoExtend
public class MuleServerPluginModel extends AbstractMuleArtifactModel {

  private static final String PLUGIN_CLASS_NAME = "pluginClassName";

  private final MuleArtifactLoaderDescriptor extensionModelLoaderDescriptor;
  private final String pluginClassName;

  private MuleServerPluginModel(String name, String minMuleVersion, Product product,
                                MuleArtifactLoaderDescriptor classLoaderModelLoaderDescriptor,
                                MuleArtifactLoaderDescriptor extensionModelLoaderDescriptor,
                                MuleArtifactLoaderDescriptor bundleDescriptor, String pluginClassName) {
    super(name, minMuleVersion, product, classLoaderModelLoaderDescriptor, bundleDescriptor);
    this.extensionModelLoaderDescriptor = extensionModelLoaderDescriptor;
    this.pluginClassName = pluginClassName;
  }

  public Optional<MuleArtifactLoaderDescriptor> getExtensionModelLoaderDescriptor() {
    return ofNullable(extensionModelLoaderDescriptor);
  }

  public String getPluginClassName() {
    return pluginClassName;
  }

  @Override
  protected void doValidateCustomFields(String descriptorName) {
    validateMandatoryFieldIsSet(descriptorName, pluginClassName, PLUGIN_CLASS_NAME);
  }

  /**
   * A builder to create instances of {@link MuleServerPluginModel}.
   *
   * @since 1.0
   */
  public static class MuleServerPluginModelBuilder
      extends AbstractMuleArtifactModelBuilder<MuleServerPluginModelBuilder, MuleServerPluginModel> {

    private Optional<MuleArtifactLoaderDescriptorBuilder> extensionModelDescriptorBuilder = empty();
    private String pluginClassName;

    @Override
    protected MuleServerPluginModelBuilder getThis() {
      return this;
    }

    /**
     * @param className name of the class implementing the server plugin
     * @return same builder instance
     */
    public MuleServerPluginModelBuilder withPluginClassName(String className) {
      this.pluginClassName = className;

      return this;
    }

    /**
     * @return a well formed {@link MuleServerPluginModel}
     */
    public MuleServerPluginModel build() {
      checkArgument(!isBlank(getName()), "name cannot be a blank");
      checkArgument(!isBlank(pluginClassName), "pluginClassName cannot be blank");
      checkArgument(getMinMuleVersion() != null, "minMuleVersion cannot be null");
      checkArgument(getBundleDescriptorLoader() != null, "bundleDescriber cannot be null");

      return new MuleServerPluginModel(getName(), getMinMuleVersion(), getRequiredProduct(),
                                       getClassLoaderModelDescriptorLoader(),
                                       extensionModelDescriptorBuilder.isPresent() ? extensionModelDescriptorBuilder.get().build()
                                           : null,
                                       getBundleDescriptorLoader(), pluginClassName);
    }
  }
}
