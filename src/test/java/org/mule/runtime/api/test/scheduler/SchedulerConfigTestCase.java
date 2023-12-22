/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.test.scheduler;


import static org.mule.runtime.api.scheduler.SchedulerConfig.config;

import static java.lang.String.format;
import static java.lang.Thread.MAX_PRIORITY;
import static java.lang.Thread.MIN_PRIORITY;

import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.rules.ExpectedException.none;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class SchedulerConfigTestCase {

  @Rule
  public ExpectedException expected = none();

  @Test
  public void invalidLowPriority() {
    assertInvalidPriorityIsRejected(MIN_PRIORITY - 1);
  }

  @Test
  public void invalidHighPriority() {
    assertInvalidPriorityIsRejected(MAX_PRIORITY + 1);
  }

  private void assertInvalidPriorityIsRejected(int priority) {
    expected.expect(instanceOf(IllegalArgumentException.class));
    expected.expectMessage(format("'priority' must be in the range [1, 10]. %d passed", priority));
    config().withPriority(priority);
  }
}
