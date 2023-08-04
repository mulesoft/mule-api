/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.metadata;

import org.mule.api.annotation.NoImplement;

/**
 * A data type that represents a generified map.
 * <p>
 * When checked for compatibility the map type, the generic key type and the generic value type will be compared.
 *
 * @since 1.0
 */
@NoImplement
public interface MapDataType extends DataType {

  /**
   * Obtains the {@link DataType} of the keys in the map.
   *
   * @return the {@link DataType} of map keys.
   */
  DataType getKeyDataType();

  /**
   * Obtains the {@link DataType} of the values in the map.
   *
   * @return the {@link DataType} of map values.
   */
  DataType getValueDataType();

}
