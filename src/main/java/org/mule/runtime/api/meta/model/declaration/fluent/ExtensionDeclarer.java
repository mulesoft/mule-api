/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model.declaration.fluent;

import static java.util.Arrays.asList;
import static org.mule.metadata.api.utils.MetadataTypeUtils.getTypeId;
import org.mule.metadata.api.model.MetadataType;
import org.mule.metadata.api.model.ObjectType;
import org.mule.runtime.api.meta.Category;
import org.mule.runtime.api.meta.MuleVersion;
import org.mule.runtime.api.meta.model.ExternalLibraryModel;
import org.mule.runtime.api.meta.model.ImportedTypeModel;
import org.mule.runtime.api.meta.model.ModelProperty;
import org.mule.runtime.api.meta.model.XmlDslModel;
import org.mule.runtime.api.meta.model.error.ErrorModel;

import java.util.Collection;
import java.util.List;

/**
 * A builder object which allows creating an {@link ExtensionDeclaration}
 * through a fluent API.
 *
 * @since 1.0
 */
public class ExtensionDeclarer extends Declarer<ExtensionDeclaration>
    implements HasModelProperties<ExtensionDeclarer>, HasOperationDeclarer,
    HasConnectionProviderDeclarer, HasSourceDeclarer, DeclaresExternalLibraries<ExtensionDeclarer> {

  private static final List<String> UNREGISTERED_PACKAGES =
      asList("java.", "javax.", "com.mulesoft.mule.runtime.", "org.mule.runtime.", "com.sun.");

  /**
   * Constructor for this descriptor
   */
  public ExtensionDeclarer() {
    super(new ExtensionDeclaration());
  }

  /**
   * Provides the extension's name
   *
   * @param name the extension's name
   * @return {@code this} descriptor
   */
  public ExtensionDeclarer named(String name) {
    declaration.setName(name);
    return this;
  }

  /**
   * Provides the extension's version
   *
   * @param version the extensions version
   * @return {@code this} descriptor
   */
  public ExtensionDeclarer onVersion(String version) {
    declaration.setVersion(version);
    return this;
  }

  /**
   * Adds a description
   *
   * @param description a description
   * @return {@code this} descriptor
   */
  public ExtensionDeclarer describedAs(String description) {
    declaration.setDescription(description);
    return this;
  }

  /**
   * Adds a config of the given name
   *
   * @param name a non blank name
   * @return a {@link ConfigurationDeclarer} which allows describing the created configuration
   */
  public ConfigurationDeclarer withConfig(String name) {
    ConfigurationDeclaration config = new ConfigurationDeclaration(name);
    declaration.addConfig(config);

    return new ConfigurationDeclarer(config);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ConnectionProviderDeclarer withConnectionProvider(String name) {
    ConnectionProviderDeclaration declaration = new ConnectionProviderDeclaration(name);

    final ConnectionProviderDeclarer connectionProviderDeclarer = new ConnectionProviderDeclarer(declaration);
    withConnectionProvider(connectionProviderDeclarer);

    return connectionProviderDeclarer;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void withConnectionProvider(ConnectionProviderDeclarer declarer) {
    declaration.addConnectionProvider(declarer.getDeclaration());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public OperationDeclarer withOperation(String name) {
    OperationDeclaration operation = new OperationDeclaration(name);
    final OperationDeclarer operationDeclarer = new OperationDeclarer(operation);
    withOperation(operationDeclarer);

    return operationDeclarer;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void withOperation(OperationDeclarer declarer) {
    declaration.addOperation(declarer.getDeclaration());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ScopeDeclarer withScope(String name) {
    ScopeDeclaration scope = new ScopeDeclaration(name);
    final ScopeDeclarer scopeDeclarer = new ScopeDeclarer(scope);
    withScope(scopeDeclarer);

    return scopeDeclarer;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void withScope(ScopeDeclarer declarer) {
    declaration.addOperation(declarer.getDeclaration());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public RouterDeclarer withRouter(String name) {
    RouterDeclaration router = new RouterDeclaration(name);
    final RouterDeclarer routerDeclarer = new RouterDeclarer(router);
    withRouter(routerDeclarer);

    return routerDeclarer;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void withRouter(RouterDeclarer declarer) {
    declaration.addOperation(declarer.getDeclaration());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public SourceDeclarer withMessageSource(String name) {
    SourceDeclaration declaration = new SourceDeclaration(name);

    final SourceDeclarer sourceDeclarer = new SourceDeclarer(declaration);
    withMessageSource(sourceDeclarer);

    return sourceDeclarer;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void withMessageSource(SourceDeclarer declarer) {
    declaration.addMessageSource(declarer.getDeclaration());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ExtensionDeclarer withModelProperty(ModelProperty value) {
    declaration.addModelProperty(value);
    return this;
  }

  /**
   * Adds the given {@code objectType} to the list of types declared
   * by the extension being built.
   *
   * @param objectType an {@link ObjectType}
   * @return {@code this} declarer
   */
  public ExtensionDeclarer withType(ObjectType objectType) {
    if (isTypeRegistrable(objectType)) {
      declaration.addType(objectType);
    }

    return this;
  }

  /**
   * Adds the given {@code resourcePath} to the list of resources
   * declared by the extension being built
   *
   * @param resourcePath the relative path to the extension's resource
   * @return {@code this} declarer
   */
  public ExtensionDeclarer withResource(String resourcePath) {
    declaration.addResource(resourcePath);
    return this;
  }

  /**
   * Declares that the extension is importing a type from another
   * extension
   *
   * @param importedType a {@link ImportedTypeModel} with the import information
   * @return {@code this} declarer
   */
  public ExtensionDeclarer withImportedType(ImportedTypeModel importedType) {
    declaration.addImportedType(importedType);
    return this;
  }

  /**
   * Describes the language which allows using the extension
   *
   * @param xmlDslModel an {@link XmlDslModel}
   * @return {@code this} declarer
   */
  public ExtensionDeclarer withXmlDsl(XmlDslModel xmlDslModel) {
    declaration.setXmlDslModel(xmlDslModel);
    return this;
  }

  /**
   * Registers the given {@code subType} as an implementation of the {@code baseType}
   *
   * @param baseType a base type
   * @param subType  a sub type implementation
   * @return {@code this} declarer
   */
  public ExtensionDeclarer withSubType(MetadataType baseType, MetadataType subType) {
    declaration.addSubtype(baseType, subType);
    return this;
  }

  /**
   * Registers the given {@code subTypes} as implementations of the {@code baseType}
   *
   * @param baseType a base type
   * @param subTypes a {@link Collection} of sub type implementations
   * @return {@code this} declarer
   */
  public ExtensionDeclarer withSubTypes(MetadataType baseType, Collection<MetadataType> subTypes) {
    declaration.addSubtypes(baseType, subTypes);
    return this;
  }

  private boolean isTypeRegistrable(ObjectType objectType) {
    String typeId = getTypeId(objectType).orElse(null);
    return typeId != null && !typeId.trim().isEmpty() && UNREGISTERED_PACKAGES.stream().noneMatch(typeId::startsWith);
  }

  /**
   * Adds the extension's Vendor name
   *
   * @param vendor name
   * @return {@code this} descriptor
   */
  public ExtensionDeclarer fromVendor(String vendor) {
    declaration.setVendor(vendor);
    return this;
  }

  /**
   * Adds the extension's {@link Category}
   *
   * @param category of the extension
   * @return {@code this} descriptor
   */
  public ExtensionDeclarer withCategory(Category category) {
    declaration.setCategory(category);
    return this;
  }

  /**
   * Adds the extension's minimum Mule Version
   *
   * @param minMuleVersion of the extension
   * @return {@code this} descriptor
   */
  public ExtensionDeclarer withMinMuleVersion(MuleVersion minMuleVersion) {
    declaration.setMinMuleVersion(minMuleVersion);
    return this;
  }

  /**
   * Registers an {@link ErrorModel} that could be thrown by one their
   * operations
   *
   * @param errorModel to add
   * @return {@code this} descriptor
   */
  public ExtensionDeclarer withErrorModel(ErrorModel errorModel) {
    declaration.addErrorModel(errorModel);
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ExtensionDeclarer withExternalLibrary(ExternalLibraryModel externalLibrary) {
    declaration.addExternalLibrary(externalLibrary);
    return this;
  }
}
