/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.deployment.meta;

import static java.util.Collections.emptyList;
import static java.util.Collections.unmodifiableList;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.mule.runtime.api.util.Preconditions.checkArgument;
import static org.mule.runtime.api.util.Preconditions.checkState;
import org.mule.api.annotation.NoExtend;

import java.util.List;

/**
 * This object matches the mule-artifact.json element within a service artifact. The describer holds information that has been
 * picked up from the JSON file (and the pom.xml when implemented).
 *
 * @since 1.0
 */
@NoExtend
public class MuleServiceModel extends AbstractMuleArtifactModel {

  private static final String CONTRACT_CLASS_NAME = "contractClassName";
  private static final String SERVICE_PROVIDER_CLASS_NAME = "serviceProviderClassName";

  private final List<MuleServiceContractModel> contracts;

  private MuleServiceModel(String name, String minMuleVersion, Product product,
                           MuleArtifactLoaderDescriptor classLoaderModelLoaderDescriptor,
                           MuleArtifactLoaderDescriptor bundleDescriptorLoader,
                           List<MuleServiceContractModel> contracts) {
    super(name, minMuleVersion, product, classLoaderModelLoaderDescriptor, bundleDescriptorLoader);
    checkArgument(contracts != null && !contracts.isEmpty(), "service must fulfill at least one contract");

    this.contracts = unmodifiableList(contracts);
  }

  /**
   * @return Describes the contracts that are fulfilled by the elements in the described bundle
   * @since 1.2
   */
  public List<MuleServiceContractModel> getContracts() {
    return contracts;
  }

  @Override
  protected void doValidateCustomFields(String descriptorName) {
    checkState(contracts != null && !contracts.isEmpty(), "Service must fulfill at least one contract");
    contracts.forEach(contract -> {
      validateMandatoryFieldIsSet(CONTRACT_CLASS_NAME, contract.getContractClassName(), CONTRACT_CLASS_NAME);
      validateMandatoryFieldIsSet(SERVICE_PROVIDER_CLASS_NAME, contract.getServiceProviderClassName(),
                                  SERVICE_PROVIDER_CLASS_NAME);
    });
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
