/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.meta.model.config;

import org.mule.api.annotation.NoImplement;
import org.mule.runtime.api.meta.DescribedObject;
import org.mule.runtime.api.meta.NamedObject;
import org.mule.runtime.api.meta.model.EnrichableModel;
import org.mule.runtime.api.meta.model.ExtensionModel;
import org.mule.runtime.api.meta.model.HasExternalLibraries;
import org.mule.runtime.api.meta.model.connection.ConnectionProviderModel;
import org.mule.runtime.api.meta.model.connection.HasConnectionProviderModels;
import org.mule.runtime.api.meta.model.deprecated.DeprecableModel;
import org.mule.runtime.api.meta.model.display.HasDisplayModel;
import org.mule.runtime.api.meta.model.operation.HasOperationModels;
import org.mule.runtime.api.meta.model.operation.OperationModel;
import org.mule.runtime.api.meta.model.parameter.ParameterizedModel;
import org.mule.runtime.api.meta.model.source.HasSourceModels;
import org.mule.runtime.api.meta.model.source.SourceModel;
import org.mule.runtime.api.meta.model.stereotype.HasStereotypeModel;
import org.mule.runtime.api.meta.model.version.HasMinMuleVersion;

/**
 * A named configuration for an extension
 * <p/>
 * Configurations describe different ways to initialize a scope for operations. Upon execution, each operation will be associated
 * to a given configuration, so configurations define both a set of shared properties used in operations, and a common context to
 * relate operations.
 * <p/>
 * The configuration can also imply different implicit behaviors not strictly attached to the operations
 * <p/>
 * The configuration is also the place in which cross operation, extension level attributes are configured. Every
 * {@link ExtensionModel} is required to have at least one {@link ConfigurationModel}. That {@link ConfigurationModel} is defined
 * as the &quot;default configuration&quot;
 * <p>
 * Although the {@link SourceModel}s, {@link OperationModel}s and {@link ConnectionProviderModel}s defined at the extension level
 * are available to every single config, configs can also define its own set of those which are exclusive to them.
 *
 * @since 1.0
 */
@NoImplement
public interface ConfigurationModel extends NamedObject, DescribedObject, EnrichableModel, ParameterizedModel,
    HasOperationModels, HasSourceModels, HasConnectionProviderModels, HasDisplayModel,
    HasExternalLibraries, HasStereotypeModel, DeprecableModel, HasMinMuleVersion {

}
