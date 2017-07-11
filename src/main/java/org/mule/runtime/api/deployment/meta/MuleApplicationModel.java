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

/**
 * This object matches the mule-artifact.json element within a policy. The describer holds information that has being picked up from
 * the JSON file (and the pom.xml when implemented).
 *
 * @since 1.0
 */
public class MuleApplicationModel extends MuleDeployableModel {

  private final String domain;

  private MuleApplicationModel(String name, String minMuleVersion,
                               MuleArtifactLoaderDescriptor classLoaderModelLoaderDescriptor,
                               MuleArtifactLoaderDescriptor bundleDescriptor, List<String> configs,
                               Optional<String> domain, Optional<Boolean> redeploymentEnabled) {
    super(name, minMuleVersion, classLoaderModelLoaderDescriptor, bundleDescriptor, configs, redeploymentEnabled);
    this.domain = domain.orElse(null);
  }


  /**
   * @return the domain associated with this application
   */
  public Optional<String> getDomain() {
    return ofNullable(domain);
  }

  /**
   * A builder to create instances of {@link MuleApplicationModel}.
   *
   * @since 1.0
   */
  public static class MuleApplicationModelBuilder
      extends MuleDeployableModelBuilder<MuleApplicationModelBuilder, MuleApplicationModel> {

    private String domain;

    @Override
    protected MuleApplicationModelBuilder getThis() {
      return this;
    }

    /**
     * @param domain the domain associated with this application
     * @return the same builder instance.
     */
    public MuleApplicationModelBuilder setDomain(String domain) {
      this.domain = domain;
      return this;
    }

    @Override
    protected MuleApplicationModel doCreateModel(List<String> configs, Boolean redeploymentEnabled) {
      return new MuleApplicationModel(getName(), getMinMuleVersion(), getClassLoaderModelDescriptorLoader(),
                                      getBundleDescriptorLoader(), configs, ofNullable(domain), ofNullable(redeploymentEnabled));

    }
  }
}
