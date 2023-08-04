/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.test.metadata;

import static org.mule.runtime.api.metadata.MetadataKeyBuilder.newKey;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.Is.is;

import org.mule.runtime.api.metadata.MetadataKey;
import org.mule.runtime.api.metadata.MetadataKeyBuilder;

import org.junit.Test;

public class DefaultMetadataKeyTestCase {

  @Test
  public void equalKeys() {
    assertThat(europeKey(true).build(), is(europeKey(true).build()));
  }

  @Test
  public void differentKeys() {
    MetadataKey enrichedEurope = europeKey(false).withChild(newKey("Germany")).build();
    assertThat(enrichedEurope, is(not(europeKey(false).build())));

    enrichedEurope = europeKey(true).build();
    assertThat(enrichedEurope, is(not(europeKey(false).build())));
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
