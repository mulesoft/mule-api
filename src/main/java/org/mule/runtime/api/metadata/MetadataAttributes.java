/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.metadata;

import static java.util.Optional.*;
import static org.mule.runtime.api.util.Preconditions.checkArgument;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Immutable implementation of {@link MetadataAttributes}.
 *
 * @since 1.0
 */
public final class MetadataAttributes {

  private final MetadataKey key;
  private final String category;
  private final Map<String, String> parameters;
  private final String outputResolver;
  private final String attributesResolver;

  private MetadataAttributes(MetadataKey key, String category,
                             Map<String, String> parameters, String outputResolver,
                             String attributesResolver) {
    this.key = key;
    this.category = category;
    this.parameters = parameters;
    this.outputResolver = outputResolver;
    this.attributesResolver = attributesResolver;
  }

  public static MetadataAttributesBuilder builder() {
    return new MetadataAttributesBuilder();
  }

  /**
   * Provides the {@link MetadataKey} associated (if any) to the resolution of the component's metadata.
   *
   * @return {@link MetadataKey} used for the metadata resolution.
   */
  public Optional<MetadataKey> getKey() {
    return ofNullable(key);
  }

  /**
   * Provides the name of the resolver associated to a given parameter.
   *
   * @param parameterName name of the parameter
   * @return Name of the resolver associated to the parameter
   */
  public String getParameterResolverName(String parameterName) {
    checkArgument(parameters.containsKey(parameterName), "Parameter named %s is not associated to a resolver");
    return parameters.get(parameterName);
  }

  /**
   * Provides the name of the metadata category associated to the Component.
   *
   * @return category name
   */
  public String getCategoryName() {
    return category;
  }

  /**
   * Provides the name of the output resolver (if any) associated to the Component.
   *
   * @return output resolver's name
   */
  public Optional<String> getOutputResolverName() {
    return ofNullable(outputResolver);
  }

  /**
   * Provides the name of the output attributes resolver (if any) associated to the Component.
   *
   * @return output resolver's name
   */
  public Optional<String> getOutputAttributesResolverName() {
    return ofNullable(attributesResolver);
  }

  public static class MetadataAttributesBuilder {

    private MetadataKey metadataKey;
    private String categoryName;
    private Map<String, String> parameterResolvers = new HashMap<>();
    private String outputResolver;
    private String outputAttributesResolver;

    private MetadataAttributesBuilder() {}

    public MetadataAttributesBuilder withParameterResolver(String parameterName, String resolverName) {
      this.parameterResolvers.put(parameterName, resolverName);
      return this;
    }

    public MetadataAttributesBuilder withOutputResolver(String outputResolver) {
      this.outputResolver = outputResolver;
      return this;
    }

    public MetadataAttributesBuilder withOutputAttributesResolver(String outputAttributesResolver) {
      this.outputAttributesResolver = outputAttributesResolver;
      return this;
    }

    public MetadataAttributesBuilder withKey(MetadataKey key) {
      this.metadataKey = key;
      return this;
    }

    public MetadataAttributesBuilder withCategoryName(String categoryName) {
      this.categoryName = categoryName;
      return this;
    }

    public MetadataAttributes build() {
      return new MetadataAttributes(metadataKey, categoryName, parameterResolvers,
                                    outputResolver,
                                    outputAttributesResolver);
    }

  }

}
