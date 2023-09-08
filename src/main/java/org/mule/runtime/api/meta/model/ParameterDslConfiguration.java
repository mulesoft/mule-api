/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model;

/**
 * Contains directives regarding semantics for the DSL which supports one given owning element.
 * <p>
 * Instances of this interface are to be created by the builder obtained through {@link #builder()}
 *
 * @since 1.0
 */
public final class ParameterDslConfiguration {

  /**
   * A builder which allows creating instance of {@link ParameterDslConfiguration}
   *
   * @since 1.0
   */
  public static final class Builder {

    private ParameterDslConfiguration product = new ParameterDslConfiguration();

    private Builder() {}

    /**
     * Specifies whether the associated element should support inline definition as child element
     *
     * @param allowsInlineDefinition the value
     * @return {@code this} builder
     */
    public Builder allowsInlineDefinition(boolean allowsInlineDefinition) {
      product.allowInlineDefinition = allowsInlineDefinition;
      return this;
    }

    /**
     * Specifies whether the associated element should support being defined as a top level definition
     *
     * @param allowTopLevelDefinition the value
     * @return {@code this} builder
     */
    public Builder allowTopLevelDefinition(boolean allowTopLevelDefinition) {
      product.allowTopLevelDefinition = allowTopLevelDefinition;
      return this;
    }

    /**
     * Specifies whether the associated element should support registry references
     *
     * @param allowsReferences the value
     * @return {@code this} builder
     */
    public Builder allowsReferences(boolean allowsReferences) {
      product.allowReferences = allowsReferences;
      return this;
    }

    /**
     * @return the generated model
     */
    public ParameterDslConfiguration build() {
      return product;
    }
  }

  /**
   * @return a new instance set with default values
   */
  public static ParameterDslConfiguration getDefaultInstance() {
    return builder().build();
  }

  /**
   * @return a new {@link Builder}
   */
  public static Builder builder() {
    return new Builder();
  }

  /**
   * @param prototype a prototype {@link ParameterDslConfiguration}
   * @return a new builder initialised to match the state of the given {@code prototype}
   */
  public static Builder builder(ParameterDslConfiguration prototype) {
    return builder()
        .allowsInlineDefinition(prototype.allowsInlineDefinition())
        .allowsReferences(prototype.allowsReferences())
        .allowTopLevelDefinition(prototype.allowTopLevelDefinition());
  }

  private boolean allowInlineDefinition = true;
  private boolean allowTopLevelDefinition = false;
  private boolean allowReferences = true;

  /**
   * @return whether the associated element should support inline definition as child element
   */
  public boolean allowsInlineDefinition() {
    return allowInlineDefinition;
  }

  /**
   * @return whether the associated element should support being defined as a top level definition
   */
  public boolean allowTopLevelDefinition() {
    return allowTopLevelDefinition;
  }

  /**
   * @return whether the associated element should support registry references
   */
  public boolean allowsReferences() {
    return allowReferences;
  }
}
