/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.privileged.metadata;

import org.mule.metadata.message.api.el.TypeBindings;
import org.mule.runtime.api.metadata.ExpressionLanguageMetadataService;
import org.mule.runtime.api.metadata.MetadataContext;

import java.util.Optional;

/**
 * A {@link MetadataContext} with additional capabilities, for internal use only.
 *
 * @since 4.8.0
 */
public interface InternalMetadataContext extends MetadataContext {

  /**
   * @return the {@link ExpressionLanguageMetadataService}, if available.
   */
  Optional<ExpressionLanguageMetadataService> getExpressionLanguageMetadataService();

  /**
   * @return the {@link TypeBindings} in context, if available.
   */
  Optional<TypeBindings> getTypeBindings();

}
