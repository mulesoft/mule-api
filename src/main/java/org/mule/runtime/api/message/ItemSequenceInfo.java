package org.mule.runtime.api.message;

import static java.util.OptionalInt.empty;

import java.io.Serializable;
import java.util.OptionalInt;

/**
 * Immutable container for an {@ling Event} sequence information.
 *
 * @since 1.1
 */
public class ItemSequenceInfo implements Serializable {

  public static final String NOT_SET = "<not set>";
  private static final long serialVersionUID = -2294860079566380331L;

  private final int itemIndex;
  private final int sequenceSize;

  public static ItemSequenceInfo of(int itemIndex) {
    return new ItemSequenceInfo(itemIndex, empty());
  }

  public static ItemSequenceInfo of(int itemIndex, int sequenceSize) {
    return new ItemSequenceInfo(itemIndex, OptionalInt.of(sequenceSize));
  }

  /**
   * Builds a new {@link ItemSequenceInfo} with the given parameters.
   *
   * @param itemIndex see {@link #getItemIndex()}.
   * @param sequenceSize see {@link #getSequenceSize()}.
   */
  private ItemSequenceInfo(int itemIndex, OptionalInt sequenceSize) {
    this.itemIndex = itemIndex;
    this.sequenceSize = sequenceSize.orElse(-1);
  }

  /**
   * Gets the index or ordering number for this event in the the sequence.
   *
   * @return the index of the item inside the sequence.
   */
  public int getItemIndex() {
    return itemIndex;
  }

  /**
   * Determines how many events are in the sequence
   *
   * @return total events in this sequence or null value if the size is not known
   */
  public OptionalInt getSequenceSize() {
    return sequenceSize > 0 ? OptionalInt.of(sequenceSize) : empty();
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder(120);

    // format message for multi-line output, single-line is not readable
    buf.append("{");
    buf.append("itemIndex=").append(getItemIndex());
    buf.append("; sequenceSize=").append(getSequenceSize().isPresent() ? getSequenceSize().getAsInt() : NOT_SET);
    buf.append('}');
    return buf.toString();
  }



}
