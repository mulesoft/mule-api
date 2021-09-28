/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.exception;

import io.qameta.allure.Issue;
import org.junit.Test;
import org.mule.runtime.internal.exception.SuppressedMuleException;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;

public class MuleExceptionTestCase {

  @Test
  @Issue("MULE-19811")
  public void summaryMessageWithNullRootMuleException() {
    MuleException muleException =
            new SuppressedMuleException(new DefaultMuleException("Suppressed exception", new TestException()));
    assertThat(muleException.getSummaryMessage(), containsString("Suppressed exception"));
  }

  private static class TestException extends MuleException {

    private static final long serialVersionUID = 1029575542191754519L;

    public TestException() {
      addInfo("Additional info key", "Additional info value");
    }
  }
}
