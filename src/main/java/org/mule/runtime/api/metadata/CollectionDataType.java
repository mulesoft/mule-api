/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.metadata;

import org.mule.api.annotation.NoImplement;

/**
 * A data type that represents a generified collection.
 * <p>
 * When checked for compatibility both the collection type and the generic item type will be compared.
 *
 * @since 1.0
 */
@NoImplement
public interface CollectionDataType extends DataType {

  /**
   * Obtains the {@link DataType} of the items in the collection.
   *
   * @return the {@link DataType} of collection items.
   */
  DataType getItemDataType();
}
