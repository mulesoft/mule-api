/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.test.deployment.exception;

import io.qameta.allure.Issue;
import org.junit.Test;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;
import static org.mule.runtime.internal.exception.SuppressedMuleException.suppressIfPresent;

import org.mule.runtime.api.exception.DefaultMuleException;
import org.mule.runtime.api.exception.MuleException;

public class MuleExceptionTestCase {

  @Test
  @Issue("MULE-18041")
  public void summaryMessageMustNotIncludeAllInformation() {
    TestException testException = new TestException();
    assertThat(testException.getSummaryMessage(), not(containsString("Additional info value")));
  }

  @Test
  @Issue("MULE-18041")
  public void verboseMessageMustIncludeAllInformation() {
    TestException testException = new TestException();
    assertThat(testException.getVerboseMessage(), containsString("Additional info value"));
  }

  @Test
  @Issue("MULE-19811")
  public void summaryMessageWithNullRootMuleException() {
    MuleException muleException =
        (MuleException) suppressIfPresent(new DefaultMuleException("Suppressed exception", new TestException()),
                                          DefaultMuleException.class);
    assertThat(muleException.getSummaryMessage(), containsString("Suppressed exception"));
  }

  private static class TestException extends MuleException {

    private static final long serialVersionUID = 1029575542191754519L;

    public TestException() {
      addInfo("Additional info key", "Additional info value");
    }
  }
}
