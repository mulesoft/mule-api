/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.deployment.meta;

import static java.util.Collections.emptyList;
import static java.util.Collections.unmodifiableList;
import static java.util.Optional.ofNullable;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.mule.runtime.api.util.Preconditions.checkArgument;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * This object matches the mule-policy.json element within a policy. The describer holds information that has being picked up from
 * the JSON file (and the pom.xml when implemented).
 *
 * @since 1.0
 */
public class MuleApplicationModel extends AbstractMuleArtifactModel {

  private final List<String> configs;
  private final String domain;
  private final boolean redeploymentEnabled;

  private MuleApplicationModel(String name, String minMuleVersion,
                               MuleArtifactLoaderDescriptor classLoaderModelLoaderDescriptor,
                               MuleArtifactLoaderDescriptor bundleDescriptor, List<String> configs,
                               Optional<String> domain, Optional<Boolean> redeploymentEnabled) {
    super(name, minMuleVersion, classLoaderModelLoaderDescriptor, bundleDescriptor);
    this.configs = configs == null ? new ArrayList<>() : configs;
    this.domain = domain.orElse(null);
    this.redeploymentEnabled = redeploymentEnabled.orElse(true);
  }

  /**
   * @return the application configuration files
   */
  public List<String> getConfigs() {
    return configs == null ? emptyList() : unmodifiableList(configs);
  }

  /**
   * @return the domain associated with this application
   */
  public Optional<String> getDomain() {
    return ofNullable(domain);
  }

  /**
   * @return true if the application supports redeployment by changing a configuration file.
   */
  public boolean isRedeploymentEnabled() {
    return redeploymentEnabled;
  }

  /**
   * A builder to create instances of {@link MuleApplicationModel}.
   *
   * @since 1.0
   */
  public static class MuleApplicationModelBuilder
      extends AbstractMuleArtifactModelBuilder<MuleApplicationModelBuilder, MuleApplicationModel> {

    private List<String> configs = new ArrayList<>();
    private String domain;
    private Boolean redeploymentEnabled;

    /**
     * @param configs the set of application configuration files
     */
    public void setConfigs(List<String> configs) {
      this.configs = configs;
    }

    @Override
    protected MuleApplicationModelBuilder getThis() {
      return this;
    }

    /**
     * @return a well formed {@link MuleApplicationModel}
     */
    public MuleApplicationModel build() {
      checkArgument(!isBlank(getName()), "name cannot be a blank");
      checkArgument(getMinMuleVersion() != null, "minMuleVersion cannot be null");
      checkArgument(getBundleDescriptorLoader() != null, "bundleDescriber cannot be null");
      return new MuleApplicationModel(getName(), getMinMuleVersion(), getClassLoaderModelDescriptorLoader(),
                                      getBundleDescriptorLoader(), configs, ofNullable(domain), ofNullable(redeploymentEnabled));
    }

    /**
     * @param domain the domain associated with this application
     * @return the same builder instance.
     */
    public MuleApplicationModelBuilder setDomain(String domain) {
      this.domain = domain;
      return this;
    }

    /**
     * @param redeploymentEnabled true if the application supports redeployment, false otherwise.
     */
    public void setRedeploymentEnabled(boolean redeploymentEnabled) {
      this.redeploymentEnabled = redeploymentEnabled;
    }
  }
}
