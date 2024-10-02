/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.deployment.meta;

import static org.mule.runtime.api.util.Preconditions.checkArgument;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static java.util.Optional.ofNullable;

import static org.apache.commons.lang3.StringUtils.isBlank;

import org.mule.api.annotation.NoExtend;
import org.mule.runtime.api.meta.model.ExtensionModel;

import java.util.Optional;

/**
 * This object matches the mule-artifact.json element within a plugin. The describer holds information that has being picked up
 * from the JSON file (and the pom.xml when implemented). There's no extra logic, such as calculating the URLs needed to feed a
 * class loader or the {@link ExtensionModel} for the current plugin.
 *
 * @since 1.0
 */
@NoExtend
public class MulePluginModel extends AbstractMuleArtifactModel {

  public static final String EXTENSION_LOADER_DESCRIPTOR = "extensionModelLoaderDescriptor";

  private final MuleArtifactLoaderDescriptor extensionModelLoaderDescriptor;
  private final LicenseModel license;

  private MulePluginModel(String name, String minMuleVersion, Product product,
                          MuleArtifactLoaderDescriptor classLoaderModelLoaderDescriptor,
                          MuleArtifactLoaderDescriptor extensionModelLoaderDescriptor,
                          MuleArtifactLoaderDescriptor bundleDescriptor,
                          LicenseModel license) {
    super(name, minMuleVersion, product, classLoaderModelLoaderDescriptor, bundleDescriptor);
    this.extensionModelLoaderDescriptor = extensionModelLoaderDescriptor;
    this.license = license;
  }

  public Optional<MuleArtifactLoaderDescriptor> getExtensionModelLoaderDescriptor() {
    return ofNullable(extensionModelLoaderDescriptor);
  }

  public Optional<LicenseModel> getLicense() {
    return ofNullable(license);
  }

  /**
   * A builder to create instances of {@link MulePluginModel}.
   *
   * @since 1.0
   */
  public static class MulePluginModelBuilder extends AbstractMuleArtifactModelBuilder<MulePluginModelBuilder, MulePluginModel> {

    private Optional<MuleArtifactLoaderDescriptorBuilder> extensionModelDescriptorBuilder = empty();
    private Optional<LicenseModelBuilder> licenseModelBuilder = empty();

    @Override
    protected MulePluginModelBuilder getThis() {
      return this;
    }

    /**
     * @return a {@link MuleArtifactLoaderDescriptorBuilder} to populate the {@link ExtensionModel} describer with the ID and any
     *         additional attributes
     */
    public MuleArtifactLoaderDescriptorBuilder withExtensionModelDescriber() {
      if (!extensionModelDescriptorBuilder.isPresent()) {
        extensionModelDescriptorBuilder = of(new MuleArtifactLoaderDescriptorBuilder());
      }
      return extensionModelDescriptorBuilder.get();
    }

    /**
     * @return a {@link LicenseModelBuilder} to define the license attributes
     */
    public LicenseModelBuilder withLicenseModel() {
      if (!licenseModelBuilder.isPresent()) {
        licenseModelBuilder = of(new LicenseModelBuilder());
      }
      return licenseModelBuilder.get();
    }

    /**
     * @return a well formed {@link MulePluginModel}
     */
    @Override
    public MulePluginModel build() {
      checkArgument(!isBlank(getName()), "name cannot be a blank");
      checkArgument(getBundleDescriptorLoader() != null, "bundleDescriber cannot be null");

      return new MulePluginModel(getName(), getMinMuleVersion(), getRequiredProduct(),
                                 getClassLoaderModelDescriptorLoader(),
                                 extensionModelDescriptorBuilder.isPresent() ? extensionModelDescriptorBuilder.get().build()
                                     : null,
                                 getBundleDescriptorLoader(),
                                 licenseModelBuilder.isPresent() ? licenseModelBuilder.get().build() : null);
    }
  }
}
