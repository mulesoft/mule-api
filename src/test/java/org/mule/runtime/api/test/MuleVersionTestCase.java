/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.test;

import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.Matchers.is;

import org.mule.runtime.api.meta.MuleVersion;

import org.junit.Ignore;
import org.junit.Test;

import io.qameta.allure.Issue;

@Ignore
public class MuleVersionTestCase {

  @Test
  public void testValidMuleVersions() {
    assertThat("3.3.1", is(new MuleVersion("3.3.1").toString()));
    assertThat("3.4", is(new MuleVersion("3.4").toString()));
    assertThat("3.4-RC1", is(new MuleVersion("3.4-RC1").toString()));
    assertThat("3.4.1-SNAPSHOT", is(new MuleVersion("3.4.1-SNAPSHOT").toString()));
    assertThat("3.4-M3-SNAPSHOT", is(new MuleVersion("3.4-M3-SNAPSHOT").toString()));
    assertThat("4.0.1+20130313144700", is(new MuleVersion("4.0.1+20130313144700").toString()));
    assertThat("4.2.1-prerelease+meta", is(new MuleVersion("4.2.1-prerelease+meta").toString()));
    assertThat("4.9.5----RC-SNAPSHOT.12.9.1--.12+788", is(new MuleVersion("4.9.5----RC-SNAPSHOT.12.9.1--.12+788").toString()));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidMuleVersion1() {
    new MuleVersion("a.b.c");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidMuleVersion2() {
    new MuleVersion("1.1.SNAPSHOT");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidMuleVersion3() {
    new MuleVersion("1");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidMuleVersion4() {
    new MuleVersion("1-RC1");
  }

  @Test
  public void testEquals() {
    assertThat(new MuleVersion("3.3.3"), is(new MuleVersion("3.3.3")));
    assertThat(new MuleVersion("3.4"), is(new MuleVersion("3.4")));
    assertThat(new MuleVersion("3.4-RC1"), is(new MuleVersion("3.4-RC1")));
  }

  @Test
  public void testAtLeast() {
    assertThat(new MuleVersion("3.4.1").atLeast("3.4"), is(true));
    assertThat(new MuleVersion("3.4.1").atLeast("3.4.1"), is(true));
    assertThat(new MuleVersion("3.4.1").atLeast("3.3.2"), is(true));
    assertThat(new MuleVersion("3.4.1").atLeast("3.4.1-RC2"), is(true));

    // 3.4.1-RC1 is previous to the released version 3.4.1
    assertThat(new MuleVersion("3.4.1-RC1").atLeast("3.4.1"), is(false));
  }

  @Test
  public void testAtLeastBase() {
    assertThat(new MuleVersion("3.4.1").atLeastBase("3.4"), is(true));
    assertThat(new MuleVersion("3.4.1-SNAPSHOT").atLeastBase("3.4"), is(true));
    assertThat(new MuleVersion("3.3.0").atLeastBase("3.3"), is(true));
    assertThat(new MuleVersion("3.4.0-RC1").atLeastBase("3.4"), is(true));
    assertThat(new MuleVersion("3.5.0-M1-SNAPSHOT").atLeastBase("3.5.0"), is(true));

    assertThat(new MuleVersion("3.4.1-RC1").atLeastBase("3.5"), is(false));
  }

  @Test
  public void testNewerThan() {
    assertThat(new MuleVersion("3.4.1").newerThan("3.4"), is(true));
    assertThat(new MuleVersion("3.4.1").newerThan("3.3.2"), is(true));
    assertThat(new MuleVersion("3.4.1").newerThan("3.4.1-RC2"), is(true));
    assertThat(new MuleVersion("3.5.0-BIGHORN").newerThan("3.5.0-ANDES"), is(true));

    assertThat(new MuleVersion("3.4.1").newerThan("3.4.1"), is(false));
    assertThat(new MuleVersion("3.5.0-ANDES").newerThan("3.5.0-ANDES"), is(false));
  }

  @Test
  public void testPriorTo() {
    assertThat(new MuleVersion("3.4").priorTo("3.4.1"), is(true));
    assertThat(new MuleVersion("3.3.2").priorTo("3.4.1"), is(true));
    assertThat(new MuleVersion("3.4.1-RC2").priorTo("3.4.1"), is(true));
    assertThat(new MuleVersion("3.5.0-ANDES").priorTo("3.5.0-BIGHORN"), is(true));

    assertThat(new MuleVersion("3.4.1").priorTo("3.4.1"), is(false));
    assertThat(new MuleVersion("3.5.0-ANDES").priorTo("3.5.0-ANDES"), is(false));
  }

  @Test
  @Issue("MULE-19682")
  public void testWithoutSuffixes() {
    assertThat("3.3.1", is(new MuleVersion("3.3.1").withoutSuffixes().toString()));
    assertThat("3.4.0", is(new MuleVersion("3.4").withoutSuffixes().toString()));
    assertThat("3.4.0", is(new MuleVersion("3.4-RC1").withoutSuffixes().toString()));
    assertThat("3.4.1", is(new MuleVersion("3.4.1-SNAPSHOT").withoutSuffixes().toString()));
    assertThat("3.4.0", is(new MuleVersion("3.4-M3-SNAPSHOT").withoutSuffixes().toString()));
    assertThat("4.0.1", is(new MuleVersion("4.0.1").withoutSuffixes().toString()));
    assertThat("4.2.1", is(new MuleVersion("4.2.1-prerelease+meta").withoutSuffixes().toString()));
    assertThat("4.9.5", is(new MuleVersion("4.9.5----RC-SNAPSHOT.12.9.1--.12+788").withoutSuffixes().toString()));
  }

}
