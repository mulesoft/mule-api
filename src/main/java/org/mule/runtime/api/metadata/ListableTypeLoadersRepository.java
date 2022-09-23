/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.metadata;

import org.mule.metadata.api.ListableTypeLoader;
import org.mule.runtime.api.exception.MuleException;

import java.util.Map;

/**
 * Interface to retrieve all the available {@link ListableTypeLoader} instances, or by a particular prefix.
 *
 * @since 1.5.0
 */
public interface ListableTypeLoadersRepository {

  /**
   * Retrieves all the available {@link ListableTypeLoader} for the different extensions, classified by the extension prefix.
   * If you need a particular {@link ListableTypeLoader}, you should use {@link #getTypeLoaderByPrefix(String)} instead,
   * so you take advantage of a possible optimization that only builds the {@link ListableTypeLoader} lazily, when it's
   * requested.
   *
   * @return all the available {@link ListableTypeLoader} for the different extensions, classified by the extension prefix.
   */
  Map<String, ListableTypeLoader> getAllTypeLoaders();

  /**
   * Retrieves a particular {@link ListableTypeLoader} for the passed {@code prefix}.
   *
   * @param prefix the extension's prefix.
   * @return the corresponding instance of {@link ListableTypeLoader}.
   * @throws MuleException if a type loader couldn't be found for the passed prefix.
   */
  ListableTypeLoader getTypeLoaderByPrefix(String prefix) throws MuleException;
}
