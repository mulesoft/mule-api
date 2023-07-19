/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.el.validation;

/**
 * Represents the position within a script.
 *
 * @since 1.0
 */
public final class Position {

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
