/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
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
 * every {@link InputTypeResolver}, {@link TypeKeysResolver} and {@link OutputTypeResolver}. It also allows to evict entries in
 * case recalculations are needed.
 *
 * @since 1.5
 */
@NoImplement
public interface MetadataStorage {

  /**
   * Associates the specified value with the specified key in the storage. if the storage previously contained a mapping for the
   * specified key, the old value gets replaced
   *
   * @param key   a key to associate the specified value
   * @param value the value to persist in the storage
   */
  void put(Serializable key, Serializable value);

  /**
   * Copies all of the entries from the specified map to the storage.
   *
   * @param values values to be stored in the storage
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
  <T extends Serializable> T computeIfAbsent(Serializable key, MetadataStorageValueResolver mappingFunction)
      throws MetadataResolvingException, ConnectionException;

  @FunctionalInterface
  interface MetadataStorageValueResolver {

    Serializable compute(Serializable key) throws MetadataResolvingException, ConnectionException;
  }

  /**
   * Evicts the key in the storage.
   *
   * @return true if the key was in the storage, false if not.
   */
  boolean evictEntry(Serializable key);
}
