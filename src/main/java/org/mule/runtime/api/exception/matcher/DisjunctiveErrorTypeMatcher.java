/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.exception.matcher;

import org.mule.runtime.api.message.ErrorType;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class DisjunctiveErrorTypeMatcher implements ErrorTypeMatcher {

  List<ErrorTypeMatcher> errorTypeMatchers;

  public DisjunctiveErrorTypeMatcher(List<ErrorTypeMatcher> errorTypeMatchers) {
    this.errorTypeMatchers = new CopyOnWriteArrayList<>(errorTypeMatchers);
  }

  @Override
  public boolean match(ErrorType errorType) {
    for (ErrorTypeMatcher errorTypeMatcher : errorTypeMatchers) {
      if (errorTypeMatcher.match(errorType)) {
        return true;
      }
    }

    return false;
  }
}

