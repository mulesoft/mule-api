/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.values;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toSet;
import static org.mule.metadata.internal.utils.StringUtils.isNotEmpty;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

/**
 * An implementation of the builder design pattern to create a new {@link ValueBuilder} instance.
 *
 * @since 1.0
 */
public class ValueBuilder {

  private final String id;
  private final Set<ValueBuilder> childs = new LinkedHashSet<>();
  private String displayName;
  private String partName = "";

  private ValueBuilder(String id) {
    this.id = id;
  }

  private ValueBuilder(String id, String partName) {
    this.id = id;
    this.partName = partName;
  }

  /**
   * Creates and returns new instance of a {@link ValueBuilder}, to help building a new {@link Value}
   * represented by the given {@param id}
   *
   * @param id of the {@link Value} to be created
   * @return an initialized instance of {@link ValueBuilder}
   */
  public static ValueBuilder newValue(String id) {
    return new ValueBuilder(id);
  }

  /**
   * Creates and returns new instance of a {@link ValueBuilder}, to help building a new {@link Value}
   * represented by the given {@param id}
   *
   * @param id       of the {@link Value} to be created
   * @param partName the name of the part
   * @return an initialized instance of {@link ValueBuilder}
   */
  public static ValueBuilder newValue(String id, String partName) {
    return new ValueBuilder(id, partName);
  }

  /**
   * Utility to create single level {@link Value values} from an array of Strings.
   *
   * @param values array of {@link String strings} to be converted to {@link Value}
   * @return a {@link Set} of {@link Value} based on the given values
   */
  public static Set<Value> getValuesFor(String... values) {
    return getValuesFor(stream(values));
  }

  /**
   * Utility to create single level {@link Value values} from an {@link List} of Strings.
   *
   * @param values list of {@link String strings} to be converted to {@link Value}
   * @return a {@link Set} of {@link Value} based on the given values
   */
  public static Set<Value> getValuesFor(List<String> values) {
    return getValuesFor(values.stream());
  }

  /**
   * Utility to create single level {@link Value values} from an {@link Stream} of Strings.
   *
   * @param values stream of {@link String strings} to be converted to {@link Value}
   * @return a {@link Set} of {@link Value} based on the given values
   */
  public static Set<Value> getValuesFor(Stream<String> values) {
    return values
        .map(ValueBuilder::newValue)
        .map(ValueBuilder::build)
        .collect(toSet());
  }

  /**
   * Utility to create single level {@link Value values} from an {@link Map} of Strings to Strings.
   * The key of the Map will be considered as the {@link Value#getId() id} and the value as
   * {@link Value#getDisplayName() display name}
   *
   * @param values array of {@link String strings} to be converted to {@link Value}
   * @return a {@link Set} of {@link Value} based on the given values
   */
  public static Set<Value> getValuesFor(Map<String, String> values) {
    return values.entrySet()
        .stream()
        .map((entry) -> ValueBuilder.newValue(entry.getKey())
            .withDisplayName(entry.getValue())
            .build())
        .collect(toSet());
  }

  /**
   * Adds a display name to the {@link Value} that is being built
   *
   * @param displayName of the {@link Value} to be created
   * @return {@code this} builder with the configured display name
   */
  public ValueBuilder withDisplayName(String displayName) {
    this.displayName = displayName;
    return this;
  }

  /**
   * Adds a new {@link ValueBuilder} child to the {@link Value} that is being built.
   *
   * @param valueBuilder the {@link ValueBuilder} that is used to create the instance of the new child.
   * @return {@code this} builder with a new child.
   */
  public ValueBuilder withChild(ValueBuilder valueBuilder) {
    childs.add(valueBuilder);
    return this;
  }

  protected void setPartName(String partName) {
    this.partName = partName;
  }

  /**
   * Create an {@link Value} based in the information given to the Builder, included all their children.
   * @return the built {@link  Value}
   */
  public Value build() {
    String name = isNotEmpty(displayName) ? displayName : id;
    return new DefaultValue(id, name, childs.stream().map(ValueBuilder::build).collect(toSet()),
                            partName);
  }
}
