/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model.declaration.fluent;

import static org.mule.metadata.api.utils.MetadataTypeUtils.getTypeId;
import static org.mule.runtime.api.meta.JavaVersion.JAVA_11;
import static org.mule.runtime.api.meta.JavaVersion.JAVA_8;
import static org.mule.runtime.api.util.Preconditions.checkArgument;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static java.util.Collections.sort;
import static java.util.Collections.unmodifiableList;
import static java.util.Collections.unmodifiableSet;
import static java.util.Comparator.comparing;
import static java.util.Comparator.naturalOrder;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toCollection;

import static org.apache.commons.lang3.StringUtils.isBlank;

import org.mule.metadata.api.model.MetadataType;
import org.mule.metadata.api.model.ObjectType;
import org.mule.runtime.api.artifact.ArtifactCoordinates;
import org.mule.runtime.api.meta.Category;
import org.mule.runtime.api.meta.JavaVersion;
import org.mule.runtime.api.meta.MuleVersion;
import org.mule.runtime.api.meta.model.ExtensionModel;
import org.mule.runtime.api.meta.model.ExternalLibraryModel;
import org.mule.runtime.api.meta.model.ImportedTypeModel;
import org.mule.runtime.api.meta.model.SubTypesModel;
import org.mule.runtime.api.meta.model.XmlDslModel;
import org.mule.runtime.api.meta.model.deprecated.DeprecationModel;
import org.mule.runtime.api.meta.model.error.ErrorModel;
import org.mule.runtime.api.meta.model.notification.NotificationModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;


/**
 * A declaration object for a {@link ExtensionModel}. It contains raw, unvalidated data which is used to declare the structure of
 * a {@link ExtensionModel}
 *
 * @since 1.0
 */
