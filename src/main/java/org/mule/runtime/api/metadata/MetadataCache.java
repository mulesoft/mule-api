/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.metadata;

import org.mule.api.annotation.NoImplement;
import org.mule.runtime.api.connection.ConnectionException;
import org.mule.runtime.api.metadata.resolving.InputTypeResolver;
import org.mule.runtime.api.metadata.resolving.OutputTypeResolver;
import org.mule.runtime.api.metadata.resolving.TypeKeysResolver;

import java.io.Serializable;
import java.util.Map;
import java.util.Optional;

/**
 * This component provides the capability to store data so future requests for that data can be served faster and accessed from
 * every {@link InputTypeResolver}, {@link TypeKeysResolver} and {@link OutputTypeResolver}
 *
 * @deprecated since 1.5.0. Use {@link MetadataStorage} instead.
 * @since 1.0
 */
@NoImplement
@Deprecated
public interface MetadataCache extends Serializable {

  /**
   * Associates the specified value with the specified key in the cache. if the cache previously contained a mapping for the
   * specified key, the old value gets replaced
   *
   * @param key   a key to associate the specified value
   * @param value the value to persist in the cache
   */
  void put(Serializable key, Serializable value);

  /**
   * Copies all of the entries from the specified map to the cache.
   *
   * @param values values to be stored in the cache
   */
  void putAll(Map<? extends Serializable, ? extends Serializable> values);

  /**
   * @param key the key whose associated value is to be returned
   * @return the value to which the specified key is mapped, or {@code Option.empty()} if this map contains no value for the
   *         specified key.
   */
  <T extends Serializable> Optional<T> get(Serializable key);

  /**
   * If the specified key is not already associated with a value, attempts to compute its value using the given mapping function
   * and enters it into this map unless {@code null}.
   *
   * @param key the key whose associated value is to be returned
   * @return the current (existing or computed) value associated with the specified key, or null if the computed value is null
   */
  <T extends Serializable> T computeIfAbsent(Serializable key, MetadataCacheValueResolver mappingFunction)
      throws MetadataResolvingException, ConnectionException;

  @FunctionalInterface
  interface MetadataCacheValueResolver {

    Serializable compute(Serializable key) throws MetadataResolvingException, ConnectionException;
  }
}
