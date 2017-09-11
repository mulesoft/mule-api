/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.runtime.api.deployment.meta;

import static java.util.Optional.ofNullable;

import java.util.List;
import java.util.Optional;

public class MuleDomainModel extends MuleDeployableModel {

  private MuleDomainModel(String name, String minMuleVersion, Product product,
                          MuleArtifactLoaderDescriptor classLoaderModelLoaderDescriptor,
                          MuleArtifactLoaderDescriptor bundleDescriptor, List<String> configs,
                          Optional<Boolean> redeploymentEnabled) {
    super(name, minMuleVersion, product, classLoaderModelLoaderDescriptor, bundleDescriptor, configs, redeploymentEnabled);
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
    protected MuleDomainModel doCreateModel(List<String> configs, Boolean redeploymentEnabled) {
      return new MuleDomainModel(getName(), getMinMuleVersion(), getProduct(), getClassLoaderModelDescriptorLoader(),
                                 getBundleDescriptorLoader(), configs, ofNullable(redeploymentEnabled));
    }
  }
}
