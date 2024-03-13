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
import org.mule.runtime.api.meta.model.OutputModel;
import org.mule.runtime.api.metadata.MetadataContext;
import org.mule.runtime.api.metadata.MetadataResolvingException;

/**
 * {@link OutputTypeResolver} implementation for Scopes.
 * <p>
 * This {@link OutputTypeResolver} propagates the metadata result of the inner chain of the scope, without any modification.
 *
 * @since 1.7
 */
public class SameAsChainOutputTypeResolver implements OutputTypeResolver<Void> {

  @Override
  public String getCategoryName() {
    return "OUTPUT_SCOPE_DYNAMIC";
  }

  @Override
  public MetadataType getOutputType(MetadataContext context, Void key) throws MetadataResolvingException, ConnectionException {
    return context.getInnerChainOutputType()
        .orElseThrow(() -> new MetadataResolvingException("Invalid Chain output.", INVALID_CONFIGURATION)).get();
  }
}
