/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.util.collection;

import org.mule.runtime.internal.util.collection.ImmutableListCollector;
import org.mule.runtime.internal.util.collection.ImmutableMapCollector;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collector;

/**
 * Provides different implementations of {@link Collector}
 *
 * @since 1.0
 */
public class Collectors {

  private Collectors() {}

  /**
   * Returns a {@code Collector} that accumulates the input elements into an immutable {@code List}.
   *
   * @param <T> the type of the input elements
   * @return a {@code Collector} which collects all the input elements into an immutable {@code List}, in encounter order
   */
  public static <T> Collector<T, ?, List<T>> toImmutableList() {
    return new ImmutableListCollector<>();
  }

  /**
   * Returns a {@code Collector} that accumulates elements into an immutable {@code Map} whose keys and values are the result of
   * applying the provided mapping functions to the input elements.
   *
   * @param <T>         the type of the input elements
   * @param <K>         the output type of the key mapping function
   * @param <U>         the output type of the value mapping function
   * @param keyMapper   a mapping function to produce keys
   * @param valueMapper a mapping function to produce values
   * @return a {@code Collector} which collects elements into a {@code Map} whose keys and values are the result of applying
   *         mapping functions to the input elements
   */
  public static <T, K, V> Collector<T, ?, Map<K, V>> toImmutableMap(Function<T, K> keyMapper, Function<T, V> valueMapper) {
    return new ImmutableMapCollector<>(keyMapper, valueMapper);
  }
}
