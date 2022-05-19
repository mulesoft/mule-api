/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.component.location;

import static org.mule.runtime.api.util.Preconditions.checkState;
import static org.mule.runtime.internal.util.NameValidationUtil.verifyStringDoesNotContainsReservedCharacters;

import static java.lang.String.join;

import static org.apache.commons.lang3.StringUtils.isNumeric;

import org.mule.api.annotation.NoImplement;

import java.util.LinkedList;
import java.util.List;

/**
 * A location allows to define the position of a certain component in the configuration.
 * <p/>
 * Only components contained within global components with name can be referenced using a {@link Location} instance.
 * <p/>
 * The string representation of the implementation of this interface must be the serialized form of the location which consist of
 * the global element name and the parts separated by an slash character.
 *
 * @since 1.0
 */
@NoImplement
public interface Location {

  String SOURCE = "source";
  String CONNECTION = "connection";
  String PROCESSORS = "processors";
  String ERROR_HANDLER = "errorHandler";

  /**
   * @return the name of the global element that contains the component referenced by {@code this} {@link Location}.
   */
  String getGlobalName();

  /**
   * @return the parts within the global element that define the location of the component referenced by {@code this}
   *         {@link Location}.
   */
  List<String> getParts();

  /**
   * @return a new builder instance.
   */
  static Builder builder() {
    return new LocationBuilder();
  }

  /**
   * Creates a new {@link Builder} with the provided location represented as string as base.
   *
   * @param location a location to use to pre-configured the builder
   * @return a new builder instance with the provided location as base.
   */
  static Builder builderFromStringRepresentation(String location) {
    String[] parts = location.split("/");
    Builder builder = Location.builder();
    builder = builder.globalName(parts[0]);
    for (int i = 1; i < parts.length; i++) {
      builder = builder.addPart(parts[i]);
    }
    return builder;
  }

  /**
   * A builder to create a {@link Location} object.
   * <p>
   * All {@link Location} instances must be created using this builder. The builder implementation may not be thread safe but it
   * is immutable so each method call in the builder returns a new instance so it can be reused.
   *
   * @since 1.0
   */
  interface Builder {

    /**
     * Sets the name of the global component. This method must only be called once.
     *
     * @param globalName the name of the global component
     * @return a new builder with the provided configuration.
     */
    Builder globalName(String globalName);

    /**
     * Adds a new part at the end of the location.
     *
     * @param part the name of the part
     * @return a new builder with the provided configuration.
     */
    Builder addPart(String part);

    /**
     * Adds a new {@link Location#CONNECTION} part at the end of the location.
     * <p>
     * Connection elements within a configuration component must be addressed using a {@link Location#CONNECTION} part.
     *
     * @return a new builder with the provided configuration.
     */
    Builder addConnectionPart();

    /**
     * Adds a new {@link Location#SOURCE} part at the end of the location.
     * <p>
     * Message sources within other component must be addressed using a {@link Location#SOURCE} part.
     *
     * @return a new builder with the provided configuration.
     */
    Builder addSourcePart();

    /**
     * Adds a new {@link Location#PROCESSORS} part at the end of the location.
     * <p>
     * Components that allow nested processors must have {@link Location#PROCESSORS} as part before the nested processors indexes.
     *
     * @return a new builder with the provided configuration.
     */
    Builder addProcessorsPart();

    /**
     * Adds a new {@link Location#ERROR_HANDLER} part at the end of the location.
     * <p>
     * Components that allow nested {@code on-error} components must have {@link Location#ERROR_HANDLER} as part before the
     * {@code on-error} indexes.
     *
     * @return a new builder with the provided configuration.
     */
    Builder addErrorHandlerPart();

    /**
     * Adds a new index part. The index part is used to reference a component within a collection.
     * <p>
     * There cannot be two index parts consecutively.
     *
     * @param index the index of the component.
     * @return a new builder with the provided configuration.
     */
    Builder addIndexPart(int index);

    /**
     * Adds the parts of this location.
     *
     * @param parts the parts of the location
     * @return a new builder with the provided configuration.
     */
    Builder parts(List<String> parts);

