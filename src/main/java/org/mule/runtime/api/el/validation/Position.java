/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.el.validation;

/**
 * Represents the position within a script.
 *
 * @since 1.0
 */
public class Position {

  private int line;
  private int column;
  private int offset;

  public Position(int line, int column, int offset) {
    this.line = line;
    this.column = column;
    this.offset = offset;
  }

  public int getLine() {
    return line;
  }

  public int getColumn() {
    return column;
  }

  public int getOffset() {
    return offset;
  }
}
