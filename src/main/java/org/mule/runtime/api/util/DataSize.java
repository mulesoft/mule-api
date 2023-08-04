/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.util;

import static java.util.Objects.hash;
import static org.mule.runtime.api.util.Preconditions.checkArgument;

/**
 * Represents an amount of information by combining a scalar size with a {@link DataUnit}
 *
 * @since 1.0
 */
public final class DataSize {

  private final int size;
  private final DataUnit unit;

  /**
   * Creates a new instance
   *
   * @param size an scalar amount of data
   * @param unit the unit which qualifies the {@code size}
   */
  public DataSize(int size, DataUnit unit) {
    checkArgument(unit != null, "unit cannot be null");
    this.size = size;
    this.unit = unit;
  }

  /**
   * @return the scalar value in bytes
   */
  public int toBytes() {
    return unit.toBytes(size);
  }

  /**
   * @return the scalar value in KB
   */
  public int toKB() {
    return unit.toKB(size);
  }

  /**
   * @return the scalar value in MB
   */
  public int toMB() {
    return unit.toMB(size);
  }

  /**
   * @return the scalar value in GB
   */
  public int toGB() {
    return unit.toGB(size);
  }

  /**
   * @return the scalar value as was provided in the constructor
   */
  public int getSize() {
    return size;
  }

  /**
   * @return the {@link DataUnit} provided in the constructor
   */
  public DataUnit getUnit() {
    return unit;
  }

  @Override
  public boolean equals(Object o) {
    if (o instanceof DataSize) {
      DataSize dataSize = (DataSize) o;
      return size == dataSize.size && unit == dataSize.unit;
    }

    return false;
  }

  @Override
  public int hashCode() {
    return hash(size, unit);
  }
}
