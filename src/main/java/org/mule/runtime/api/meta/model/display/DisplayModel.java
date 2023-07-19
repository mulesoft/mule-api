/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.meta.model.display;

import static java.util.Optional.ofNullable;

import java.util.Objects;
import java.util.Optional;

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
     * A {@link PathModel} with metadata for a parameter that points to file or directory.
     *
     * @param pathModel a {@link PathModel} with the metadata associated to the path param.
     * @return {@code this} builder
     */
    public DisplayModelBuilder path(PathModel pathModel) {
      product.pathModel = pathModel;
      return this;
    }

    /**
     * A {@link ClassValueModel} with metadata for a parameter which references a class
     *
     * @param classValueModel a {@link ClassValueModel} with the metadata associated to the class param
     * @return {@code this} builder
     */
    public DisplayModelBuilder classValue(ClassValueModel classValueModel) {
      product.classValueModel = classValueModel;
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
  private PathModel pathModel;
  private ClassValueModel classValueModel;

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

  /**
   * @return an {@link Optional} with a {@link PathModel} if the parameter is a Path to a file or directory,\ an
   *         {@link Optional#empty()} if its not.
   */
  public Optional<PathModel> getPathModel() {
    return ofNullable(pathModel);
  }

  public Optional<ClassValueModel> getClassValueModel() {
    return ofNullable(classValueModel);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof DisplayModel) {
      DisplayModel other = ((DisplayModel) obj);
      return Objects.equals(other.getDisplayName(), this.getDisplayName()) &&
          Objects.equals(other.getSummary(), this.getSummary()) &&
          Objects.equals(other.getExample(), this.getExample()) &&
          Objects.equals(other.getPathModel(), this.getPathModel()) &&
          Objects.equals(other.getClassValueModel(), this.getClassValueModel());
    }

    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(displayName, summary, example, classValueModel);
  }
}
