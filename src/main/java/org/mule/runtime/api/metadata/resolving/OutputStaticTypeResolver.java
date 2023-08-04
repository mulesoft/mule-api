/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.metadata.resolving;

import org.mule.metadata.api.model.MetadataType;
import org.mule.runtime.api.connection.ConnectionException;
import org.mule.runtime.api.meta.model.OutputModel;
import org.mule.runtime.api.metadata.MetadataContext;
import org.mule.runtime.api.metadata.MetadataResolvingException;

/**
 * {@link StaticResolver} implementation for the {@link OutputModel}s.
 * <p>
 * This {@link OutputTypeResolver} is discarded once the extension model is built. It's not registered with the dynamic
 * {@link OutputTypeResolver}s.
 *
 * @since 1.1
 */
public abstract class OutputStaticTypeResolver implements OutputTypeResolver, StaticResolver {

  /**
   * {@inheritDoc}
   * <p>
   * The category name is not used for {@link StaticResolver}s since they are not registered in the extension.
   */
  @Override
  public String getCategoryName() {
    return "OUTPUT_" + STATIC_RESOLVER_NAME;
  }

  /**
   * This method resolves the static output type for a {@link OutputModel} proxing to the
   * {@link StaticResolver#getStaticMetadata()} method.
   * <p>
   * The context and key are not used nor injected.
   */
  @Override
  final public MetadataType getOutputType(MetadataContext context, Object key) {
    return getStaticMetadata();
  }
}
