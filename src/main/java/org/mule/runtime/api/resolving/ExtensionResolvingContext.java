/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.resolving;

import org.mule.metadata.api.ClassTypeLoader;
import org.mule.metadata.api.builder.BaseTypeBuilder;
import org.mule.runtime.api.connection.ConnectionException;
import org.mule.runtime.api.lifecycle.Disposable;

import java.util.Optional;

public interface ExtensionResolvingContext extends Disposable {

  /**
   * @param <C> Configuration type
   * @return Optional instance of the configuration related to the component
   */
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
   * @return the {@link ClassTypeLoader} for the current {@link ExtensionResolvingContext}.
   */
  ClassTypeLoader getTypeLoader();

  /**
   * @return the {@link BaseTypeBuilder} for the current {@link ExtensionResolvingContext}.
   */
  BaseTypeBuilder getTypeBuilder();
}
