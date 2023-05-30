/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.test.metadata;

import static org.mule.runtime.api.metadata.MetadataKeyBuilder.newKey;

import static org.hamcrest.MatcherAssert.assertThat;

import org.mule.runtime.api.metadata.MetadataKey;
import org.mule.runtime.api.metadata.MetadataKeyBuilder;

import org.hamcrest.Matchers;
import org.hamcrest.core.Is;
import org.junit.Test;

public class DefaultMetadataKeyTestCase {

  @Test
  public void equalKeys() {
    assertThat(europeKey(true).build(), Is.is(europeKey(true).build()));
  }

  @Test
  public void differentKeys() {
    MetadataKey enrichedEurope = europeKey(false).withChild(newKey("Germany")).build();
    assertThat(enrichedEurope, Is.is(Matchers.not(europeKey(false).build())));

    enrichedEurope = europeKey(true).build();
    assertThat(enrichedEurope, Is.is(Matchers.not(europeKey(false).build())));
  }

  private MetadataKeyBuilder europeKey(boolean withManchester) {
    MetadataKeyBuilder uk = newKey("UK")
        .withChild(newKey("London"));

    if (withManchester) {
      uk.withChild(newKey("Manchester"));
    }

    return newKey("Europe")
        .withChild(newKey("France").withChild(newKey("Paris")))
        .withChild(uk);
  }
}
