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
 * This object matches the mule-policy.json element within a policy. The describer holds information that has being picked up from
 * the JSON file (and the pom.xml when implemented).
 *
 * @since 1.0
 */
public class MulePolicyModel extends AbstractMuleArtifactModel {

  private MulePolicyModel(String name, String minMuleVersion,
                          MuleArtifactLoaderDescriptor classLoaderModelLoaderDescriptor) {
    super(name, minMuleVersion, classLoaderModelLoaderDescriptor);
  }

  /**
   * A builder to create instances of {@link MulePolicyModel}.
   *
   * @since 1.0
   */
  public static class MulePolicyModelBuilder extends AbstractMuleArtifactModelBuilder<MulePolicyModelBuilder, MulePolicyModel> {


    private MuleArtifactLoaderDescriptor classLoaderDescriptorBuilder;

    @Override
    protected MulePolicyModelBuilder getThis() {
      return this;
    }

    /**
     * @return a {@link MuleArtifactLoaderDescriptor} to populate the {@link ClassLoader} describer with the ID and any additional
     *         attributes
     */
    public MulePolicyModelBuilder withClassLoaderModelDescriber(MuleArtifactLoaderDescriptor policyClassLoaderModelDescriber) {
      checkArgument(policyClassLoaderModelDescriber != null, "policyClassLoaderModelDescriber cannot be null");
      classLoaderDescriptorBuilder = policyClassLoaderModelDescriber;

      return this;
    }

    /**
     * @return a well formed {@link MulePolicyModel}
     */
    public MulePolicyModel build() {
      checkArgument(!isBlank(getName()), "name cannot be a blank");
      checkArgument(getMinMuleVersion() != null, "minMuleVersion cannot be null");

      return new MulePolicyModel(getName(), getMinMuleVersion(), classLoaderDescriptorBuilder);
    }
  }
}
