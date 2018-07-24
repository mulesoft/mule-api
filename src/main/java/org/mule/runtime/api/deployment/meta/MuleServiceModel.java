/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.runtime.api.deployment.meta;

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

  private final List<MuleServiceContractModel> contracts;

  private MuleServiceModel(String name, String minMuleVersion, Product product,
                           MuleArtifactLoaderDescriptor classLoaderModelLoaderDescriptor,
                           MuleArtifactLoaderDescriptor bundleDescriptorLoader,
                           List<MuleServiceContractModel> contracts) {
    super(name, minMuleVersion, product, classLoaderModelLoaderDescriptor, bundleDescriptorLoader);
    checkArgument(contracts != null && !contracts.isEmpty(), "service must fulfill at least one contract");
    
    this.contracts = unmodifiableList(contracts);
  }

  public List<MuleServiceContractModel> getContracts() {
    return contracts;
  }

  /**
   * A builder to create instances of {@link MuleServiceModel}.
   *
   * @since 1.0
   */
  public static class MuleServiceModelBuilder
      extends AbstractMuleArtifactModelBuilder<MuleServiceModelBuilder, MuleServiceModel> {

    private List<MuleServiceContractModel> contracts = emptyList();

    @Override
    protected MuleServiceModelBuilder getThis() {
      return this;
    }

    public MuleServiceModelBuilder withContracts(List<MuleServiceContractModel> contracts) {
      this.contracts = contracts;
      return this;
    }

    /**
     * @return a well formed {@link MuleServiceModel}
     */
    public MuleServiceModel build() {
      checkArgument(!isBlank(getName()), "name cannot be a blank");
      checkArgument(getMinMuleVersion() != null, "minMuleVersion cannot be null");
      checkArgument(getBundleDescriptorLoader() != null, "bundleDescriber cannot be null");

      return new MuleServiceModel(getName(), getMinMuleVersion(),
                                  getRequiredProduct(),
                                  getClassLoaderModelDescriptorLoader(),
                                  getBundleDescriptorLoader(), contracts);
    }
  }
}