    /**
     * @return a location built with the provided configuration.
     */
    Location build();

  }


  class LocationImpl implements Location {

    protected static final String PARTS_SEPARATOR = "/";
    private LinkedList<String> parts = new LinkedList<>();

    private String asString;

    @Override
    public String getGlobalName() {
      return parts.get(0);
    }

    @Override
    public List<String> getParts() {
      return parts.subList(1, parts.size());
    }

    @Override
    public String toString() {
      if (asString == null) {
        asString = join(PARTS_SEPARATOR, parts);
      }
      return asString;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }

      LocationImpl location = (LocationImpl) o;

      return parts.equals(location.parts);

    }

    @Override
    public int hashCode() {
      return parts.hashCode();
    }
  }


  class LocationBuilder implements Builder {

    private static final String PLACEHOLDER_PREFIX = "${";
    private static final String PLACEHOLDER_SUFFIX = "}";
    private LocationImpl location = new LocationImpl();
    private boolean globalNameAlreadySet = false;

    @Override
    public Builder globalName(String globalName) {
      globalNameAlreadySet = true;
      if (!isPlaceholder(globalName)) {
        verifyStringDoesNotContainsReservedCharacters(globalName);
      }
      LocationBuilder locationBuilder = builderCopy();
      locationBuilder.location.parts.add(0, globalName);
      return locationBuilder;
    }

    private static boolean isPlaceholder(String string) {
      return string.startsWith(PLACEHOLDER_PREFIX) && string.endsWith(PLACEHOLDER_SUFFIX);
    }

    @Override
    public Builder addPart(String part) {
      verifyStringDoesNotContainsReservedCharacters(part);
      verifyIndexPartAfterProcessor(part);
      LocationBuilder locationBuilder = builderCopy();
      locationBuilder.location.parts.addLast(part);
      return locationBuilder;
    }

    @Override
    public Builder addConnectionPart() {
      checkIsNotFirstPart(CONNECTION);
      LocationBuilder locationBuilder = builderCopy();
      locationBuilder.location.parts.add(CONNECTION);
      return locationBuilder;
    }

    @Override
    public Builder addSourcePart() {
      checkIsNotFirstPart(SOURCE);
      LocationBuilder locationBuilder = builderCopy();
      locationBuilder.location.parts.add(SOURCE);
      return locationBuilder;
    }

    @Override
    public Builder addProcessorsPart() {
      checkIsNotFirstPart(PROCESSORS);
      LocationBuilder locationBuilder = builderCopy();
      locationBuilder.location.parts.add(PROCESSORS);
      return locationBuilder;
    }

    @Override
    public Builder addErrorHandlerPart() {
      checkIsNotFirstPart(ERROR_HANDLER);
      LocationBuilder locationBuilder = builderCopy();
      locationBuilder.location.parts.add(ERROR_HANDLER);
      return locationBuilder;
    }

    private void checkIsNotFirstPart(String partName) {
      checkState(!location.parts.isEmpty(), "[" + partName + "] cannot be the first part");
    }

    @Override
    public Builder addIndexPart(int index) {
      checkState(!location.parts.isEmpty(), "An index cannot be the first part");
      LocationBuilder locationBuilder = builderCopy();
      locationBuilder.location.parts.addLast(String.valueOf(index));
      return locationBuilder;
    }

    @Override
    public Builder parts(List<String> parts) {
      location.parts = new LinkedList<>(parts);
      return this;
    }

    private void verifyIndexPartAfterProcessor(String part) {
      if (location.parts.getLast().equals(PROCESSORS)) {
        checkState(isNumeric(part), "Only an index part can follow a processors part");
      }
    }

    private LocationBuilder builderCopy() {
      LocationBuilder locationBuilder = new LocationBuilder();
      locationBuilder.globalNameAlreadySet = this.globalNameAlreadySet;
      locationBuilder.location.parts.addAll(this.location.parts);
      return locationBuilder;
    }

    @Override
    public Location build() {
      checkState(globalNameAlreadySet, "global component name must be set");
      return location;
    }
  }


}
