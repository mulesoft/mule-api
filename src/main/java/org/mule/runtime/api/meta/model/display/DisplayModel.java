/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model.display;

import java.util.Objects;

/**
 * A model which provides directives about how the model should be shown in the user interface.
 * <p>
 * Instances are to be created through a {@link #builder() builder}
 *
 * @since 1.0
 */
public final class DisplayModel {

  /**
   * A Builder which allows creating instances of {@link DisplayModel}
   */
  public static final class DisplayModelBuilder {

    private final DisplayModel product = new DisplayModel();

    private DisplayModelBuilder() {}

    /**
     * Sets the name which should be use to show this model
     *
     * @param displayName a display name
     * @return {@code this} builder
     */
    public DisplayModelBuilder displayName(String displayName) {
      product.displayName = displayName;
      return this;
    }

    /**
     * A very brief overview about this model
     *
     * @param summary a summary
     * @return {@code this} builder
     */
    public DisplayModelBuilder summary(String summary) {
      product.summary = summary;
      return this;
    }

    /**
     * An example about the content of this model
     *
     * @param example an example
     * @return {@code this} builder
     */
    public DisplayModelBuilder example(String example) {
      product.example = example;
      return this;
    }

    /**
     * @return the built {@link DisplayModel}
     */
    public DisplayModel build() {
      return product;
    }
  }

  /**
   * @return a new {@link DisplayModelBuilder}
   */
  public static DisplayModelBuilder builder() {
    return new DisplayModelBuilder();
  }

  private String displayName;
  private String summary;
  private String example;

  private DisplayModel() {}

  /**
   * @return The name which should be use to show this model
   */
  public String getDisplayName() {
    return displayName;
  }

  /**
   * @return A very brief overview about this model
   */
  public String getSummary() {
    return summary;
  }

  /**
   * @return An example of the content that should be used
   */
  public String getExample() {
    return example;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof DisplayModel) {
      DisplayModel other = ((DisplayModel) obj);
      return Objects.equals(other.getDisplayName(), this.getDisplayName()) &&
          Objects.equals(other.getSummary(), this.getSummary()) &&
          Objects.equals(other.getExample(), this.getExample());
    }

    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(displayName, summary, example);
  }
}
