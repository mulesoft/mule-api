/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.metadata;

import org.mule.metadata.api.model.MetadataType;

import java.util.List;
import java.util.Map;

/**
 * This interface allows to fetch {@link MetadataType} information associated with the extension prefix
 *
 * @since 1.5.0
 */
public interface ExtendedTypeCatalog {

  /***
   * Gets all {@link MetadataType} associated with an extension namespace prefix.
   *
   * @return All {@link MetadataType} associated with an extension namespace prefix
   */
  Map<String, List<MetadataType>> getAllTypes();

  /***
   * Gets a specific {@link MetadataType} associated with an extension namespace prefix and type id or type alias.
   *
   * @param extensionPrefix The namespace prefix of the extension to obtain the type from
   * @param typeIdOrAlias   The type id or alias of the {@link MetadataType}
   * @return {@link MetadataType} associated with this particular extension prefix and type id or type alias.
   */
  MetadataType getType(String extensionPrefix, String typeIdOrAlias);
}
