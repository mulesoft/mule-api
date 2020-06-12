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
