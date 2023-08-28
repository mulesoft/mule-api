/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model.display;

import static java.util.Collections.emptyList;
import static java.util.Collections.unmodifiableList;
import org.mule.runtime.api.meta.model.parameter.ParameterModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A model which describes how a given {@link ParameterModel} is actually representing a class.
 *
 * @since 1.1
 */
public final class ClassValueModel {

  private final List<String> assignableFrom;

  /**
   * Creates a new instance
   *
   * @param assignableFrom a list with the FQN from which the referenced class is assignable from
   */
  public ClassValueModel(List<String> assignableFrom) {
    if (assignableFrom != null && !assignableFrom.isEmpty()) {
      this.assignableFrom = unmodifiableList(new ArrayList<>(assignableFrom));
    } else {
      this.assignableFrom = emptyList();
    }
  }

  /**
   * @return a list with the FQN from which the referenced class is assignable from
   */
  public List<String> getAssignableFrom() {
    return assignableFrom;
  }
}
