/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model;

import org.mule.api.annotation.NoImplement;
import org.mule.metadata.api.model.ObjectType;
import org.mule.runtime.api.artifact.ArtifactCoordinates;
import org.mule.runtime.api.meta.Category;
import org.mule.runtime.api.meta.DescribedObject;
import org.mule.runtime.api.meta.NamedObject;
import org.mule.runtime.api.meta.model.config.ConfigurationModel;
import org.mule.runtime.api.meta.model.connection.ConnectionProviderModel;
import org.mule.runtime.api.meta.model.connection.HasConnectionProviderModels;
import org.mule.runtime.api.meta.model.construct.HasConstructModels;
import org.mule.runtime.api.meta.model.deprecated.DeprecableModel;
import org.mule.runtime.api.meta.model.display.HasDisplayModel;
import org.mule.runtime.api.meta.model.error.ErrorModel;
import org.mule.runtime.api.meta.model.function.HasFunctionModels;
import org.mule.runtime.api.meta.model.notification.NotificationModel;
import org.mule.runtime.api.meta.model.operation.HasOperationModels;
import org.mule.runtime.api.meta.model.operation.OperationModel;
import org.mule.runtime.api.meta.model.source.HasSourceModels;
import org.mule.runtime.api.meta.model.source.SourceModel;
import org.mule.runtime.api.meta.model.version.HasMinMuleVersion;
import org.mule.runtime.api.meta.JavaVersion;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * An Extension that provides packaged functionality.
 * <p>
 * Extensions can augment a system by providing new features in the form of operations and connection providers. What makes the
 * extension model different from a class implementing a certain interface is the fact that extensions provide enough information
 * at runtime so that they can be used without prior knowledge, and can be both executed or integrated into tooling seamlessly.
 * <p>
 * An extension model is not a miscellaneous group of methods, but can be seen (and may be derived from) an object model. As such,
 * an extension will provide several ways to configure itself and will provide a set of operations that may be eventually
 * executed.
 * <p>
 * The extension model doesn't just map a JVM object model. Extensions provide richer metadata, and a dynamic execution model, but
 * more importantly they restrict the way operations are defined and used to a manageable subset that would deterministic data
 * flow analysis.
 * <p>
 * An extension doesn't define any predefined syntax, evaluation order or execution paradigm. The operations provided are expected
 * to be used as individual building blocks in a bigger system, hence the name <code>Extension</code>
 *
 * @since 1.0
 */
