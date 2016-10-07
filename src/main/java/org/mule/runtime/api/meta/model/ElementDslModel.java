/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model;

/**
 * Contains directives regarding semantics for the DSL which supports
 * one given owning element.
 * <p>
 * Instances of this interface are to be created by the builder
 * obtained through {@link #builder()}
 *
 * @since 1.0
 */
public final class ElementDslModel {

  /**
   * A builder which allows creating instance of {@link ElementDslModel}
   *
   * @since 1.0
   */
  public static final class ElementDslModelBuilder {

    private ElementDslModel product = new ElementDslModel();

    private ElementDslModelBuilder() {}

    /**
     * Specifies whether the associated element should support inline definition as child element
     *
     * @param allowsInlineDefinition the value
     * @return {@code this} builder
     */
    public ElementDslModelBuilder allowsInlineDefinition(boolean allowsInlineDefinition) {
      product.allowInlineDefinition = allowsInlineDefinition;
      return this;
    }

    /**
     * Specifies whether the associated element should support being defined as a top level definition
     *
     * @param allowTopLevelDefinition the value
     * @return {@code this} builder
     */
    public ElementDslModelBuilder allowTopLevelDefinition(boolean allowTopLevelDefinition) {
      product.allowTopLevelDefinition = allowTopLevelDefinition;
      return this;
    }

    /**
     * Specifies whether the associated element should support registry references
     *
     * @param allowsReferences the value
     * @return {@code this} builder
     */
    public ElementDslModelBuilder allowsReferences(boolean allowsReferences) {
      product.allowReferences = allowsReferences;
      return this;
    }

    /**
     * @return the generated model
     */
    public ElementDslModel build() {
      return product;
    }
  }

  /**
   * @return a new instance set with default values
   */
  public static ElementDslModel getDefaultInstance() {
    return builder().build();
  }

  /**
   * @return a new {@link ElementDslModelBuilder}
   */
  public static ElementDslModelBuilder builder() {
    return new ElementDslModelBuilder();
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
