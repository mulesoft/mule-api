/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.test.internal.exception;

import io.qameta.allure.Issue;
import org.junit.Test;
import org.mule.runtime.api.exception.MuleException;
import org.mule.runtime.internal.exception.SuppressedMuleException;

import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.collection.IsMapContaining.hasEntry;
import static org.hamcrest.core.Is.is;

public class SuppressedMuleExceptionTestCase {

  @Test
  @Issue("MULE-18041")
  public void whenClassToSuppressIsNotFoundThenNoSuppressionIsAdded() {
    Throwable result = SuppressedMuleException.suppressIfPresent(new SimpleException(), ExceptionWithAdditionalInfo.class);
    assertThat(result, is(instanceOf(SimpleException.class)));
  }

  @Test
  @Issue("MULE-18041")
  public void whenClassToSuppressIsFoundThenSuppressionIsAdded() {
    Throwable result =
        SuppressedMuleException.suppressIfPresent(new SimpleException(new SimpleException(new ExceptionWithAdditionalInfo())),
                                                  ExceptionWithAdditionalInfo.class);
    assertThat(result, is(instanceOf(SuppressedMuleException.class)));
  }

  @Test
  @Issue("MULE-18041")
  public void whenSuppressionIsAddedThenIncludeSuppressedAdditionalProperties() {
    Throwable suppressedTestException =
        SuppressedMuleException.suppressIfPresent(new SimpleException(new ExceptionWithAdditionalInfo()), SimpleException.class);
    Map<String, Object> suppressedTestExceptionEntries = ((SuppressedMuleException) suppressedTestException).getInfo();
    Throwable suppressedAnotherTestException =
        SuppressedMuleException.suppressIfPresent(new SimpleException(new ExceptionWithAdditionalInfo()),
                                                  ExceptionWithAdditionalInfo.class);
    Map<String, Object> suppressedAnotherTestExceptionEntries =
        ((SuppressedMuleException) suppressedAnotherTestException).getInfo();
    assertThat(suppressedTestExceptionEntries, hasEntry("Additional entry key", "Test additional entry value"));
    assertThat(suppressedAnotherTestExceptionEntries, hasEntry("Additional entry key", "Test additional entry value"));
  }

  @Test
  @Issue("MULE-18041")
  public void whenSuppressionIsAddedThenPreviousSuppressionsAreAdded() {
    Throwable suppressedTestException = SuppressedMuleException.suppressIfPresent(new SimpleException(), SimpleException.class);
    Throwable suppressedAnotherTestException =
        SuppressedMuleException.suppressIfPresent(new ExceptionWithAdditionalInfo(suppressedTestException),
                                                  ExceptionWithAdditionalInfo.class);
    List<MuleException> suppressions = ((MuleException) suppressedAnotherTestException).getExceptionInfo().getSuppressedCauses();
    assertThat(suppressions,
               contains(sameInstance(((SuppressedMuleException) suppressedAnotherTestException).getSuppressedException()),
                        sameInstance(((SuppressedMuleException) suppressedTestException).getSuppressedException())));
  }

  @Test
  public void selfCausedExceptionInMainLoopMustBeResolved() {
    Throwable suppressedSelfCausedException =
        SuppressedMuleException.suppressIfPresent(new SimpleException(new SelfCausedException()), SelfCausedException.class);
    assertThat(suppressedSelfCausedException, is(instanceOf(SuppressedMuleException.class)));
    List<MuleException> suppressions = ((MuleException) suppressedSelfCausedException).getExceptionInfo().getSuppressedCauses();
    assertThat(suppressions,
               contains(sameInstance(((SuppressedMuleException) suppressedSelfCausedException).getSuppressedException())));
  }

  @Test
  public void selfCausedExceptionInSecondaryLoopMustBeResolved() {
    Throwable suppressedSelfCausedException =
        SuppressedMuleException.suppressIfPresent(new SimpleException(new SelfCausedException()), SimpleException.class);
    assertThat(suppressedSelfCausedException, is(instanceOf(SuppressedMuleException.class)));
    List<MuleException> suppressions = ((MuleException) suppressedSelfCausedException).getExceptionInfo().getSuppressedCauses();
    assertThat(suppressions,
               contains(sameInstance(((SuppressedMuleException) suppressedSelfCausedException).getSuppressedException())));
  }

  @Test
  @Issue("MULE-18562")
  public void consecutiveSuppressionsMustNotBePossible() {
    SimpleException exception = new SimpleException(new ExceptionWithAdditionalInfo());
    SuppressedMuleException suppression =
        (SuppressedMuleException) SuppressedMuleException.suppressIfPresent(exception,
                                                                            ExceptionWithAdditionalInfo.class);
    SuppressedMuleException attemptedConsecutiveSuppression =
        (SuppressedMuleException) SuppressedMuleException.suppressIfPresent(suppression,
                                                                            SimpleException.class);
    assertThat(attemptedConsecutiveSuppression, is(suppression));
  }

  @Test
  @Issue("MULE-18562")
  public void suppressionMustBeUnwrapped() {
    SimpleException unwrappedException = new SimpleException();
    SuppressedMuleException result =
        (SuppressedMuleException) SuppressedMuleException.suppressIfPresent(new SimpleException(),
                                                                            SimpleException.class);
    assertThat(result.unwrap(), is(unwrappedException));
  }

  private static class SimpleException extends MuleException {

    private static final long serialVersionUID = 21078091124109763L;

    public SimpleException() {}

    public SimpleException(Throwable cause) {
      super(cause);
    }

  }

  private static class ExceptionWithAdditionalInfo extends MuleException {

    private static final long serialVersionUID = -2095590887184779909L;

    public ExceptionWithAdditionalInfo() {
      getExceptionInfo().putAdditionalEntry("Additional entry key", "Test additional entry value");
    }

    public ExceptionWithAdditionalInfo(Throwable cause) {
      super(cause);
      getExceptionInfo().putAdditionalEntry("Additional entry key", "Test additional entry value");
    }

  }

  private static class SelfCausedException extends MuleException {

    private static final long serialVersionUID = -8420615463020752243L;

    @Override
    public synchronized Throwable getCause() {
      return this;
    }

  }
}
