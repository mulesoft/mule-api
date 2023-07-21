/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.util;

import java.util.ConcurrentModificationException;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Utilities for functional programming
 *
 * @since 1.2
 */
public class FunctionalUtils {

  /**
   * Use this method instead if {@link Map#computeIfAbsent(Object, Function)} when the mapping functions are recursive and/or will
   * end up accessing the same {@code map} again. This is necessary because starting with JDK9
   * {@link Map#computeIfAbsent(Object, Function)} throws {@link ConcurrentModificationException} if used in concurrent,
   * recursive, or nested fashion.
   */
  public static <K, V> V computeIfAbsent(Map<K, V> map, K key, Function<K, V> mappingFunction) {
    V value = map.get(key);
    if (value == null) {
      value = mappingFunction.apply(key);
      map.put(key, value);
    }
    return value;
  }

  /**
   * If the given {@code optional} is not present, then it returns the optional provided by the {@code orElse} supplier
   *
   * @param optional an {@link Optional}
   * @param orElse   a {@link Supplier} that provides the return value in case the {@code optional} is empty
   * @param <T>      the generic type of the optional item
   * @return an {@link Optional}
   */
  public static <T> Optional<T> or(Optional<T> optional, Supplier<Optional<T>> orElse) {
    return optional.isPresent() ? optional : orElse.get();
  }

  /**
   * If the {@code optional} is present, it executes the {@code ifPresent} consumer. Otherwise, it executes the {@code orElse}
   * runnable
   *
   * @param optional  an {@link Optional} value
   * @param ifPresent the consumer to execute if the value is present
   * @param orElse    a fallback runnable in case the optional is empty.
   * @param <T>       the generic type of the optional's value.
   */
  public static <T> void ifPresent(Optional<T> optional, Consumer<? super T> ifPresent, Runnable orElse) {
    if (optional.isPresent()) {
      ifPresent.accept(optional.get());
    } else {
      orElse.run();
    }
  }
}
