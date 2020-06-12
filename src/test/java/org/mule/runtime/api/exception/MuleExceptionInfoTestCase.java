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

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

public class MuleExceptionInfoTestCase {

  MuleExceptionInfo muleExceptionInfo = null;

  @Before
  public void before() {
    muleExceptionInfo = new MuleExceptionInfo();
  }

  @Test
  @Issue("MULE-18041")
  public void whenCausedByExistsThenIsAddedToSummary() {
    StringBuilder summaryMessage = new StringBuilder();
    muleExceptionInfo.setCausedBy("Caused by error");
    muleExceptionInfo.addToSummaryMessage(summaryMessage);
    assertThat(summaryMessage.toString(), containsString("Caused by error"));
  }

  @Test
  @Issue("MULE-18041")
  public void whenCausedByNotExistsThenIsNotAddedToSummary() {
    StringBuilder summaryMessage = new StringBuilder();
    muleExceptionInfo.addToSummaryMessage(summaryMessage);
    assertThat(summaryMessage.toString(), not(containsString("Caused by error")));
  }

}
