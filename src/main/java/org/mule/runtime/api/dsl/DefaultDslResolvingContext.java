/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.dsl;

import static java.util.Collections.unmodifiableCollection;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toMap;
import org.mule.runtime.api.meta.model.ExtensionModel;
import org.mule.runtime.api.meta.type.TypeCatalog;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * Default implementation of {@link DslResolvingContext} that uses the {@link Set} of {@link ExtensionModel} to provide
 * the required {@link ExtensionModel}s
 *
 * @since 4.0
 */
final class DefaultDslResolvingContext implements DslResolvingContext {

  private final Map<String, ExtensionModel> extensions;
  private final TypeCatalog typeCatalog;

  DefaultDslResolvingContext(Set<ExtensionModel> extensions) {
    this.extensions = extensions.stream().collect(toMap(ExtensionModel::getName, e -> e));
    this.typeCatalog = TypeCatalog.getDefault(extensions);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Optional<ExtensionModel> getExtension(String name) {
    return ofNullable(extensions.get(name));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Collection<ExtensionModel> getExtensions() {
    return unmodifiableCollection(extensions.values());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public TypeCatalog getTypeCatalog() {
    return typeCatalog;
  }
}
