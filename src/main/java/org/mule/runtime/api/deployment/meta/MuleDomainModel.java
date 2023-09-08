/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.deployment.meta;

import static java.util.Optional.ofNullable;

import org.mule.api.annotation.NoExtend;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@NoExtend
public class MuleDomainModel extends MuleDeployableModel {

  private MuleDomainModel(String name, String minMuleVersion, Product product,
                          MuleArtifactLoaderDescriptor classLoaderModelLoaderDescriptor,
                          MuleArtifactLoaderDescriptor bundleDescriptor, Set<String> configs,
                          Optional<Boolean> redeploymentEnabled, List<String> secureProperties, String logConfigFile) {
    super(name, minMuleVersion, product, classLoaderModelLoaderDescriptor, bundleDescriptor, configs, redeploymentEnabled,
          secureProperties, logConfigFile);
  }

  /**
   * A builder to create instances of {@link MuleDomainModel}.
   *
   * @since 1.0
   */
  public static class MuleDomainModelBuilder
      extends MuleDeployableModelBuilder<MuleDomainModel.MuleDomainModelBuilder, MuleDomainModel> {

    @Override
    protected MuleDomainModelBuilder getThis() {
      return this;
    }

    @Override
    protected MuleDomainModel doCreateModel(Set<String> configs, Boolean redeploymentEnabled, List<String> secureProperties,
                                            String logConfigFile) {
      return new MuleDomainModel(getName(), getMinMuleVersion(), getRequiredProduct(), getClassLoaderModelDescriptorLoader(),
                                 getBundleDescriptorLoader(), configs, ofNullable(redeploymentEnabled), secureProperties,
                                 logConfigFile);
    }
  }
}
