/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.metadata;

import org.mule.runtime.api.connection.ConnectionException;

import java.io.Serializable;
import java.util.Map;
import java.util.Optional;

/**
 * Adapter to go from {@link MetadataStorage} to {@link MetadataCache} to use in the APIs that are still depending on
 * {@link MetadataCache}.
 *
 * @since 1.5.
 */
public class MetadataStorageToCacheAdapter implements MetadataCache {

  private MetadataStorage delegate;

  public MetadataStorageToCacheAdapter(MetadataStorage storage) {
    this.delegate = storage;
  }

  @Override
  public void put(Serializable key, Serializable value) {
    this.delegate.put(key, value);
  }

  @Override
  public void putAll(Map<? extends Serializable, ? extends Serializable> values) {
    this.putAll(values);
  }

  @Override
  public <T extends Serializable> Optional<T> get(Serializable key) {
    return this.delegate.get(key);
  }

  @Override
  public <T extends Serializable> T computeIfAbsent(Serializable key, MetadataCacheValueResolver mappingFunction)
      throws MetadataResolvingException, ConnectionException {
    return this.delegate.computeIfAbsent(key, mappingFunction::compute);
  }
}
