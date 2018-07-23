/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.runtime.api.deployment.meta;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.unmodifiableList;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.mule.runtime.api.util.Preconditions.checkArgument;
import org.mule.api.annotation.NoExtend;

import java.util.List;

/**
 * This object matches the mule-artifact.json element within a service. The describer holds information that has been picked up
 * from the JSON file (and the pom.xml when implemented).
 *
 * @since 1.0
 */
@NoExtend
public class MuleServiceModel extends AbstractMuleArtifactModel {

  private static final String SERVICE_PROVIDER_CLASS_NAME = "serviceProviderClassName";
  private final String serviceProviderClassName;
  private final List<String> satisfiedServiceClassNames;

  private MuleServiceModel(String name, String minMuleVersion, Product product,
                           MuleArtifactLoaderDescriptor classLoaderModelLoaderDescriptor,
                           MuleArtifactLoaderDescriptor bundleDescriptorLoader, List<String> satisfiedServiceClassNames,
                           String serviceProviderClassName) {
    super(name, minMuleVersion, product, classLoaderModelLoaderDescriptor, bundleDescriptorLoader);
    this.satisfiedServiceClassNames = satisfiedServiceClassNames;
    this.serviceProviderClassName = serviceProviderClassName;
  }

  public String getServiceProviderClassName() {
    return serviceProviderClassName;
  }

  public List<String> getSatisfiedServiceClassNames() {
    return satisfiedServiceClassNames;
  }

  @Override
  protected void doValidateCustomFields(String descriptorName) {
    validateMandatoryFieldIsSet(descriptorName, serviceProviderClassName, SERVICE_PROVIDER_CLASS_NAME);
  }

  /**
   * A builder to create instances of {@link MuleServiceModel}.
   *
   * @since 1.0
   */
  public static class MuleServiceModelBuilder
      extends AbstractMuleArtifactModelBuilder<MuleServiceModelBuilder, MuleServiceModel> {

    private String serviceProviderClassName;
    private List<String> satisfiedServiceClassNames = emptyList();

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

    public MuleServiceModelBuilder satisfyingServiceClassNames(String... satisfiedServiceClassNames) {
      if (satisfiedServiceClassNames == null || satisfiedServiceClassNames.length == 0) {
        this.satisfiedServiceClassNames = emptyList();
      } else {
        this.satisfiedServiceClassNames = unmodifiableList(asList(satisfiedServiceClassNames));
      }

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
                                  getRequiredProduct(),
                                  getClassLoaderModelDescriptorLoader(),
                                  getBundleDescriptorLoader(), satisfiedServiceClassNames, serviceProviderClassName);
    }
  }
}
