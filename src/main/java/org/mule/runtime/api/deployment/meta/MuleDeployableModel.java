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

import org.mule.api.annotation.NoExtend;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Base class for defining models for deployable artifacts.
 *
 * @since 1.0
 */
@NoExtend
public abstract class MuleDeployableModel extends AbstractMuleArtifactModel {

  private final Set<String> configs;
  private final List<String> secureProperties;

  // this field must be true by default because it's the default value used when deserializing this class with no content.
  private Boolean redeploymentEnabled = true;
  private String logConfigFile;

  /**
   * Creates a new model
   *
   * @param name                             name of the artifact
   * @param minMuleVersion                   minimum Mule Runtime version that requires to work correctly.
   * @param product                          the target product for this artifact
   * @param classLoaderModelLoaderDescriptor describes how to create the class loader for the artifact.
   * @param bundleDescriptorLoader           indicates how to load the bundle descriptor.
   * @param configs                          the application configuration files
   * @param redeploymentEnabled              indicates if the artifact can be redeployed or not
   * @param secureProperties                 the list of properties names that must be handled as secrets. Those properties names
   *                                         won't be shown in the runtime manager UI when looking at the deployment configuration
   *                                         of the artifact.
   * @param logConfigFile                    the location of the file to use as the log4j configuration for this artifact instead
   *                                         of the default. May be null.
   */
  protected MuleDeployableModel(String name, String minMuleVersion, Product product,
                                MuleArtifactLoaderDescriptor classLoaderModelLoaderDescriptor,
                                MuleArtifactLoaderDescriptor bundleDescriptorLoader, Set<String> configs,
                                Optional<Boolean> redeploymentEnabled, List<String> secureProperties, String logConfigFile) {
    super(name, minMuleVersion, product, classLoaderModelLoaderDescriptor, bundleDescriptorLoader);
    this.configs = configs;
    this.redeploymentEnabled = redeploymentEnabled.orElse(true);
    this.secureProperties = secureProperties;
    this.logConfigFile = logConfigFile;
  }

  /**
   * Returns the configuration files for the deployable artifact if they have been analyzed, i.e. they were provided at creation
   * time. It could be an empty {@link Set} in case no configuration files were present.
   * <p>
   * In case configuration files analysis was deferred to a later time, the returned value will be {@code null}.
   *
   * @return the configuration files for the deployable artifact if they have been analyzed, or {@code null} if not.
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
   * @return the list of properties names that must be handled as secrets. Those properties names won't be shown in the runtime
   *         manager UI when looking at the deployment configuration of the artifact.
   */
  public List<String> getSecureProperties() {
    return secureProperties;
  }

  /**
   * @return the location of the file to use as the log4j configuration for this artifact instead of the default. May be null.
   */
  public String getLogConfigFile() {
    return logConfigFile;
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
    private List<String> secureProperties = new ArrayList<>();
    private String logConfigFile;

    /**
     * @param configs the set of artifact configuration files, or {@code null} if the configuration files analysis will be made at
     *                a later time.
     */
    public void setConfigs(Set<String> configs) {
      this.configs = configs;
    }

    /**
     * @return a well formed {@link MuleDeployableModel}.
     */
    @Override
    public final M build() {
      checkArgument(!isBlank(getName()), "name cannot be a blank");
      checkArgument(getMinMuleVersion() != null, "minMuleVersion cannot be null");
      checkArgument(getBundleDescriptorLoader() != null, "bundleDescriber cannot be null");
      return doCreateModel(configs, redeploymentEnabled, secureProperties, logConfigFile);
    }

    protected abstract M doCreateModel(Set<String> configs, Boolean redeploymentEnabled, List<String> secureProperties,
                                       String logConfigFile);

    /**
     * @param redeploymentEnabled true if the artifact supports redeployment, false otherwise.
     */
    public void setRedeploymentEnabled(boolean redeploymentEnabled) {
      this.redeploymentEnabled = redeploymentEnabled;
    }

    /**
     * @return secureProperties the list of properties names that must be handled as secrets. Those properties names won't be
     *         shown in the runtime manager UI when looking at the deployment configuration of the artifact.
     */
    public void setSecureProperties(List<String> secureProperties) {
      this.secureProperties = secureProperties;
    }

    /**
     * @param logConfigFile the location of the file to use as the log4j configuration for this artifact instead of the default.
     */
    public void setLogConfigFile(String logConfigFile) {
      this.logConfigFile = logConfigFile;
    }
  }
}
