/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.deployment.meta;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.mule.runtime.api.util.Preconditions.checkArgument;
import org.mule.api.annotation.NoExtend;

/**
 * This object matches the mule-artifact.json element within a policy. The describer holds information that has being picked up
 * from the JSON file (and the pom.xml when implemented).
 *
 * @since 1.0
 */
@NoExtend
public class MulePolicyModel extends AbstractMuleArtifactModel {

  private MulePolicyModel(String name, String minMuleVersion, Product product,
                          MuleArtifactLoaderDescriptor classLoaderModelLoaderDescriptor,
                          MuleArtifactLoaderDescriptor bundleDescriptor) {
    super(name, minMuleVersion, product, classLoaderModelLoaderDescriptor, bundleDescriptor);
  }

  /**
   * A builder to create instances of {@link MulePolicyModel}.
   *
   * @since 1.0
   */
  public static class MulePolicyModelBuilder extends AbstractMuleArtifactModelBuilder<MulePolicyModelBuilder, MulePolicyModel> {


    @Override
    protected MulePolicyModelBuilder getThis() {
      return this;
    }

    /**
     * @return a well formed {@link MulePolicyModel}
     */
    public MulePolicyModel build() {
      checkArgument(!isBlank(getName()), "name cannot be a blank");
      checkArgument(getMinMuleVersion() != null, "minMuleVersion cannot be null");
      checkArgument(getBundleDescriptorLoader() != null, "bundleDescriber cannot be null");

      return new MulePolicyModel(getName(), getMinMuleVersion(), getRequiredProduct(),
                                 getClassLoaderModelDescriptorLoader(),
                                 getBundleDescriptorLoader());
    }
  }
}
