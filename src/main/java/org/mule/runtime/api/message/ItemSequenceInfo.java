/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.message;

import static java.util.OptionalInt.empty;

import java.io.Serializable;
import java.util.OptionalInt;

/**
 * Immutable container for an {@link org.mule.runtime.api.event.Event} sequence information.
 *
 * @since 1.1
 */
public final class ItemSequenceInfo implements Serializable {

  public static final String NOT_SET = "<not set>";
  private static final long serialVersionUID = -2294860079566380331L;

  private final int position;
  private final int sequenceSize;

  public static ItemSequenceInfo of(int position) {
    return new ItemSequenceInfo(position, empty());
  }

  public static ItemSequenceInfo of(int position, int sequenceSize) {
    return new ItemSequenceInfo(position, OptionalInt.of(sequenceSize));
  }

  /**
   * Builds a new {@link ItemSequenceInfo} with the given parameters.
   *
   * @param position     see {@link #getPosition()}.
   * @param sequenceSize see {@link #getSequenceSize()}.
   */
  private ItemSequenceInfo(int position, OptionalInt sequenceSize) {
    this.position = position;
    this.sequenceSize = sequenceSize.orElse(-1);
  }

  /**
   * Gets the position or ordering number for this event in the the sequence, starting from 0 up to {@link #getSequenceSize()} - 1
   * in case it's defined
   *
   * @return the position of the item inside the sequence.
   */
  public int getPosition() {
    return position;
  }

  /**
   * Determines how many events are in the sequence
   *
   * @return total events in this sequence or {@link OptionalInt#empty()} value if the size is not known
   */
  public OptionalInt getSequenceSize() {
    return sequenceSize > 0 ? OptionalInt.of(sequenceSize) : empty();
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder(120);

    // format message for multi-line output, single-line is not readable
    buf.append("{");
    buf.append("position=").append(getPosition());
    buf.append("; sequenceSize=").append(getSequenceSize().isPresent() ? getSequenceSize().getAsInt() : NOT_SET);
    buf.append('}');
    return buf.toString();
  }

}
