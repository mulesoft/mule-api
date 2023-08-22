/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.util;

/**
 * Units to measure amounts of bytes and convenience transformation methods
 *
 * @since 4.0
 */
public enum DataUnit {

  BYTE {

    @Override
    public int toBytes(int value) {
      return value;
    }

    @Override
    public int toKB(int value) {
      return value / ONE_KB;
    }

    @Override
    public int toMB(int value) {
      return value / ONE_MB;
    }

    @Override
    public int toGB(int value) {
      return value / ONE_GB;
    }
  },

  KB {

    @Override
    public int toBytes(int value) {
      return value * ONE_KB;
    }

    @Override
    public int toKB(int value) {
      return value;
    }

    @Override
    public int toMB(int value) {
      return value / ONE_KB;
    }

    @Override
    public int toGB(int value) {
      return value / ONE_MB;
    }
  },

  MB {

    @Override
    public int toBytes(int value) {
      return value * ONE_MB;
    }

    @Override
    public int toKB(int value) {
      return value * ONE_KB;
    }

    @Override
    public int toMB(int value) {
      return value;
    }

    @Override
    public int toGB(int value) {
      return value / ONE_KB;
    }
  },

  GB {

    @Override
    public int toBytes(int value) {
      return value * ONE_GB;
    }

    @Override
    public int toKB(int value) {
      return value * ONE_MB;
    }

    @Override
    public int toMB(int value) {
      return value * ONE_KB;
    }

    @Override
    public int toGB(int value) {
      return value;
    }
  };

  /**
   * @param value an scalar amount of information
   * @return the given {@code value} in bytes
   */
  public int toBytes(int value) {
    throw new AbstractMethodError();
  }

  /**
   * @param value an scalar amount of information
   * @return the given {@code value} in KB
   */
  public int toKB(int value) {
    throw new AbstractMethodError();
  }

  /**
   * @param value an scalar amount of information
   * @return the given {@code value} in MB
   */
  public int toMB(int value) {
    throw new AbstractMethodError();
  }

  /**
   * @param value an scalar amount of information
   * @return the given {@code value} in GB
   */
  public int toGB(int value) {
    throw new AbstractMethodError();
  }

  private static final int ONE_KB = 1024;
  private static final int ONE_MB = ONE_KB * 1024;
  private static final int ONE_GB = ONE_MB * 1024;

}
