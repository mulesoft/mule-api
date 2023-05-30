/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.test.metadata;

import static org.mule.runtime.api.metadata.MetadataKeyBuilder.newKey;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.hamcrest.core.Is.is;

import org.mule.runtime.api.metadata.MetadataKey;
import org.mule.runtime.api.metadata.MetadataKeyBuilder;
import org.mule.runtime.api.metadata.MetadataProperty;

import java.util.Optional;

import org.junit.Test;

public class MetadataKeyBuilderTestCase {

  private static final String ID = "id";
  private static final String DESCRIPTION = "description";
  private static final String CHILD = "Child";

  @Test
  public void createMetadataKey() {
    TestMetadataProperty testMetadataProperty = new TestMetadataProperty();

    final MetadataKey key = newKey(ID)
        .withDisplayName(DESCRIPTION)
        .withProperty(testMetadataProperty)
        .build();

    assertThat(key.getId(), is(ID));
    assertThat(key.getDisplayName(), is(DESCRIPTION));
    assertThat(key.getMetadataProperty(TestMetadataProperty.class).isPresent(), is(true));
    assertThat(key.getMetadataProperty(TestMetadataProperty.class).get(), is(testMetadataProperty));
    assertThat(key.getMetadataProperty(InvalidMetadataProperty.class), is(Optional.empty()));
    assertThat(key.getProperties(), hasSize(1));
    assertThat(key.getProperties(), contains(testMetadataProperty));
  }

  @Test
  public void createMetadataKeyWithDefaults() {
    MetadataKey key = newKey(ID).build();

    assertThat(key.getId(), is(ID));
    assertThat(key.getDisplayName(), is(ID));
    assertThat(key.getProperties(), is(empty()));
  }

  @Test(expected = IllegalArgumentException.class)
  public void addMetadataPropertiesOfTheSameType() {
    newKey(ID)
        .withDisplayName(DESCRIPTION)
        .withProperty(new TestMetadataProperty())
        .withProperty(new TestMetadataProperty())
        .build();
  }

  @Test
  public void createMultilevelMetadataKey() {
    final MetadataKeyBuilder childKey = newKey(CHILD).withDisplayName(CHILD);
    final MetadataKeyBuilder childKey2 = newKey(CHILD + "2");

    MetadataKey key = newKey(ID)
        .withDisplayName(DESCRIPTION)
        .withProperty(new TestMetadataProperty())
        .withChild(childKey)
        .withChild(childKey2)
        .build();

    assertThat(key.getId(), is(ID));
    assertThat(key.getDisplayName(), is(DESCRIPTION));
    assertThat(key.getProperties(), hasSize(1));
    assertThat(key.getChilds(), hasSize(2));
    assertThat(key.getChilds(), hasItems(childKey.build(), childKey2.build()));
  }

  private class TestMetadataProperty implements MetadataProperty {

    @Override
    public String getName() {
      return "TestMetadataProperty";
    }
  }

  private class InvalidMetadataProperty implements MetadataProperty {

    @Override
    public String getName() {
      return "Invalid";
    }
  }
}
