/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.metadata.resolving;

import org.mule.metadata.api.builder.BaseTypeBuilder;
import org.mule.metadata.api.builder.UnionTypeBuilder;
import org.mule.metadata.api.model.MetadataType;
import org.mule.runtime.api.connection.ConnectionException;
import org.mule.runtime.api.metadata.MetadataContext;
import org.mule.runtime.api.metadata.MetadataResolvingException;

import static org.mule.metadata.api.model.MetadataFormat.JAVA;
import static org.mule.runtime.api.metadata.resolving.FailureCode.INVALID_CONFIGURATION;

public class UnionOfRoutesOutputTypeResolver implements OutputTypeResolver<Object> {

  @Override
  public String getCategoryName() {
    return "OUTPUT_ROUTER_DYNAMIC";
  }

  @Override
  public MetadataType getOutputType(MetadataContext context, Object key) throws MetadataResolvingException, ConnectionException {
    if (context.getInnerRoutesOutputType().isEmpty()) {
      throw new MetadataResolvingException("Invalid Routes output.", INVALID_CONFIGURATION);
    }
    UnionTypeBuilder builder = BaseTypeBuilder.create(JAVA).unionType();
    context.getInnerRoutesOutputType().values().forEach(metadataSupplier -> builder.of(metadataSupplier.get()));
    return builder.build();
  }
}