@NoImplement
public interface ExtensionModel
    extends NamedObject, DescribedObject, EnrichableModel, HasOperationModels, HasSourceModels, HasFunctionModels,
    HasConnectionProviderModels, HasDisplayModel, HasExternalLibraries, HasConstructModels, DeprecableModel, HasMinMuleVersion {

  /**
   * A simple name for this extension. Usually one or two simple words that describes the functionality. For example, for an
   * extension that performs validations it could be something like &quot;validation&quot;. For an FTP connector it could be
   * &quot;ftp&quot;. For a connector that accesses the google contacts API it could be &quot;google-contacts&quot;.
   * <p>
   * To follow the convention described above is important since the name has to be unique. The platform will use this name to
   * make resources available based on it. This attribute will be used in a convention over configuration pattern. It cannot
   * contain spaces
   */
  String getName();

  /**
   * Returns this extension's version.
   * <p>
   * The extension version is specified as a <a href=&quot;http://semver.org/&quot;>Semantic Versioning</a>.
   * <p>
   * Note that while an extension implements a specific version, nothing prevents several versions of the same extension to
   * coexists at runtime.
   * <p>
   *
   * @return the version associated with this extension
   */
  String getVersion();

  /**
   * Returns the {@link ConfigurationModel configurationModels} available for this extension. Each configuration is guaranteed to
   * have a unique name.
   * <p>
   * The first configuration is the preferred (default) one, the rest of the configurations are ordered alphabetically. If the
   * {@link ExtensionModel} contains any {@link OperationModel}, then at least one {@link ConfigurationModel} is required.
   * However, the existence of one or more {@link ConfigurationModel} doesn't require the presence of any {@link OperationModel}.
   * <p>
   *
   * @return an immutable {@link List} with the available {@link ConfigurationModel configurationModels}.
   */
  List<ConfigurationModel> getConfigurationModels();

  /**
   * Returns the {@link ConfigurationModel} that matches the given name.
   *
   * @param name case sensitive configuration name
   * @return an {@link Optional} {@link ConfigurationModel}
   */
  Optional<ConfigurationModel> getConfigurationModel(String name);

  /**
   * Returns a {@link List} of {@link OperationModel}s defined at the extension level.
   * <p>
   * When an operation is defined at this level, it means that such operation does not require nor accept a configuration.
   * <p>
   * Each operation is guaranteed to have a unique name which will not overlap with any {@link SourceModel} or
   * {@link ConnectionProviderModel} defined at any level.
   *
   * @return an immutable {@link List} of {@link OperationModel}
   */
  @Override
  List<OperationModel> getOperationModels();

  /**
   * Returns the {@link ConnectionProviderModel}s which will be available to every {@link ConfigurationModel} defined in
   * {@code this} extension.
   * <p>
   * It is valid to define an {@link ExtensionModel} which does nothing but exposing connection providers. It is not required for
   * the extension to also expose any {@link ConfigurationModel} or {@link OperationModel}.
   *
   * @return an immutable {@link List} of {@link ConnectionProviderModel}
   */
  @Override
  List<ConnectionProviderModel> getConnectionProviders();

  /**
   * Returns a {@link List} of {@link SourceModel}s defined at the extension level. When a source is defined at this level, it
   * means that such source does not require nor accept a configuration.
   * <p>
   * Each source is guaranteed to have a unique name which will not overlap with any {@link OperationModel} or
   * {@link ConnectionProviderModel} defined at any level.
   *
   * @return an immutable {@link List} of {@link SourceModel}
   */
  @Override
  List<SourceModel> getSourceModels();

  /**
   * @return an immutable {@link Set} with all the object types defined by this extension
   */
  Set<ObjectType> getTypes();

  /**
   * @return an immutable {@link Set} with the paths to all the resources exposed by this extension
   */
  Set<String> getResources();

  /**
   * @return an immutable {@link Set} with all the Java packages that should be exported as privileged API by this extension
   */
  Set<String> getPrivilegedPackages();

  /**
   * @return an immutable {@link Set} with all the artifact IDs that have access to the privileged API exported by this extension.
   *         Each artifact is defined using Maven's groupId:artifactId
   */
  Set<String> getPrivilegedArtifacts();

  /**
   * Returns the name of the extension's vendor This name is used to:
   * <ul>
   * <li>Represent the extension's vendor</li>
   * <li>Differentiate different extensions with the same name, to give the possibility of having two connectors with the same
   * name, for example, one made by MuleSoft and the second one by a third party</li>
   * </ul>
   *
   * @return the name of the extension's vendor
   */
  String getVendor();

  /**
   * Returns the extension's {@link Category} that identifies the extension.
   *
   * @return the extension's {@link Category}
   * @see Category
   */
  Category getCategory();

  /**
   * @return the {@link XmlDslModel} which describes the language which allows using the extension
   */
  XmlDslModel getXmlDslModel();

  /**
   * @return A {@link Set} of {@link SubTypesModel} which describes the subtypes defined by this extension
   */
  Set<SubTypesModel> getSubTypes();

  /**
   * @return A {@link Set} of {@link ImportedTypeModel} which describes the types that are imported by this extension
   */
  Set<ImportedTypeModel> getImportedTypes();

  /**
   * @return A {@link Set} of {@link ErrorModel} registered in the extension.
   */
  Set<ErrorModel> getErrorModels();

  /**
   * @return A {@link Set} of {@link NotificationModel} registered in the extension.
   * @since 1.1
   */
  Set<NotificationModel> getNotificationModels();

  /**
   * Looks for a {@link ComponentModel} of the given {@code componentName} all through the extension model.
   *
   * @param componentName the name of the component being looked for
   * @return an {@link Optional} {@link ComponentModel}
   * @since 1.4.0
   */
  Optional<ComponentModel> findComponentModel(String componentName);

  /**
   * @return a {@link ArtifactCoordinates} if the extension model belongs to a mule plugin or empty if not.
   */
  Optional<ArtifactCoordinates> getArtifactCoordinates();

  Set<JavaVersion> getSupportedJavaVersions();
}
