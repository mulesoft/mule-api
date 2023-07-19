/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.el.validation;

/**
 * Represents the a location within a script.
 *
 * @since 1.0
 */
public final class Location {

  private Position startPosition;
  private Position endPosition;

  public Location(Position startPosition, Position endPosition) {
    this.startPosition = startPosition;
    this.endPosition = endPosition;
  }

  public Position getStartPosition() {
    return startPosition;
  }

  public Position getEndPosition() {
    return endPosition;
  }
}
