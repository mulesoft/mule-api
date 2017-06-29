/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model;

import static java.util.Optional.of;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.mule.runtime.api.util.Preconditions.checkArgument;
import static org.mule.runtime.api.util.Preconditions.checkState;
import org.mule.runtime.api.meta.DescribedObject;
import org.mule.runtime.api.meta.MuleVersion;
import org.mule.runtime.api.meta.NamedObject;

import java.util.Optional;

/**
 * Describes a Maven dependency that the extension needs on runtime but is not packaged with it.
 *
 * @since 1.0
 */
public final class ExternalDependencyModel implements NamedObject, DescribedObject {

  /**
   * A Builder for creating instances of {@link ExternalDependencyModel}.
   * <p>
   * Instances are to be created through the {@link ExternalDependencyModel#builder()} method. Instances are not reusable.
   *
   * @since 1.0
   */
  public static class ExternalDependencyModelBuilder {

    private final ExternalDependencyModel product = new ExternalDependencyModel();

    private ExternalDependencyModelBuilder() {}


    /**
     * Sets the name of the dependency. It is mandatory to supply a name.
     *
     * @param name the dependency name
     * @return {@code this} builder
     * @throws IllegalArgumentException if {@code name} is {@code null}
     */
    public ExternalDependencyModelBuilder withName(String name) {
      checkArgument(name != null && !name.trim().isEmpty(), "name cannot be blank");
      product.name = name;
      return this;
    }

    /**
     * Sets a description for the dependency
     *
     * @param description the dependency's description
     * @return {@code this} builder
     */
    public ExternalDependencyModelBuilder withDescription(String description) {
      product.description = description;
      return this;
    }

    /**
     * Sets the groupId of the dependency. It is mandatory to supply a groupId.
     *
     * @param groupId the dependency groupId
     * @return {@code this} builder
     * @throws IllegalArgumentException if {@code groupId} is {@code null} or empty
     */
    public ExternalDependencyModelBuilder withGroupId(String groupId) {
      checkArgument(!isBlank(groupId), "groupId cannot be blank");
      product.groupId = groupId;
      return this;
    }

    /**
     * Sets the artifactId of the dependency. It is mandatory to supply an artifactId.
     *
     * @param artifactId the dependency artifactId
     * @return {@code this} builder
     * @throws IllegalArgumentException if {@code artifactId} is {@code null} or empty
     */
    public ExternalDependencyModelBuilder withArtifactId(String artifactId) {
      checkArgument(!isBlank(artifactId), "artifactId cannot be blank");
      product.artifactId = artifactId;
      return this;
    }

    /**
     * If provided, the dependency version should be less or equal to the {@code minVersion}
     *
     * @param minVersion the version
     * @return {@code this} builder
     */
    public ExternalDependencyModelBuilder withMinVersion(String minVersion) {
      product.minVersion = of(new MuleVersion(minVersion));
      return this;
    }

    /**
     * If provided, the dependency version should be newer or equal to the {@code maxVersion}
     *
     * @param maxVersion the version
     * @return {@code this} builder
     */
    public ExternalDependencyModelBuilder withMaxVersion(String maxVersion) {
      product.maxVersion = of(new MuleVersion(maxVersion));
      return this;
    }

    /**
     * @return a new {@link ExternalDependencyModel} instance
     * @throws IllegalStateException if {@link #withName(String)} was not provided
     * @throws IllegalStateException if {@link #withArtifactId(String)} or {@link #withGroupId(String)} were not provided
     */
    public ExternalDependencyModel build() {
      checkState(product.name != null, "name was not provided");
      checkState(!isBlank(product.artifactId) && !isBlank(product.groupId),
                 "artifactId and groupId are required and were not provided");
      return product;
    }
  }

  /**
   * @return a new {@link ExternalDependencyModelBuilder}
   */
  public static ExternalDependencyModelBuilder builder() {
    return new ExternalDependencyModelBuilder();
  }

  private String groupId;
  private String artifactId;
  private Optional<MuleVersion> minVersion = Optional.empty();
  private Optional<MuleVersion> maxVersion = Optional.empty();
  private String name;
  private String description;

  private ExternalDependencyModel() {}

  /**
   * @return The dependency name. Will never be {@code null}
   */
  @Override
  public String getName() {
    return name;
  }

  /**
   * @return The dependency description. Defaults to an empty string.
   */
  @Override
  public String getDescription() {
    return description;
  }

  /**
   * @return The dependency groupId. Will never be {@code null}
   */
  public String getGroupId() {
    return groupId;
  }

  /**
   * @return The dependency artifactId. Will never be {@code null}
   */
  public String getArtifactId() {
    return artifactId;
  }

  public Optional<MuleVersion> getMinVersion() {
    return minVersion;
  }

  public Optional<MuleVersion> getMaxVersion() {
    return maxVersion;
  }
}
