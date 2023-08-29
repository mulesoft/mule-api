/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.test.message.error.matcher;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;
import org.mule.runtime.api.component.ComponentIdentifier;
import org.mule.runtime.api.message.ErrorType;
import org.mule.runtime.api.message.error.matcher.DisjunctiveErrorTypeMatcher;
import org.mule.runtime.api.message.error.matcher.ErrorTypeMatcher;
import org.mule.runtime.api.message.error.matcher.SingleErrorTypeMatcher;
import org.mule.runtime.api.message.error.matcher.WildcardErrorTypeMatcher;

import java.util.ArrayList;
import java.util.List;

public class ErrorMatchersTestCase {

  private ErrorType dummyParent = new ErrorType() {

    @Override
    public String getIdentifier() {
      return "PARENT";
    }

    @Override
    public String getNamespace() {
      return "TST";
    }

    @Override
    public ErrorType getParentErrorType() {
      return null;
    }
  };

  private ErrorType dummyErrorType = new ErrorType() {

    private static final long serialVersionUID = -5155728711167777541L;

    @Override
    public String getIdentifier() {
      return "TEST_ERROR";
    }

    @Override
    public String getNamespace() {
      return "TST";
    }

    @Override
    public ErrorType getParentErrorType() {
      return dummyParent;
    }
  };

  private ErrorType unrelatedError = new ErrorType() {

    @Override
    public String getIdentifier() {
      return "TEST_ERROR";
    }

    @Override
    public String getNamespace() {
      return "OTHERNAMESPACE";
    }

    @Override
    public ErrorType getParentErrorType() {
      return null;
    }
  };

  @Test
  public void singleErrorTypeForSpecificError() {
    ErrorTypeMatcher matcher = new SingleErrorTypeMatcher(dummyParent);
    assertThat(matcher.match(dummyParent), is(true));
    assertThat(matcher.match(dummyErrorType), is(true));
    assertThat(matcher.match(unrelatedError), is(false));
  }

  @Test
  public void combinedErrorTypes() {
    List<ErrorTypeMatcher> matchers = new ArrayList<>();
    matchers.add(new SingleErrorTypeMatcher(dummyErrorType));
    matchers.add(new SingleErrorTypeMatcher(unrelatedError));
    ErrorTypeMatcher matcher = new DisjunctiveErrorTypeMatcher(matchers);
    assertThat(matcher.match(dummyParent), is(false));
    assertThat(matcher.match(dummyErrorType), is(true));
    assertThat(matcher.match(unrelatedError), is(true));
  }

  @Test
  public void wildcardInNameMatcher() {
    ErrorTypeMatcher matcher = new WildcardErrorTypeMatcher(new ComponentIdentifier() {

      @Override
      public String getNamespace() {
        return "TST";
      }

      @Override
      public String getNamespaceUri() {
        return null;
      }

      @Override
      public String getName() {
        return "*";
      }
    });
    assertThat(matcher.match(dummyParent), is(true));
    assertThat(matcher.match(dummyErrorType), is(true));
    assertThat(matcher.match(unrelatedError), is(false));
  }


  @Test
  public void wildcardInNamespaceMatcher() {
    ErrorTypeMatcher matcher = new WildcardErrorTypeMatcher(new ComponentIdentifier() {

      @Override
      public String getNamespace() {
        return "*";
      }

      @Override
      public String getNamespaceUri() {
        return null;
      }

      @Override
      public String getName() {
        return "TEST_ERROR";
      }
    });
    assertThat(matcher.match(dummyParent), is(false));
    assertThat(matcher.match(dummyErrorType), is(true));
    assertThat(matcher.match(unrelatedError), is(true));
  }
}
