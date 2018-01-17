/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.deployment.meta;

import static java.util.Optional.ofNullable;

import org.mule.api.annotation.NoExtend;
import org.mule.api.annotation.NoImplement;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * This object matches the mule-artifact.json element within a application. The describer holds information that has being picked
 * up from the JSON file (and the pom.xml when implemented).
 *
 * @since 1.0
 */
@NoExtend
public class MuleApplicationModel extends MuleDeployableModel {

  private MuleApplicationModel(String name, String minMuleVersion, Product product,
                               MuleArtifactLoaderDescriptor classLoaderModelLoaderDescriptor,
                               MuleArtifactLoaderDescriptor bundleDescriptor, Set<String> configs,
                               Optional<Boolean> redeploymentEnabled, List<String> secureProperties) {
    super(name, minMuleVersion, product, classLoaderModelLoaderDescriptor, bundleDescriptor, configs, redeploymentEnabled,
          secureProperties);
  }

  /**
   * A builder to create instances of {@link MuleApplicationModel}.
   *
   * @since 1.0
   */
  public static class MuleApplicationModelBuilder
      extends MuleDeployableModelBuilder<MuleApplicationModelBuilder, MuleApplicationModel> {

    @Override
    protected MuleApplicationModelBuilder getThis() {
      return this;
    }

    @Override
    protected MuleApplicationModel doCreateModel(Set<String> configs, Boolean redeploymentEnabled,
                                                 List<String> secureProperties) {
      return new MuleApplicationModel(getName(), getMinMuleVersion(), getRequiredProduct(), getClassLoaderModelDescriptorLoader(),
                                      getBundleDescriptorLoader(), configs, ofNullable(redeploymentEnabled), secureProperties);

    }
  }
}
