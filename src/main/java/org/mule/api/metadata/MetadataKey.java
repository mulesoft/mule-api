/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.api.metadata;

import org.mule.metadata.api.model.MetadataType;

import java.util.Map;
import java.util.Optional;

/**
 * A key, that with the given ID, represents a {@link MetadataType}.
 * This key can be contributed with a display name and user defined properties.
 *
 * @since 1.0
 */
public interface MetadataKey
{

    /**
     * @return identifier for the current key
     */
    String getId();

    /**
     * @return human readable name to use when displaying the key
     */
    String getDisplayName();

    /**
     * @param propertyId Id of the property to retrieve
     * @return the desired property, if not exist, an {@link Optional#empty()} will be returned.
     */
    Optional<String> getProperty(String propertyId);

    /**
     * @return the entire set of properties of the current {@link MetadataKey}
     */
    Map<String, String> getProperties();
}
