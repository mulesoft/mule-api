/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.metadata.descriptor;

import static java.lang.String.format;
import static java.util.Collections.unmodifiableMap;
import org.mule.runtime.api.message.Message;
import org.mule.runtime.api.metadata.MetadataProvider;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents the view of all the Metadata associated to an Component's {@link Message} output
 *
 * @since 1.0
 */
public final class InputMetadataDescriptor {

  private final Map<String, ParameterMetadataDescriptor> parameters;

  private InputMetadataDescriptor(Map<String, ParameterMetadataDescriptor> parameters) {
    this.parameters = parameters;
  }

  public static InputMetadataDescriptorBuilder builder() {
    return new InputMetadataDescriptorBuilder();
  }

  /**
   * @return a {@link TypeMetadataDescriptor} that describes the Component's output {@link Message#getPayload}
   */
  public ParameterMetadataDescriptor getParameterMetadata(String paramName) {
    ParameterMetadataDescriptor result = parameters.get(paramName);
    if (result == null) {
      throw new IllegalArgumentException(format("The parameter [%s] does not belong to the described component", paramName));
    }
    return result;
  }

  /**
   * @return a {@link TypeMetadataDescriptor} that describes the Component's output {@link Message#getAttributes}
   */
  public Map<String, ParameterMetadataDescriptor> getAllParameters() {
    return unmodifiableMap(parameters);
  }

  /**
   * Implementation of the builder design pattern to create instances of {@link OutputMetadataDescriptor}
   *
   * @since 1.0
   */
  public static class InputMetadataDescriptorBuilder {

    private Map<String, ParameterMetadataDescriptor> parameters = new HashMap<>();

    /**
     * Creates a new instance of {@link InputMetadataDescriptorBuilder}
     */
    private InputMetadataDescriptorBuilder() {}

    /**
     * Ads a parameter to the resulting {@link InputMetadataDescriptor}.
     *
     * @param name            the name of the parameter
     * @param parameterResult the {@link ParameterMetadataDescriptor} representing this parameter type.
     * @return this {@link InputMetadataDescriptorBuilder}.
     */
    public InputMetadataDescriptorBuilder withParameter(String name, ParameterMetadataDescriptor parameterResult) {
      if (parameterResult == null) {
        throw new IllegalArgumentException("A null ParameterMetadataDescriptor is not valid for the InputMetadataDescriptor");
      }
      parameters.put(name, parameterResult);
      return this;
    }

    /**
     * @return a {@link OutputMetadataDescriptor} instance with the metadata description for the output of a
     *         {@link MetadataProvider} component
     * @throws IllegalArgumentException if the {@link Message#getPayload} or {@link Message#getAttributes} were not set during
     *                                  building
     */
    public InputMetadataDescriptor build() {
      return new InputMetadataDescriptor(parameters);
    }

  }

}
