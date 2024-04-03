/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.metadata.descriptor;

import static org.mule.runtime.api.util.Preconditions.checkArgument;

import org.mule.api.annotation.NoExtend;

import java.util.HashMap;
import java.util.Map;

/**
 * Base class for builders that produce instances of {@link BaseInputMetadataDescriptor}.
 *
 * <b>NOTE:</b> This class is to ONLY be implemented by Mule Runtime. No customer or external component should extend it.
 *
 * @param <T> the generic type of the produced descriptor
 * @param <B> the generic type of the builder concrete implementation
 * @since 1.7.0
 */
@NoExtend
abstract class BaseInputMetadataDescriptorBuilder<T extends BaseInputMetadataDescriptor, B extends BaseInputMetadataDescriptorBuilder> {

  protected Map<String, ParameterMetadataDescriptor> parameters = new HashMap<>();

  /**
   * Ads a parameter to the resulting {@link InputMetadataDescriptor}.
   *
   * @param name            the name of the parameter
   * @param parameterResult the {@link ParameterMetadataDescriptor} representing this parameter type.
   * @return this {@link InputMetadataDescriptor.InputMetadataDescriptorBuilder}.
   */
  public B withParameter(String name, ParameterMetadataDescriptor parameterResult) {
    if (parameterResult == null) {
      throw new IllegalArgumentException("A null ParameterMetadataDescriptor is not valid for the InputMetadataDescriptor");
    }
    parameters.put(name, parameterResult);
    return (B) this;
  }

  public B withParameters(Map<String, ParameterMetadataDescriptor> parameters) {
    checkArgument(parameters != null, "parameters cannot be null");
    this.parameters.putAll(parameters);

    return (B) this;
  }

  public abstract T build();
}