public class ExtensionDeclaration extends NamedDeclaration<ExtensionDeclaration>
    implements ConnectedDeclaration<ExtensionDeclaration>, WithSourcesDeclaration<ExtensionDeclaration>,
    WithOperationsDeclaration<ExtensionDeclaration>, WithFunctionsDeclaration<ExtensionDeclaration>,
    WithConstructsDeclaration<ExtensionDeclaration>, WithDeprecatedDeclaration, WithArtifactCoordinatesDeclaration,
    WithMinMuleVersionDeclaration {

  private static final Set<JavaVersion> DEFAULT_SUPPORTED_JAVA_VERSIONS =
      unmodifiableSet(new LinkedHashSet<>(asList(JAVA_8, JAVA_11)));

  private final SubDeclarationsContainer subDeclarations = new SubDeclarationsContainer();
  private final List<ConfigurationDeclaration> configurations = new LinkedList<>();
  private final Set<ImportedTypeModel> importedTypes = new TreeSet<>(comparing(t -> getTypeId(t.getImportedType()).orElse("")));
  private final Set<ExternalLibraryModel> externalLibraryModels = new TreeSet<>(comparing(ExternalLibraryModel::getName));
  private final Map<String, ObjectType> typesById = new TreeMap<>();
  private final Set<String> privilegedPackages = new TreeSet<>(naturalOrder());
  private final Set<String> privilegedArtifacts = new TreeSet<>(naturalOrder());
  private final Set<String> resources = new TreeSet<>(naturalOrder());
  private final Set<ErrorModel> errorModels = new LinkedHashSet<>();
  private final Set<NotificationModel> notificationModels = new LinkedHashSet<>();
  private String name;
  private String version;
  private String vendor;
  private Category category;
  private XmlDslModel xmlDslModel;
  private final Map<MetadataType, Set<MetadataType>> subTypes = new LinkedHashMap<>();
  private DeprecationModel deprecation;
  private ArtifactCoordinates artifactCoordinates;
  private MuleVersion minMuleVersion;
  private Set<JavaVersion> supportedJavaVersions = DEFAULT_SUPPORTED_JAVA_VERSIONS;

  /**
   * Creates a new instance
   */
  ExtensionDeclaration() {
    super("");
  }

  /**
   * Returns an immutable list with the {@link ConfigurationDeclaration} instances that have been declared so far.
   *
   * @return an unmodifiable list. May be empty but will never be {@code null}
   */
  public List<ConfigurationDeclaration> getConfigurations() {
    ArrayList<ConfigurationDeclaration> list = new ArrayList<>(configurations);
    sort(list, comparing(c -> c.getName()));
    return unmodifiableList(list);
  }

  /**
   * Adds a {@link ConfigurationDeclaration}
   *
   * @param config a not {@code null} {@link ConfigurationDeclaration}
   * @return this declaration
   * @throws {@link IllegalArgumentException} if {@code config} is {@code null}
   */
  public ExtensionDeclaration addConfig(ConfigurationDeclaration config) {
    if (config == null) {
      throw new IllegalArgumentException("Can't add a null config");
    }

    configurations.add(config);
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<ConstructDeclaration> getConstructs() {
    return subDeclarations.getConstructs();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ExtensionDeclaration addConstruct(ConstructDeclaration declaration) {
    subDeclarations.addConstruct(declaration);
    return this;
  }

  /**
   * @return an unmodifiable {@link List} with the available {@link OperationDeclaration}s
   */
  @Override
  public List<OperationDeclaration> getOperations() {
    return subDeclarations.getOperations();
  }

  /**
   * @return an unmodifiable {@link List} with the available {@link ConnectionProviderDeclaration}s
   */
  @Override
  public List<ConnectionProviderDeclaration> getConnectionProviders() {
    return subDeclarations.getConnectionProviders();
  }

  /**
   * @return an unmodifiable {@link List} with the available {@link SourceDeclaration}s
   */
  @Override
  public List<SourceDeclaration> getMessageSources() {
    return subDeclarations.getMessageSources();
  }

  /**
   * Adds a {@link ConnectionProviderDeclaration}
   *
   * @param connectionProvider a not {@code null} {@link ConnectionProviderDeclaration}
   * @return {@code this} declaration
   * @throws IllegalArgumentException if {@code connectionProvider} is {@code null}
   */
  @Override
  public ExtensionDeclaration addConnectionProvider(ConnectionProviderDeclaration connectionProvider) {
    subDeclarations.addConnectionProvider(connectionProvider);
    return this;
  }

  /**
   * Adds a {@link OperationDeclaration}
   *
   * @param operation a not {@code null} {@link OperationDeclaration}
   * @return {@code this} declaration
   * @throws {@link IllegalArgumentException} if {@code operation} is {@code null}
   */
  @Override
  public ExtensionDeclaration addOperation(OperationDeclaration operation) {
    subDeclarations.addOperation(operation);
    return this;
  }

  /**
   * Adds a {@link SourceDeclaration}
   *
   * @param sourceDeclaration a not {@code null} {@link SourceDeclaration}
   * @return {@code this} declaration
   * @throws {@link IllegalArgumentException} if {@code sourceDeclaration} is {@code null}
   */
  @Override
  public ExtensionDeclaration addMessageSource(SourceDeclaration sourceDeclaration) {
    subDeclarations.addMessageSource(sourceDeclaration);
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ExtensionDeclaration addFunction(FunctionDeclaration function) {
    subDeclarations.addFunction(function);
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<FunctionDeclaration> getFunctions() {
    return subDeclarations.getFunctions();
  }

  /**
   * @return an immutable {@link Set} with all the types registered through {@link #getTypes()}
   */
  public Set<ObjectType> getTypes() {
    final Set<ObjectType> types = new TreeSet<>(comparing(t -> getTypeId(t).orElse("")));
    types.addAll(typesById.values());
    return unmodifiableSet(types);
  }

  /**
   * @param typeId the id of the type to get.
   * @return a type present in this declaration with the given {@code typeId}
   */
  public ObjectType getTypeById(String typeId) {
    return typesById.get(typeId);
  }

  /**
   * @return an immutable {@link Set} with the paths to all of the resources exposed by the declared extension
   */
  public Set<String> getResources() {
    return unmodifiableSet(resources);
  }

  /**
   * @return an immutable {@link Set} with all the Java package name that are registered as privileged API
   */
  public Set<String> getPrivilegedPackages() {
    return unmodifiableSet(privilegedPackages);
  }

  /**
   * @return an immutable {@link Set} with all the artifact IDs that are registered to have access to the privileged API. Each
   *         artifact is defined using Maven's groupId:artifactId
   */
  public Set<String> getPrivilegedArtifacts() {
    return unmodifiableSet(privilegedArtifacts);
  }

  /**
   * Declares that this extension defined the given {@code objectType}
   *
   * @param objectType an {@link ObjectType}
   * @return {@code this} declaration
   */
  public ExtensionDeclaration addType(ObjectType objectType) {
    getTypeId(objectType).ifPresent(typeId -> {
      if (!typesById.containsKey(typeId)) {
        typesById.put(typeId, objectType);
      }
    });
    return this;
  }

  /**
   * Declares that this extension contains a resource which can be found at the relative {@code resourcePath}
   *
   * @param resourcePath the relative path to the extension's resource
   * @return {@code this} declaration
   */
  public ExtensionDeclaration addResource(String resourcePath) {
    resources.add(resourcePath);
    return this;
  }

  /**
   * Declares that this extension declares a privileged API exporting a Java package
   *
   * @param packageName the Java package name to be exported
   * @return {@code this} declaration
   */
  public ExtensionDeclaration addPrivilegedPackage(String packageName) {
    checkArgument(!isBlank(packageName), "packageName cannot be blank");
    privilegedPackages.add(packageName);
    return this;
  }

  /**
   * Declares that this extension declares a privileged API accessible by a given artifact
   *
   * @param artifactId the artifactId that the extension provides access to the privileged API
   * @return {@code this} declaration
   */
  public ExtensionDeclaration addPrivilegedArtifact(String artifactId) {
    checkArgument(!isBlank(artifactId), "artifactId cannot be blank");
    privilegedArtifacts.add(artifactId);
    return this;
  }

  /**
   * Declares that the extension is importing a type from another extension
   *
   * @param importedType a {@link ImportedTypeModel} with the import information
   * @return {@code this} declaration
   */
  public ExtensionDeclaration addImportedType(ImportedTypeModel importedType) {
    importedTypes.add(importedType);
    return this;
  }

  /**
   * Adds an {@link ExternalLibraryModel}
   *
   * @param externalLibraryModel the model of the external library to be referenced
   * @return {@code this} declarer
   */
  public ExtensionDeclaration addExternalLibrary(ExternalLibraryModel externalLibraryModel) {
    externalLibraryModels.add(externalLibraryModel);
    return this;
  }

  /**
   * Registers the given {@code subType} as an implementation of the {@code baseType}
   *
   * @param baseType a base type
   * @param subType  a sub type implementation
   */
  public void addSubtype(MetadataType baseType, MetadataType subType) {
    addSubtypes(baseType, singletonList(subType));
  }

  /**
   * Registers the given {@code subTypes} as implementations of the {@code baseType}
   *
   * @param baseType a base type
   * @param subTypes a {@link Collection} of sub type implementations
   */
  public void addSubtypes(MetadataType baseType, Collection<MetadataType> subTypes) {
    Set<MetadataType> items = this.subTypes.computeIfAbsent(baseType, key -> new LinkedHashSet<>());
    items.addAll(subTypes);
  }

  /**
   * @return a {@link Map} with the subType mappings declared through {@link #addSubtype(MetadataType, MetadataType)} and
   *         {@link #addSubtypes(MetadataType, Collection)}
   */
  public Set<SubTypesModel> getSubTypes() {
    return subTypes.entrySet().stream().map(entry -> new SubTypesModel(entry.getKey(), entry.getValue()))
        .collect(toCollection(LinkedHashSet::new));
  }

  /**
   * @return A {@link Set} of {@link ExternalLibraryModel} which represent the extension's external libraries
   */
  public Set<ExternalLibraryModel> getExternalLibraryModels() {
    return externalLibraryModels;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getVersion() {
    return version;
  }

  void setVersion(String version) {
    this.version = version;
  }

  public String getVendor() {
    return vendor;
  }

  public void setVendor(String vendor) {
    this.vendor = vendor;
  }

  public Category getCategory() {
    return this.category;
  }

  public void setCategory(Category category) {
    this.category = category;
  }

  public XmlDslModel getXmlDslModel() {
    return xmlDslModel;
  }

  public void setXmlDslModel(XmlDslModel xmlDslModel) {
    this.xmlDslModel = xmlDslModel;
  }

  public Set<ImportedTypeModel> getImportedTypes() {
    return importedTypes;
  }

  public void addErrorModel(ErrorModel errorModel) {
    errorModels.add(errorModel);
  }

  public Set<ErrorModel> getErrorModels() {
    return errorModels;
  }

  public void addNotificationModel(NotificationModel notificationModel) {
    notificationModels.add(notificationModel);
  }

  public Set<NotificationModel> getNotificationModels() {
    return notificationModels;
  }

  @Override
  public Optional<DeprecationModel> getDeprecation() {
    return ofNullable(deprecation);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void withDeprecation(DeprecationModel deprecation) {
    this.deprecation = deprecation;
  }

  @Override
  public Optional<ArtifactCoordinates> getArtifactCoordinates() {
    return ofNullable(artifactCoordinates);
  }

  @Override
  public void withArtifactCoordinates(ArtifactCoordinates artifactCoordinates) {
    this.artifactCoordinates = artifactCoordinates;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Optional<MuleVersion> getMinMuleVersion() {
    return ofNullable(minMuleVersion);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void withMinMuleVersion(MuleVersion minMuleVersion) {
    this.minMuleVersion = minMuleVersion;
  }

  public Set<JavaVersion> getSupportedJavaVersions() {
    return supportedJavaVersions;
  }

  public void setSupportedJavaVersions(Set<JavaVersion> supportedJavaVersions) {
    if (supportedJavaVersions != null && !supportedJavaVersions.isEmpty()) {
      this.supportedJavaVersions = unmodifiableSet(new LinkedHashSet<>(supportedJavaVersions));
    } else {
      this.supportedJavaVersions = DEFAULT_SUPPORTED_JAVA_VERSIONS;
    }
  }
}
