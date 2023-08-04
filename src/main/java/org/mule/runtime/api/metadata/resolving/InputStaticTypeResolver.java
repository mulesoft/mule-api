/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.metadata.resolving;

import org.mule.api.annotation.NoExtend;
import org.mule.metadata.api.model.MetadataType;
import org.mule.runtime.api.meta.model.parameter.ParameterModel;
import org.mule.runtime.api.metadata.MetadataContext;

/**
 * {@link StaticResolver} implementation for the {@link ParameterModel}s.
 * <p>
 * This {@link InputTypeResolver} is discarded once the extension model is built. It's not registered with the dynamic
 * {@link InputTypeResolver}s.
 *
 * @since 1.1
 */
@NoExtend
public abstract class InputStaticTypeResolver implements InputTypeResolver, StaticResolver {

  /**
   * {@inheritDoc}
   * <p>
   * The category name is not used for {@link StaticResolver}s since they are not registered in the extension.
   */
  @Override
  public String getCategoryName() {
    return "INPUT_" + STATIC_RESOLVER_NAME;
  }

  /**
   * This method resolves the static input type for a {@link ParameterModel} proxing to the
   * {@link StaticResolver#getStaticMetadata()} method.
   * <p>
   * The context and key are not used nor injected.
   */
  @Override
  final public MetadataType getInputMetadata(MetadataContext context, Object key) {
    return getStaticMetadata();
  }
}
