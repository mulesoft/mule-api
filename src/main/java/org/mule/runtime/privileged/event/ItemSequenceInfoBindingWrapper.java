/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.privileged.event;

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
