/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model;

import static java.util.Optional.ofNullable;
import static org.mule.runtime.api.util.Preconditions.checkArgument;
import static org.mule.runtime.api.util.Preconditions.checkState;
import org.mule.runtime.api.meta.DescribedObject;
import org.mule.runtime.api.meta.ExternalLibraryType;
import org.mule.runtime.api.meta.NamedObject;

import java.util.Optional;

/**
 * Describes a library that the extension depends on but is not packaged with it.
 *
 * @since 1.0
 */
public final class ExternalLibraryModel implements NamedObject, DescribedObject {

  /**
   * A Builder for creating instances of {@link ExternalLibraryModel}.
   * <p>
   * Instances are to be created through the {@link ExternalLibraryModel#builder()} method. Instances are not reusable.
   *
   * @since 1.0
   */
  public static class ExternalLibraryModelBuilder {

    private final ExternalLibraryModel product = new ExternalLibraryModel();

    private ExternalLibraryModelBuilder() {}

    /**
     * Sets the name of the library. It is mandatory to supply a name.
     *
     * @param name the library name
     * @return {@code this} builder
     * @throws IllegalArgumentException if {@code name} is {@code null}
     */
    public ExternalLibraryModelBuilder withName(String name) {
      checkArgument(name != null && !name.trim().isEmpty(), "name cannot be blank");
      product.name = name;
      return this;
    }

    /**
     * Sets a description for the library
     *
     * @param description the library's description
     * @return {@code this} builder
     */
    public ExternalLibraryModelBuilder withDescription(String description) {
      product.description = description;
      return this;
    }

    /**
     * Sets a regexp which must match the name of the library's file.
     *
     * @param regexMatcher a regexp to match the library's filename
     * @return {@code this} builder
     */
    public ExternalLibraryModelBuilder withRegexpMatcher(String regexMatcher) {
      product.regexMatcher = regexMatcher;
      return this;
    }

    /**
     * If provided, the library should contain a class of the given name
     *
     * @param requiredClassName the name of the required class
     * @return {@code this} builder
     */
    public ExternalLibraryModelBuilder withRequiredClassName(String requiredClassName) {
      product.requiredClassName = requiredClassName;
      return this;
    }

    /**
     * If provided, set the library's type of dependency
     *
     * @param type the type of the library
     * @return {@code this} builder
     */
    public ExternalLibraryModelBuilder withType(ExternalLibraryType type) {
      product.type = type;
      return this;
    }

    /**
     * Suggests Maven coordinates where the required library can be found. This coordinates should follow the Maven convention:
     * {@code groupId:artifactId:packaging:classifier:version}.
     * <p>
     * Keep in mind that not all the values of the coordinates are required, for example:
     * {@code org.mule.modules:a-required-lib:1.0.0} are valid coordinates, which communicates the {@code groupId},
     * {@code artifactId} and {@code version} of the external library.
     * <p>
     * By default, the packaging is {@code jar}, so if is required to use a native library, like a .DLL, you will provide:
     * {@code org.mule.module:a-native-lib:dll:1.0.0} where {@code dll} is the packaging of the library.
     * <p>
     * More information in:
     * <a href="https://maven.apache.org/pom.html#Maven_Coordinates">https://maven.apache.org/pom.html#Maven_Coordinates</a>
     *
     * @param coordinates The library coordinates
     * @return {@code this} builder
     */
    public ExternalLibraryModelBuilder withCoordinates(String coordinates) {
      product.suggestedCoordinates = coordinates;
      return this;
    }

    /**
     * Indicates if the library is optional or not.
     *
     * @param optional boolean indicating the optionality of the library
     * @return {@code this} builder
     */
    public ExternalLibraryModelBuilder isOptional(boolean optional) {
      product.optional = optional;
      return this;
    }

    /**
     * @return a new {@link ExternalLibraryModel} instance
     * @throws IllegalStateException if {@link #withName(String)} was not provided
     */
    public ExternalLibraryModel build() {
      checkState(product.name != null, "name was not provided");
      return product;
    }
  }

  /**
   * @return a new {@link ExternalLibraryModelBuilder}
   */
  public static ExternalLibraryModelBuilder builder() {
    return new ExternalLibraryModelBuilder();
  }

  private String name;
  private String description;
  private String regexMatcher;
  private String requiredClassName;
  private ExternalLibraryType type;
  private String suggestedCoordinates;
  private boolean optional = false;

  private ExternalLibraryModel() {}

  /**
   * @return The library's name. Will never be {@code null}
   */
  @Override
  public String getName() {
    return name;
  }

  /**
   * @return The library's description
   */
  @Override
  public String getDescription() {
    return description;
  }

  /**
   * If present, returns a regexp which must match the name of the library's file.
   *
   * @return An {@link Optional} regexp to match the library's file name
   */
  public Optional<String> getRegexMatcher() {
    return ofNullable(regexMatcher);
  }

  /**
   * If present, the library should contain a class of the given name
   *
   * @return The name of the required class
   */
  public Optional<String> getRequiredClassName() {
    return ofNullable(requiredClassName);
  }

  /**
   * If provided, suggests Maven coordinates where the required library can be found. These coordinates should follow the Maven
   * convention: {@code groupId:artifactId:packaging:classifier:version}.
   * <p>
   * Keep in mind that not all the values of the coordinates are required, for example:
   * {@code org.mule.modules:a-required-lib:1.0.0} are valid coordinates, which communicates the {@code groupId},
   * {@code artifactId} and {@code version} of the external library.
   * <p>
   * By default, the packaging is {@code jar}, so if is required to use a native library, like a .DLL, you will provide:
   * {@code org.mule.module:a-native-lib:dll:1.0.0} where {@code dll} is the packaging of the library.
   * <p>
   * More information in:
   * <a href="https://maven.apache.org/pom.html#Maven_Coordinates">https://maven.apache.org/pom.html#Maven_Coordinates</a>
   *
   * @return The optional maven coordinates.
   */
  public Optional<String> getSuggestedCoordinates() {
    return ofNullable(suggestedCoordinates);
  }

  /**
   * @return A boolean indicating whether the library is required or not to the basic functioning of the connector.
   */
  public boolean isOptional() {
    return optional;
  }

  /**
   * @return The library's type
   */
  public ExternalLibraryType getType() {
    return type;
  }
}
