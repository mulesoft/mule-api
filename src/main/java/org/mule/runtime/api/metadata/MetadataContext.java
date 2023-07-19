/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.metadata;

import org.mule.api.annotation.NoImplement;
import org.mule.metadata.api.ClassTypeLoader;
import org.mule.metadata.api.builder.BaseTypeBuilder;
import org.mule.runtime.api.connection.ConnectionException;
import org.mule.runtime.api.lifecycle.Disposable;

import java.util.Optional;

/**
 * Metadata resolving context, provides access to the Config and Connection used during metadata fetch invocation.
 *
 * @since 1.0
 */
@NoImplement
public interface MetadataContext extends Disposable {

  /**
   * @param <C> Configuration type
   * @return Optional instance of the configuration related to the component
   * @deprecated metadata resolution should not require the configuration.
   */
  @Deprecated
  <C> Optional<C> getConfig();

  /**
   * Retrieves the connection for the related a component and configuration
   *
   * @param <C> Connection type
   * @return Optional connection instance of {@param <C>} type for the component.
   * @throws ConnectionException when not valid connection is found for the related component and configuration
   */
  <C> Optional<C> getConnection() throws ConnectionException;

  /**
   * @return the {@link ClassTypeLoader} for the current {@link MetadataContext}.
   */
  ClassTypeLoader getTypeLoader();

  /**
   * @return the {@link BaseTypeBuilder} for the current {@link MetadataContext}.
   */
  BaseTypeBuilder getTypeBuilder();

  /**
   * @return the {@link MetadataCache} associated with the {@link MetadataContext}.
   */
  MetadataCache getCache();

}
