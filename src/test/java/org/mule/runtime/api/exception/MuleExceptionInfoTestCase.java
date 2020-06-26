/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.exception;

import io.qameta.allure.Issue;
import org.junit.Before;
import org.junit.Test;
import org.mule.runtime.api.message.ErrorType;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mule.runtime.api.i18n.I18nMessageFactory.createStaticMessage;

public class MuleExceptionInfoTestCase {

  MuleExceptionInfo muleExceptionInfo = null;
  private final ErrorType errorType = mock(ErrorType.class);

  @Before
  public void before() {
    when(errorType.toString()).thenReturn("MULE:TESTING");
    muleExceptionInfo = new MuleExceptionInfo();
  }

  @Test
  @Issue("MULE-18041")
  public void whenCausedByExistsThenIsAddedToSummary() {
    StringBuilder summaryMessage = new StringBuilder();
    muleExceptionInfo.getSuppressedCauses().add(new TestMuleException("Caused by error"));
    muleExceptionInfo.addToSummaryMessage(summaryMessage);
    assertThat(summaryMessage.toString(), containsString("Caused by             : Caused by error"));
  }

  @Test
  @Issue("MULE-18041")
  public void whenCausedByErrorTypeExistsThenIsAddedToSummary() {
    StringBuilder summaryMessage = new StringBuilder();
    muleExceptionInfo.getSuppressedCauses().add(new TestMuleException("Caused by error", errorType));
    muleExceptionInfo.addToSummaryMessage(summaryMessage);
    assertThat(summaryMessage.toString(), containsString("Caused by             : MULE:TESTING: Caused by error"));
  }

  @Test
  @Issue("MULE-18041")
  public void whenCausedByNotExistsThenIsNotAddedToSummary() {
    StringBuilder summaryMessage = new StringBuilder();
    muleExceptionInfo.addToSummaryMessage(summaryMessage);
    assertThat(summaryMessage.toString(), not(containsString("Caused by error")));
  }

  private static class TestMuleException extends MuleException {

    private static final long serialVersionUID = -8941618124275385459L;

    public TestMuleException(String message) {
      super(createStaticMessage(message));
    }

    public TestMuleException(String message, ErrorType errorType) {
      super(createStaticMessage(message));
      getExceptionInfo().setErrorType(errorType);
    }
  }
}
