/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.runtime.api.deployment.meta;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.mule.runtime.api.util.Preconditions.checkArgument;

/**
 * This object matches the mule-artifact.json element within a service. The describer holds information that has being picked up
 * from the JSON file (and the pom.xml when implemented).
 *
 * @since 1.0
 */
public class MuleServiceModel extends AbstractMuleArtifactModel {

  private final String serviceProviderClassName;

  private MuleServiceModel(String name, String minMuleVersion,
                           MuleArtifactLoaderDescriptor classLoaderModelLoaderDescriptor,
                           MuleArtifactLoaderDescriptor bundleDescriptorLoader, String serviceProviderClassName) {
    super(name, minMuleVersion, classLoaderModelLoaderDescriptor, bundleDescriptorLoader);
    this.serviceProviderClassName = serviceProviderClassName;
  }

  public String getServiceProviderClassName() {
    return serviceProviderClassName;
  }

  /**
   * A builder to create instances of {@link MuleServiceModel}.
   *
   * @since 1.0
   */
  public static class MuleServiceModelBuilder
      extends AbstractMuleArtifactModelBuilder<MuleServiceModelBuilder, MuleServiceModel> {

    private String serviceProviderClassName;

    @Override
    protected MuleServiceModelBuilder getThis() {
      return this;
    }

    /**
     * Configures the provider for the service
     *
     * @param className name of the class providing the service
     * @return same builder instance
     */
    public MuleServiceModelBuilder withServiceProviderClassName(String className) {
      this.serviceProviderClassName = className;

      return this;
    }

    /**
     * @return a well formed {@link MuleServiceModel}
     */
    public MuleServiceModel build() {
      checkArgument(!isBlank(getName()), "name cannot be a blank");
      checkArgument(!isBlank(serviceProviderClassName), "serviceProviderClassName cannot be blank");
      checkArgument(getMinMuleVersion() != null, "minMuleVersion cannot be null");
      checkArgument(getBundleDescriptorLoader() != null, "bundleDescriber cannot be null");

      return new MuleServiceModel(getName(), getMinMuleVersion(),
                                  getClassLoaderModelDescriptorLoader(),
                                  getBundleDescriptorLoader(), serviceProviderClassName);
    }
  }
}
