/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.internal.exception;

import static java.util.Arrays.asList;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.collection.IsMapContaining.hasEntry;
import static org.hamcrest.core.Is.is;

import org.mule.runtime.api.exception.MuleException;
import org.mule.runtime.internal.exception.SuppressedMuleException;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import io.qameta.allure.Issue;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class SuppressedMuleExceptionTestCase {

  private final boolean suppressExceptions;

  @Parameterized.Parameters(name = "suppress exceptions: {0}")
  public static Collection<Object[]> params() {
    return asList(new Object[][] {{true}, {false}});
  }

  public SuppressedMuleExceptionTestCase(boolean suppressExceptions) {
    this.suppressExceptions = suppressExceptions;
  }

  @Before
  public void initializeClassUnderTest() throws NoSuchFieldException, IllegalAccessException {
    setSuppressExceptionsValue(suppressExceptions);
  }

  @After
  public void revertClassUnderTestChanges() throws IllegalAccessException, NoSuchFieldException {
    setSuppressExceptionsValue(true);
  }

  private void setSuppressExceptionsValue(boolean suppressExceptions) throws NoSuchFieldException, IllegalAccessException {
    Field suppressExceptionsField = SuppressedMuleException.class.getDeclaredField("SUPPRESS_EXCEPTIONS");
    suppressExceptionsField.setAccessible(true);
    Field modifiersField = Field.class.getDeclaredField("modifiers");
    modifiersField.setAccessible(true);
    modifiersField.setInt(suppressExceptionsField, suppressExceptionsField.getModifiers() & ~Modifier.FINAL);
    suppressExceptionsField.set(null, suppressExceptions);
  }

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
        SuppressedMuleException.suppressIfPresent(new SimpleException(new ExceptionWithAdditionalInfo()),
                                                  ExceptionWithAdditionalInfo.class);
    if (suppressExceptions) {
      assertThat(result, is(instanceOf(SuppressedMuleException.class)));
    } else {
      assertThat(result, is(instanceOf(SimpleException.class)));
    }
  }

  @Test
  @Issue("MULE-18041")
  public void whenSuppressionIsAddedThenIncludeSuppressedAdditionalProperties() {
    SimpleException simpleException = new SimpleException(new ExceptionWithAdditionalInfo());
    Throwable suppressedTestException =
        SuppressedMuleException.suppressIfPresent(simpleException, SimpleException.class);
    Throwable suppressedAnotherTestException =
        SuppressedMuleException.suppressIfPresent(simpleException,
                                                  ExceptionWithAdditionalInfo.class);
    if (suppressExceptions) {
      assertThat(suppressedTestException, is(instanceOf(SuppressedMuleException.class)));
      assertThat(suppressedAnotherTestException, is(instanceOf(SuppressedMuleException.class)));
      Map<String, Object> suppressedTestExceptionEntries = ((SuppressedMuleException) suppressedTestException).getInfo();
      Map<String, Object> suppressedAnotherTestExceptionEntries =
          ((SuppressedMuleException) suppressedAnotherTestException).getInfo();
      assertThat(suppressedTestExceptionEntries, hasEntry("Additional entry key", "Test additional entry value"));
      assertThat(suppressedAnotherTestExceptionEntries, hasEntry("Additional entry key", "Test additional entry value"));
    } else {
      assertThat(suppressedTestException, is(simpleException));
      assertThat(suppressedAnotherTestException, is(simpleException));
    }
  }

  @Test
  @Issue("MULE-18041")
  public void whenSuppressionIsAddedThenPreviousSuppressionsAreAdded() {
    Throwable suppressedTestException = SuppressedMuleException.suppressIfPresent(new SimpleException(), SimpleException.class);
    Throwable suppressedAnotherTestException =
        SuppressedMuleException.suppressIfPresent(new ExceptionWithAdditionalInfo(suppressedTestException),
                                                  ExceptionWithAdditionalInfo.class);
    List<MuleException> suppressions = ((MuleException) suppressedAnotherTestException).getExceptionInfo().getSuppressedCauses();

    if (suppressExceptions) {
      assertThat(suppressions,
                 contains(sameInstance(((SuppressedMuleException) suppressedAnotherTestException).getSuppressedException()),
                          sameInstance(((SuppressedMuleException) suppressedTestException).getSuppressedException())));
    } else {
      assertThat(suppressions, is(empty()));
    }
  }

  @Test
  public void selfCausedExceptionInMainLoopMustBeResolved() {
    Throwable suppressedSelfCausedException =
        SuppressedMuleException.suppressIfPresent(new SimpleException(new SelfCausedException()), SelfCausedException.class);
    List<MuleException> suppressions = ((MuleException) suppressedSelfCausedException).getExceptionInfo().getSuppressedCauses();
    if (suppressExceptions) {
      assertThat(suppressedSelfCausedException, is(instanceOf(SuppressedMuleException.class)));
      assertThat(suppressions,
                 contains(sameInstance(((SuppressedMuleException) suppressedSelfCausedException).getSuppressedException())));
    } else {
      assertThat(suppressedSelfCausedException, is(instanceOf(SimpleException.class)));
      assertThat(suppressions, is(empty()));
    }
  }

  @Test
  public void selfCausedExceptionInSecondaryLoopMustBeResolved() {
    Throwable suppressedSelfCausedException =
        SuppressedMuleException.suppressIfPresent(new SimpleException(new SelfCausedException()), SimpleException.class);
    List<MuleException> suppressions = ((MuleException) suppressedSelfCausedException).getExceptionInfo().getSuppressedCauses();
    if (suppressExceptions) {
      assertThat(suppressedSelfCausedException, is(instanceOf(SuppressedMuleException.class)));
      assertThat(suppressions,
                 contains(sameInstance(((SuppressedMuleException) suppressedSelfCausedException).getSuppressedException())));
    } else {
      assertThat(suppressedSelfCausedException, is(instanceOf(SimpleException.class)));
      assertThat(suppressions, is(empty()));
    }
  }

  @Test
  @Issue("MULE-18562")
  public void consecutiveSuppressionsMustNotBePossible() {
    SimpleException exception = new SimpleException(new ExceptionWithAdditionalInfo());
    MuleException suppression =
        (MuleException) SuppressedMuleException.suppressIfPresent(exception,
                                                                  ExceptionWithAdditionalInfo.class);
    MuleException attemptedConsecutiveSuppression =
        (MuleException) SuppressedMuleException.suppressIfPresent(suppression,
                                                                  SimpleException.class);
    if (suppressExceptions) {
      assertThat(attemptedConsecutiveSuppression, is(instanceOf(SuppressedMuleException.class)));
      assertThat(attemptedConsecutiveSuppression, is(suppression));
    } else {
      assertThat(attemptedConsecutiveSuppression, is(exception));
    }
  }

  @Test
  @Issue("MULE-18562")
  public void suppressionMustBeUnwrapped() {
    SimpleException unwrappedException = new SimpleException();
    MuleException result = (MuleException) SuppressedMuleException.suppressIfPresent(new SimpleException(),
                                                                                     SimpleException.class);
    if (suppressExceptions) {
      assertThat(((SuppressedMuleException) result).unwrap(), is(unwrappedException));
    } else {
      assertThat(result, is(unwrappedException));
    }
  }

  private void assertSuppressionResult(MuleException actual, MuleException expectedWithSuppression,
                                       MuleException expectedWithoutSuppression) {
    if (suppressExceptions) {

    } else {

    }
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
