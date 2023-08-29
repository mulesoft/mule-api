/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
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
