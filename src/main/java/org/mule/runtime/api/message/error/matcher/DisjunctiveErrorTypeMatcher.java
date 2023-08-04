/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.message.error.matcher;

import org.mule.api.annotation.NoExtend;
import org.mule.api.annotation.NoImplement;
import org.mule.runtime.api.message.ErrorType;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Implementation of {@link ErrorTypeMatcher} that is a composition of other {@link ErrorTypeMatcher}.
 * 
 * @since 1.5
 */
@NoImplement
@NoExtend
public class DisjunctiveErrorTypeMatcher implements ErrorTypeMatcher {

  private final List<ErrorTypeMatcher> errorTypeMatchers;

  public DisjunctiveErrorTypeMatcher(List<ErrorTypeMatcher> errorTypeMatchers) {
    this.errorTypeMatchers = new CopyOnWriteArrayList<>(errorTypeMatchers);
  }

  @Override
  public boolean match(ErrorType errorType) {
    return errorTypeMatchers.stream().anyMatch(matcher -> matcher.match(errorType));
  }
}

