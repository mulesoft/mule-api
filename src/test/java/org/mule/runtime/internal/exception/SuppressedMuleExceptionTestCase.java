/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.internal.exception;

import io.qameta.allure.Issue;
import org.hamcrest.MatcherAssert;
import org.junit.Test;
import org.mule.runtime.api.exception.MuleException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.mule.runtime.internal.exception.SuppressedMuleException.suppressIfPresent;

public class SuppressedMuleExceptionTestCase {

  @Test
  @Issue("MULE-18041")
  public void whithSameSuppressionShouldNotBeEquals() {
    ExceptionToSuppress exceptionToSuppress = new ExceptionToSuppress();
    Throwable one = suppressIfPresent(exceptionToSuppress, ExceptionToSuppress.class);
    Throwable two = suppressIfPresent(exceptionToSuppress, ExceptionToSuppress.class);
    MatcherAssert.assertThat(one, not(equalTo(two)));
  }

  @Test
  @Issue("MULE-18041")
  public void whithSameSuppressionHashCodeShouldNotBeEquals() {
    ExceptionToSuppress exceptionToSuppress = new ExceptionToSuppress();
    Throwable one = suppressIfPresent(exceptionToSuppress, ExceptionToSuppress.class);
    Throwable two = suppressIfPresent(exceptionToSuppress, ExceptionToSuppress.class);
    MatcherAssert.assertThat(one.hashCode(), not(equalTo(two.hashCode())));
  }

  private static class ExceptionToSuppress extends MuleException {

    private static final long serialVersionUID = -225801936346795213L;
  }

}
