/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
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
