/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.internal.dsl;

import static java.util.Collections.unmodifiableSet;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toMap;

import org.mule.runtime.api.dsl.DslResolvingContext;
import org.mule.runtime.api.meta.model.ExtensionModel;
import org.mule.runtime.api.meta.type.TypeCatalog;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * Default implementation of {@link DslResolvingContext} that uses the {@link Set} of {@link ExtensionModel} to provide the
 * required {@link ExtensionModel}s
 *
 * @since 1.0
 */
public final class DefaultDslResolvingContext implements DslResolvingContext {

  private final TypeCatalog typeCatalog;
  private final Set<ExtensionModel> extensions;
  private final Map<String, ExtensionModel> extensionsByName;

  public DefaultDslResolvingContext(Set<ExtensionModel> extensions) {
    this.extensions = extensions;
    this.extensionsByName = extensions.stream().collect(toMap(ExtensionModel::getName, e -> e, (u, v) -> v, LinkedHashMap::new));
    this.typeCatalog = TypeCatalog.getDefault(extensions);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Optional<ExtensionModel> getExtension(String name) {
    return ofNullable(extensionsByName.get(name));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Optional<ExtensionModel> getExtensionForType(String typeId) {
    return typeCatalog.getDeclaringExtension(typeId).map(extensionsByName::get);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Set<ExtensionModel> getExtensions() {
    return unmodifiableSet(extensions);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public TypeCatalog getTypeCatalog() {
    return typeCatalog;
  }

  @Override
  public String toString() {
    return this.getClass().getSimpleName() + extensionsByName.keySet();
  }
}
