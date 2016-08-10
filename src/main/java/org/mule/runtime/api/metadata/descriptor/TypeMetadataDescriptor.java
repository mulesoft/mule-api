/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.metadata.descriptor;

import org.mule.metadata.api.model.MetadataType;

/**
 * Represents the Metadata view of a simple {@link MetadataType}
 *
 * @since 1.0
 */
public interface TypeMetadataDescriptor {

  /**
   * @return the component's {@link MetadataType}
   */
  MetadataType getType();
}
