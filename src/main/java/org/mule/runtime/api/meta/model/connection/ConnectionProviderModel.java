/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model.connection;


import org.mule.api.annotation.NoImplement;
import org.mule.runtime.api.connection.ConnectionProvider;
import org.mule.runtime.api.meta.DescribedObject;
import org.mule.runtime.api.meta.NamedObject;
import org.mule.runtime.api.meta.model.EnrichableModel;
import org.mule.runtime.api.meta.model.HasExternalLibraries;
import org.mule.runtime.api.meta.model.declaration.fluent.HasSemanticTerms;
import org.mule.runtime.api.meta.model.deprecated.DeprecableModel;
import org.mule.runtime.api.meta.model.display.HasDisplayModel;
import org.mule.runtime.api.meta.model.parameter.ParameterizedModel;
import org.mule.runtime.api.meta.model.stereotype.HasStereotypeModel;
import org.mule.runtime.api.meta.model.version.HasMinMuleVersion;

/**
 * Introspection model for {@link ConnectionProvider} types.
 * <p>
 * Provider models implement the flyweight pattern. This means that a given connectionProvider should only be represented by only
 * one instance of this class. Thus, if the same connectionProvider is contained by different {@link HasConnectionProviderModels}
 * instances, then each of those containers should reference the same connectionProvider model instance.
 * <p>
 *
 * @since 1.0
 */
@NoImplement
public interface ConnectionProviderModel
    extends NamedObject, DescribedObject, EnrichableModel, ParameterizedModel,
    HasDisplayModel, HasExternalLibraries, HasStereotypeModel, DeprecableModel,
    HasSemanticTerms, HasMinMuleVersion {

  /**
   * @return the type of connection management that the provider performs
   */
  ConnectionManagementType getConnectionManagementType();

  /**
   * @return whether this provider supports connectivity testing or not
   */
  boolean supportsConnectivityTesting();

  /**
   * @return whether this provider connections may participate in an XA transaction.
   * @since 1.10
   */
  default boolean supportsXa() {
    return false;
  }
}
