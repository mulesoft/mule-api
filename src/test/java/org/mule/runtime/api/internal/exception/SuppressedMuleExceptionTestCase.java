package org.mule.runtime.api.internal.exception;

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
  public void whenClassToSuppressIsNotFoundThenNoSuppressionIsAdded() {
    Throwable result = SuppressedMuleException.suppressIfPresent(new TestException(), AnotherTestException.class);
    assertThat(result, is(instanceOf(TestException.class)));
  }

  @Test
  public void whenClassToSuppressIsFoundThenSuppressionIsAdded() {
    Throwable result =
        SuppressedMuleException.suppressIfPresent(new TestException(new AnotherTestException()), AnotherTestException.class);
    assertThat(result, is(instanceOf(SuppressedMuleException.class)));
  }

  @Test
  public void whenSuppressionIsAddedThenIncludeSuppressedAdditionalProperties() {
    Throwable suppressedTestException =
        SuppressedMuleException.suppressIfPresent(new TestException(new AnotherTestException()), TestException.class);
    Map<String, Object> suppressedTestExceptionEntries = ((SuppressedMuleException) suppressedTestException).getInfo();
    Throwable suppressedAnotherTestException =
        SuppressedMuleException.suppressIfPresent(new TestException(new AnotherTestException()), AnotherTestException.class);
    Map<String, Object> suppressedAnotherTestExceptionEntries =
        ((SuppressedMuleException) suppressedAnotherTestException).getInfo();
    assertThat(suppressedTestExceptionEntries, hasEntry("Additional entry key", "Test additional entry value"));
    assertThat(suppressedAnotherTestExceptionEntries, hasEntry("Additional entry key", "Test additional entry value"));
  }

  @Test
  public void whenSuppressionIsAddedThenPreviousSuppressionsAreAdded() {
    Throwable suppressedTestException = SuppressedMuleException.suppressIfPresent(new TestException(), TestException.class);
    Throwable suppressedAnotherTestException =
        SuppressedMuleException.suppressIfPresent(new AnotherTestException(suppressedTestException), AnotherTestException.class);
    List<MuleException> suppressions = ((MuleException) suppressedAnotherTestException).getExceptionInfo().getSuppressedCauses();
    assertThat(suppressions,
               contains(sameInstance(((SuppressedMuleException) suppressedAnotherTestException).getSuppressedException()),
                        sameInstance(((SuppressedMuleException) suppressedTestException).getSuppressedException())));
  }

  private static class TestException extends MuleException {

    private static final long serialVersionUID = 21078091124109763L;

    public TestException() {}

    public TestException(Throwable cause) {
      super(cause);
    }
  }

  private static class AnotherTestException extends MuleException {

    private static final long serialVersionUID = -2095590887184779909L;

    public AnotherTestException() {
      getExceptionInfo().putAdditionalEntry("Additional entry key", "Test additional entry value");
    }

    public AnotherTestException(Throwable cause) {
      super(cause);
      getExceptionInfo().putAdditionalEntry("Additional entry key", "Test additional entry value");
    }
  }

}
