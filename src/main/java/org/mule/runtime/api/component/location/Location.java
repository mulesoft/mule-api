/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.component.location;

import static java.lang.Integer.parseInt;
import static java.lang.String.join;
import static org.mule.runtime.api.component.location.Location.LocationImpl.PARTS_SEPARATOR;
import static org.mule.runtime.api.util.Preconditions.checkArgument;
import static org.mule.runtime.api.util.Preconditions.checkState;

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
public interface Location {

  /**
   * @return the global component name that contains the referenced component.
   */
  String getGlobalComponentName();

  /**
   * @return the parts within the global component that define the location of the component.
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
   *
   * All {@link Location} instances must be created using this builder. The builder implementation may not be thread safe but it
   * is immutable so each method call in the builder returns a new instance so it can be reused.
   * 
   * @since 4.0
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
     * Adds a new "source" part at the end of the location.
     * 
     * Message sources within other component must be addressed using a "source" part.
     *
     * @return a new builder with the provided configuration.
     */
    Builder addSourcePart();

    /**
     * Adds a new "processors" part at the end of the location.
     * 
     * Components that allow nested processors must have the "processors" as part before the nested processors indexes.
     * 
     * @return a new builder with the provided configuration.
     */
    Builder addProcessorsPart();

    /**
     * Adds a new index part. The index part is used to reference a component within a collection.
     * 
     * There cannot be two index parts consecutively.
     * 
     * @param index the index of the component.
     * @return a new builder with the provided configuration.
     */
    Builder addIndexPart(int index);

    /**
     * @return a location built with the provided configuration.
     */
    Location build();

  }

  class LocationImpl implements Location {

    protected static final String PARTS_SEPARATOR = "/";
    private LinkedList<String> parts = new LinkedList<>();

    @Override
    public String getGlobalComponentName() {
      return parts.get(0);
    }

    @Override
    public List<String> getParts() {
      return parts.subList(1, parts.size() - 1);
    }

    @Override
    public String toString() {
      return join(PARTS_SEPARATOR, parts);
    }
  }

  class LocationBuilder implements Builder {

    private LocationImpl location = new LocationImpl();
    private boolean globalNameAlreadySet = false;

    @Override
    public Builder globalName(String globalName) {
      globalNameAlreadySet = true;
      verifyPartDoesNotContainsSlash(globalName);
      LocationBuilder locationBuilder = builderCopy();
      locationBuilder.location.parts.add(0, globalName);
      return locationBuilder;
    }

    @Override
    public Builder addPart(String part) {
      verifyPartDoesNotContainsSlash(part);
      LocationBuilder locationBuilder = builderCopy();
      locationBuilder.location.parts.addLast(part);
      return locationBuilder;
    }

    @Override
    public Builder addSourcePart() {
      LocationBuilder locationBuilder = builderCopy();
      locationBuilder.location.parts.add("source");
      return locationBuilder;
    }

    @Override
    public Builder addProcessorsPart() {
      LocationBuilder locationBuilder = builderCopy();
      locationBuilder.location.parts.add("processors");
      return locationBuilder;
    }

    @Override
    public Builder addIndexPart(int index) {
      verifyNextPartCanBeAnIndex();
      LocationBuilder locationBuilder = builderCopy();
      locationBuilder.location.parts.addLast(String.valueOf(index));
      return locationBuilder;
    }

    private void verifyNextPartCanBeAnIndex() {
      checkState(!location.parts.isEmpty(), "An index cannot be the first part");
      checkState(location.parts.size() > 1, "An index cannot follow the global element name");
      if (location.parts.size() == 1) {
        // first part is flow name, skip it.
        return;
      }
      try {
        parseInt(location.parts.getLast());
        checkState(false, "A location cannot have two consecutive index");
      } catch (NumberFormatException e) {
        // all good, not an index.
      }
    }

    private LocationBuilder builderCopy() {
      LocationBuilder locationBuilder = new LocationBuilder();
      locationBuilder.globalNameAlreadySet = this.globalNameAlreadySet;
      locationBuilder.location.parts.addAll(this.location.parts);
      return locationBuilder;
    }

    private void verifyPartDoesNotContainsSlash(String globalName) {
      checkArgument(!globalName.contains(PARTS_SEPARATOR), "Slash cannot be part of the global name or part");
    }

    @Override
    public Location build() {
      checkState(globalNameAlreadySet, "global component name must be set");
      return location;
    }
  }


}
