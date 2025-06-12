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

/**
 * This object matches the mule-artifact.json element within a application. The describer holds information that has being picked
 * up from the JSON file (and the pom.xml when implemented).
 *
 * @since 1.0
 */
@NoExtend
public class MuleApplicationModel extends MuleDeployableModel {

  private String domain;

  private MuleApplicationModel() {
    // Nothing to do, this is just for allowing instantiation to GSON
  }

  private MuleApplicationModel(String name,
                               String minMuleVersion,
                               Product product,
                               MuleArtifactLoaderDescriptor classLoaderModelLoaderDescriptor,
                               MuleArtifactLoaderDescriptor bundleDescriptor,
                               Set<String> configs,
                               Optional<String> domain,
                               Optional<Boolean> redeploymentEnabled,
                               List<String> secureProperties,
                               Set<String> supportedJavaVersions,
                               String logConfigFile) {
    super(name,
          minMuleVersion,
          product,
          classLoaderModelLoaderDescriptor,
          bundleDescriptor,
          configs,
          redeploymentEnabled,
          secureProperties,
          supportedJavaVersions,
          logConfigFile);
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
    protected MuleApplicationModel doCreateModel(Set<String> configs,
                                                 Boolean redeploymentEnabled,
                                                 List<String> secureProperties,
                                                 Set<String> supportedJavaVersions,
                                                 String logConfigFile) {
      return new MuleApplicationModel(getName(),
                                      getMinMuleVersion(),
                                      getRequiredProduct(),
                                      getClassLoaderModelDescriptorLoader(),
                                      getBundleDescriptorLoader(),
                                      configs,
                                      ofNullable(domain),
                                      ofNullable(redeploymentEnabled),
                                      secureProperties,
                                      supportedJavaVersions,
                                      logConfigFile);

    }
  }
}
