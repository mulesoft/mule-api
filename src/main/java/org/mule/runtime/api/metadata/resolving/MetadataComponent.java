/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.metadata.resolving;

/**
 * Enumerates all the components that can be retrieved in a {@link MetadataResult}.
 *
 * @since 1.0
 */
public enum MetadataComponent {

  /**
   * Component that retrieves metadata for an Operation or Source output attributes type.
   */
  OUTPUT_ATTRIBUTES,

  /**
   * Component that retrieves metadata for an Operation or Source output type.
   */
  OUTPUT_PAYLOAD,

  /**
   * Component that retrieves metadata for an Operation or Source parameter.
   */
  INPUT,

  /**
   * Component that retrieves metadata for an Operation or a Source.
   */
  COMPONENT,

  /**
   * Component that retrieves metadata about DSQL entities.
   */
  ENTITY,

  /**
   * Component that retrieves metadata about the keys.
   */
  KEYS
}
