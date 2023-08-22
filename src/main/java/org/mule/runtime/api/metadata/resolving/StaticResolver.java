/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.metadata.resolving;

import org.mule.metadata.api.model.MetadataType;
import org.mule.runtime.api.connection.ConnectionException;
import org.mule.runtime.api.meta.Typed;
import org.mule.runtime.api.metadata.MetadataContext;
import org.mule.runtime.api.metadata.MetadataResolvingException;

/**
 * Handles the construction of a custom {@link MetadataType} in any format to be set in a {@link Typed} component statically when
 * building the extension avoiding dynamically metadata resolution when we know exactly the non necessarily java type structure at
 * compile time.
 * <p>
 * Provided implementations of this interfaces should be used and not this one directly.
 *
 * @since 1.1
 */
public interface StaticResolver extends NamedTypeResolver {

  String STATIC_RESOLVER_NAME = "STATIC_RESOLVER";

  /**
   * Creates a new {@link MetadataType}
   *
   * @return a new {@link MetadataType} to be set in a {@link Typed} component.
   */
  MetadataType getStaticMetadata();
}
