/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.internal.event;

import org.mule.runtime.api.message.ItemSequenceInfo;

/**
 * Wrapper for binding {@link ItemSequenceInfo} properly to DW
 */
public class ItemSequenceInfoBindingWrapper {

  private ItemSequenceInfo info;

  public ItemSequenceInfoBindingWrapper(ItemSequenceInfo info) {
    this.info = info;
  }

  public int getPosition() {
    return info.getPosition();
  }

  public Integer getSequenceSize() {
    return info.getSequenceSize().isPresent() ? info.getSequenceSize().getAsInt() : null;
  }

  @Override
  public String toString() {
    return info.toString();
  }
}
