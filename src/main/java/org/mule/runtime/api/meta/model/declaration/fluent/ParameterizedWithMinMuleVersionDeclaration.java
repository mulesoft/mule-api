/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model.declaration.fluent;

import static java.util.Optional.ofNullable;

import org.mule.runtime.api.meta.MuleVersion;
import org.mule.runtime.api.meta.version.MuleMinorVersion;

import java.util.Optional;


/**
 * Base class for a declaration of a named object that has a minimum version required for its usage.
 * <p>
 *
 * @param <T> the concrete type for {@code this} declaration
 * @since 1.5
 */
public abstract class ParameterizedWithMinMuleVersionDeclaration<T extends ParameterizedWithMinMuleVersionDeclaration>
    extends ParameterizedDeclaration<T>
    implements WithMinMuleVersionDeclaration {

  private MuleVersion minMuleVersion;

  /**
   * Creates a new instance
   *
   * @param name the name of the component being declared
   */
  public ParameterizedWithMinMuleVersionDeclaration(String name) {
    super(name);
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

  @Override
  public void withMinMuleVersion(MuleMinorVersion minMuleVersion) {
    withMinMuleVersion(new MuleVersion(minMuleVersion.toString() + ".0"));
  }

}
