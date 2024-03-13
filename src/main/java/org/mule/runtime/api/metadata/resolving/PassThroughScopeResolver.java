/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.metadata.resolving;

import static org.mule.runtime.api.metadata.resolving.FailureCode.INVALID_CONFIGURATION;

import org.mule.metadata.api.model.MetadataType;
import org.mule.runtime.api.connection.ConnectionException;
import org.mule.runtime.api.metadata.MetadataContext;
import org.mule.runtime.api.metadata.MetadataResolvingException;


public class PassThroughScopeResolver<K> implements OutputTypeResolver<K> {

  @Override
  public String getCategoryName() {
    return "OUTPUT_SCOPE_DYNAMIC";
  }

  @Override
  public MetadataType getOutputType(MetadataContext context, K key) throws MetadataResolvingException, ConnectionException {
    return context.getInnerChainOutputType()
        .orElseThrow(() -> new MetadataResolvingException("Invalid Chain output.", INVALID_CONFIGURATION)).get();
  }
}
