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

import java.util.Optional;

/**
 * This object matches the mule-artifact.json element within a plugin. The describer holds information that has being
 * picked up from the JSON file (and the pom.xml when implemented). There's no extra logic, such as calculating the
 * URLs needed to feed a class loader or the {@link ExtensionModel} for the current plugin.
 *
 * @since 1.0
 */
public class MulePluginModel extends AbstractMuleArtifactModel {

  private final MuleArtifactLoaderDescriptor extensionModelLoaderDescriptor;

  private MulePluginModel(String name, String minMuleVersion,
                          MuleArtifactLoaderDescriptor classLoaderModelLoaderDescriptor,
                          MuleArtifactLoaderDescriptor extensionModelLoaderDescriptor,
                          MuleArtifactLoaderDescriptor bundleDescriptor) {
    super(name, minMuleVersion, classLoaderModelLoaderDescriptor, bundleDescriptor);
    this.extensionModelLoaderDescriptor = extensionModelLoaderDescriptor;
  }

  public Optional<MuleArtifactLoaderDescriptor> getExtensionModelLoaderDescriptor() {
    return ofNullable(extensionModelLoaderDescriptor);
  }

  /**
   * A builder to create instances of {@link MulePluginModel}.
   *
   * @since 1.0
   */
  public static class MulePluginModelBuilder extends AbstractMuleArtifactModelBuilder<MulePluginModelBuilder, MulePluginModel> {

    private Optional<MuleArtifactLoaderDescriptorBuilder> extensionModelDescriptorBuilder = empty();

    @Override
    protected MulePluginModelBuilder getThis() {
      return this;
    }

    /**
     * @return a {@link MuleArtifactLoaderDescriptorBuilder} to populate the {@link ExtensionModel} describer with the ID and
     * any additional attributes
     */
    public MuleArtifactLoaderDescriptorBuilder withExtensionModelDescriber() {
      if (!extensionModelDescriptorBuilder.isPresent()) {
        extensionModelDescriptorBuilder = of(new MuleArtifactLoaderDescriptorBuilder());
      }
      return extensionModelDescriptorBuilder.get();
    }

    /**
     * @return a well formed {@link MulePluginModel}
     */
    public MulePluginModel build() {
      checkArgument(!isBlank(getName()), "name cannot be a blank");
      checkArgument(getMinMuleVersion() != null, "minMuleVersion cannot be null");
      checkArgument(getBundleDescriptorLoader() != null, "bundleDescriber cannot be null");

      return new MulePluginModel(getName(), getMinMuleVersion(),
                                 getClassLoaderModelDescriptorLoader(),
                                 extensionModelDescriptorBuilder.isPresent() ? extensionModelDescriptorBuilder.get().build()
                                     : null,
                                 getBundleDescriptorLoader());
    }
  }
}
