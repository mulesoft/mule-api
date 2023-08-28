/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.metadata;

import org.mule.api.annotation.NoImplement;
import org.mule.metadata.api.model.MetadataType;

import java.util.Set;

/**
 * A key, that with the given ID, represents a {@link MetadataType}. This key can be contributed with a display name and user
 * defined properties.
 * <p>
 * Also this {@link MetadataKey} can be composed by other {@link MetadataKey MetadataKeys} to form a composed key.
 *
 * @since 1.0
 */
@NoImplement
public interface MetadataKey extends MetadataEnrichableModel {

  /**
   * @return identifier for the current key
   */
  String getId();

  /**
   * @return human readable name to use when displaying the key
   */
  String getDisplayName();

  /**
   * @return the child {@link MetadataKey MetadataKeys} that form a composed {@link MetadataKey}.
   */
  Set<MetadataKey> getChilds();

  /**
   * @return the name of the part which this {@link MetadataKey} is from
   */
  String getPartName();
}
