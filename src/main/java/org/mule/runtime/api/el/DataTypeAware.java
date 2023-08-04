/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.el;

import org.mule.runtime.api.metadata.DataType;

import java.util.Collection;
import java.util.Map;

/**
 * Provides metadata about types of items in {@link Collection Collections} or {@link Map Maps}.
 * <p>
 * Implementations of this class must implement either {@link Collection} or {@link Map}, and implement its {@link #getDataType()}
 * method accordingly to the types of the objects contained.
 *
 * @since 1.2
 */
public interface DataTypeAware {

  /**
   * @return the {@link DataType} that best represents this implementation's data.
   */
  DataType getDataType();
}
