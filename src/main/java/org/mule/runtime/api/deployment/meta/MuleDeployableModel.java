/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.runtime.api.deployment.meta;

import static java.util.Collections.unmodifiableSet;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.mule.runtime.api.util.Preconditions.checkArgument;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Base class for defining models for deployable artifacts.
 *
 * @since 1.0
 */
public abstract class MuleDeployableModel extends AbstractMuleArtifactModel {

  private final Set<String> configs;

  // this field must be true by default because it's the default value used when deserializing this class with no content.
  private Boolean redeploymentEnabled = true;

  /**
   * Creates a new model
   * 
   * @param name name of the artifact
   * @param minMuleVersion minimum Mule Runtime version that requires to work correctly.
   * @param product the target product for this artifact
   * @param classLoaderModelLoaderDescriptor describes how to create the class loader for the artifact.
   * @param bundleDescriptorLoader indicates how to load the bundle descriptor.
   * @param configs the application configuration files
   * @param redeploymentEnabled indicates if the artifact can be redeployed or not
   */
  protected MuleDeployableModel(String name, String minMuleVersion, Product product,
                                MuleArtifactLoaderDescriptor classLoaderModelLoaderDescriptor,
                                MuleArtifactLoaderDescriptor bundleDescriptorLoader, Set<String> configs,
                                Optional<Boolean> redeploymentEnabled) {
    super(name, minMuleVersion, product, classLoaderModelLoaderDescriptor, bundleDescriptorLoader);
    this.configs = configs;
    this.redeploymentEnabled = redeploymentEnabled.orElse(true);
  }

  /**
   * @return the application configuration files
   */
  public Set<String> getConfigs() {
    return configs == null ? null : unmodifiableSet(this.configs);
  }

  /**
   * @return true if the application supports redeployment by changing a configuration file.
   */
  public boolean isRedeploymentEnabled() {
    return redeploymentEnabled == null ? true : redeploymentEnabled;
  }

  /**
   * A builder to create instances of {@link MuleDeployableModelBuilder}.
   *
   * @since 1.0
   */
  protected static abstract class MuleDeployableModelBuilder<T extends AbstractMuleArtifactModelBuilder, M extends MuleDeployableModel>
      extends AbstractMuleArtifactModelBuilder<T, M> {

    private Set<String> configs = new HashSet<>();
    private Boolean redeploymentEnabled;

    /**
     * @param configs the set of artifact configuration files
     */
    public void setConfigs(Set<String> configs) {
      this.configs = configs;
    }

    /**
     * @return a well formed {@link MuleDomainModel}
     */
    public final M build() {
      checkArgument(!isBlank(getName()), "name cannot be a blank");
      checkArgument(getMinMuleVersion() != null, "minMuleVersion cannot be null");
      checkArgument(getBundleDescriptorLoader() != null, "bundleDescriber cannot be null");
      return doCreateModel(configs, redeploymentEnabled);
    }

    protected abstract M doCreateModel(Set<String> configs, Boolean redeploymentEnabled);

    /**
     * @param redeploymentEnabled true if the artifact supports redeployment, false otherwise.
     */
    public void setRedeploymentEnabled(boolean redeploymentEnabled) {
      this.redeploymentEnabled = redeploymentEnabled;
    }
  }
}
