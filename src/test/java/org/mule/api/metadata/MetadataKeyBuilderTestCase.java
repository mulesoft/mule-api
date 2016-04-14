/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.api.metadata;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.hamcrest.core.Is.is;

import java.util.Optional;

import org.junit.Test;

public class MetadataKeyBuilderTestCase
{

    private static final String ID = "id";
    private static final String DESCRIPTION = "description";

    @Test
    public void createMetadataKey()
    {
        TestMetadataProperty testMetadataProperty = new TestMetadataProperty();

        final MetadataKey key = MetadataKeyBuilder.newKey(ID)
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
    public void createMetadataKeyWithDefaults()
    {
        MetadataKey key = MetadataKeyBuilder.newKey(ID).build();

        assertThat(key.getId(), is(ID));
        assertThat(key.getDisplayName(), is(ID));
        assertThat(key.getProperties(), is(empty()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void addMetadataPropertiesOfTheSameType()
    {
        MetadataKeyBuilder.newKey(ID)
                .withDisplayName(DESCRIPTION)
                .withProperty(new TestMetadataProperty())
                .withProperty(new TestMetadataProperty())
                .build();
    }


    private class TestMetadataProperty implements MetadataProperty
    {
        @Override
        public String getName()
        {
            return "TestMetadataProperty";
        }
    }

    private class InvalidMetadataProperty implements MetadataProperty
    {
        @Override
        public String getName()
        {
            return "Invalid";
        }
    }
}
