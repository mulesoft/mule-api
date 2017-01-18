/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model.connection;


import org.mule.runtime.api.connection.ConnectionProvider;
import org.mule.runtime.api.meta.DescribedObject;
import org.mule.runtime.api.meta.NamedObject;
import org.mule.runtime.api.meta.model.EnrichableModel;
import org.mule.runtime.api.meta.model.HasExternalLibraries;
import org.mule.runtime.api.meta.model.display.HasDisplayModel;
import org.mule.runtime.api.meta.model.parameter.ParameterizedModel;

/**
 * Introspection model for {@link ConnectionProvider} types.
 * <p>
 * Provider models implement the flyweight pattern. This means
 * that a given operation should only be represented by only
 * one instance of this class. Thus, if the same operation is
 * contained by different {@link HasConnectionProviderModels} instances,
 * then each of those containers should reference the same
 * operation model instance.
 *
 * @since 1.0
 */
public interface ConnectionProviderModel
    extends NamedObject, DescribedObject, EnrichableModel, ParameterizedModel, HasDisplayModel, HasExternalLibraries {

  /**
   * @return the type of connection management that the provider performs
   */
  ConnectionManagementType getConnectionManagementType();
}
