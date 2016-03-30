/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.api.metadata;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.hamcrest.core.Is.is;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import org.junit.Test;

public class MetadataKeyBuilderTestCase
{

    private static final String INVALID_ID = "INVALID_ID";

    private static final String ID = "id";

    private static final String DESCRIPTION = "description";

    private static final String PROPERTY_ID = "propertyId";
    private static final String PROPERTY_VALUE = "propertyValue";

    private static final Map<String, String> PROPERTIES_MAP = Collections.singletonMap(PROPERTY_ID, PROPERTY_VALUE);

    @Test
    public void createMetadataKey()
    {

        final MetadataKey key = MetadataKeyBuilder.newKey(ID)
                .withDisplayName(DESCRIPTION)
                .withProperty(PROPERTY_ID, PROPERTY_VALUE)
                .build();

        assertThat(key.getId(), is(ID));
        assertThat(key.getDisplayName(), is(DESCRIPTION));
        assertThat(key.getProperty(PROPERTY_ID), is(Optional.of(PROPERTY_VALUE)));
        assertThat(key.getProperty(INVALID_ID), is(Optional.empty()));
        assertThat(key.getProperties(), is(PROPERTIES_MAP));

    }

    @Test
    public void createMetadataKeyWithDefaults()
    {
        MetadataKey key = MetadataKeyBuilder.newKey(ID).build();

        assertThat(key.getId(), is(ID));
        assertThat(key.getDisplayName(), is(ID));
        assertThat(key.getProperties().entrySet(), is(empty()));
    }

}
