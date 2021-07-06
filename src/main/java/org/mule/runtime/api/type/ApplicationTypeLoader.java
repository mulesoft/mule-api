/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.type;

import org.mule.metadata.api.TypeLoader;
import org.mule.metadata.api.model.MetadataType;
import org.mule.runtime.api.metadata.MediaType;

import java.util.Optional;

public interface ApplicationTypeLoader  extends TypeLoader {

  Optional<MetadataType> load(String typeIdentifier, String mediaType);

  Optional<MetadataType> load(String typeIdentifier, MediaType mediaType);
}
