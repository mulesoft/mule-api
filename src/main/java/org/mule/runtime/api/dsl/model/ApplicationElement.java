/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.dsl.model;

import static java.util.Collections.unmodifiableMap;
import static java.util.Optional.ofNullable;
import static org.mule.runtime.api.util.Preconditions.checkState;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * An {@code ApplicationElement} represents the user configuration of an application element (flow, config, message processor, etc)
 * defined in an artifact configuration file.
 * <p/>
 * Which configuration element this object represents is identified by a {@link ApplicationElementIdentifier} that can be
 * retrieved using {@code #getIdentifier}.
 * <p/>
 * It may have simple configuration parameters which are retrieve by using {@code #getParameters} or complex parameters which are
 * retrieved using {@code #getInnerComponents}.
 *
 * @since 1.0
 */
public class ApplicationElement {

  private ApplicationElementIdentifier identifier;
  private String value;
  private Map<String, String> parameters = new HashMap<>();
  private List<ApplicationElement> innerElements = new LinkedList<>();

  /**
   * @return the configuration identifier.
   */
  public ApplicationElementIdentifier getIdentifier() {
    return identifier;
  }

  /**
   * @return a {@code java.util.Map} with the simple parameters of the configuration.
   */
  public Map<String, String> getParameters() {
    return unmodifiableMap(parameters);
  }

  /**
   * @return a {@code java.util.List} of all the child {@code ComponentModel}s
   */
  public List<ApplicationElement> getInnerComponents() {
    return innerElements;
  }

  /**
   * Provides the text value of the node represented by {@code this} {@link ApplicationElement}.
   * If {@code this} {@link ApplicationElement} represents an attribute in the DSL,
   * then the return of this method will be the string value set on the attribute.
   * For the case of nested elements we can have no value (this represents a node that has child elements)
   * or the value of the node's textContent.
   *
   * @return the string content of the represented element, if one is present.
   */
  public Optional<String> getValue() {
    return ofNullable(value);
  }

  /**
   * Builder to create instances of {@code ComponentModel}.
   */
  public static class Builder {

    private ApplicationElement model = new ApplicationElement();

    private Builder() {}

    /**
     * @param identifier identifier for the configuration element this object represents.
     * @return the builder.
     */
    public Builder withIdentifier(ApplicationElementIdentifier identifier) {
      this.model.identifier = identifier;
      return this;
    }

    /**
     * @param parameterName name of the configuration parameter.
     * @param value value contained by the configuration parameter.
     * @return the builder.
     */
    public Builder withParameter(String parameterName, String value) {
      this.model.parameters.put(parameterName, value);
      return this;
    }

    /**
     * Adds a new complex child object to this {@code ApplicationElement}.
     *
     * @param inner child {@code ApplicationElement} declared in the application.
     * @return the builder.
     */
    public Builder containing(ApplicationElement inner) {
      this.model.innerElements.add(inner);
      return this;
    }

    /**
     * Sets the inner content of the configuration element.
     *
     * @param content inner text content from the configuration.
     * @return the builder.
     */
    public Builder withValue(String content) {
      this.model.value = content;
      return this;
    }

    /**
     * @return a {@code ComponentModel} created based on the supplied parameters.
     */
    public ApplicationElement build() {
      checkState(model.identifier != null, "An identifier must be provided");
      return model;
    }

  }

  /**
   * @return a new {@link Builder}
   */
  public static Builder builder() {
    return new Builder();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    ApplicationElement that = (ApplicationElement) o;

    if (!identifier.equals(that.identifier)) {
      return false;
    }
    if (!parameters.equals(that.parameters)) {
      return false;
    }
    return innerElements.equals(that.innerElements);

  }

  @Override
  public int hashCode() {
    int result = 0;
    result = 31 * result + identifier.hashCode();
    result = 31 * result + parameters.hashCode();
    result = 31 * result + innerElements.hashCode();
    return result;
  }


}
