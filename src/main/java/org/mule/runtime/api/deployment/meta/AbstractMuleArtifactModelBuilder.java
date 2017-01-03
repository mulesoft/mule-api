/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.runtime.api.deployment.meta;

/**
 * A builder to create instances of {@link AbstractMuleArtifactModel} implementations.
 *
 * @param <T> type of the builder, used to enable builder pattern on subclasses methods
 * @param <M> type of the model being built, used to have type safety
 *
 * @since 1.0
 */
public abstract class AbstractMuleArtifactModelBuilder<T extends AbstractMuleArtifactModelBuilder, M extends AbstractMuleArtifactModel> {

  private String name;
  private String minMuleVersion;

  /**
   * Sets the describer's name
   *
   * @param name the name the describer will have
   * @return {@code this} builder
   */
  public T setName(String name) {
    this.name = name;

    return getThis();
  }

  public String getName() {
    return name;
  }

  /**
   * @return the parametrized type of the builder
   */
  protected abstract T getThis();

  /**
   * Sets the describer's minimum Mule Runtime version that requires to work correctly
   *
   * @param muleVersion of the describer
   * @return {@code this} builder
   */
  public T setMinMuleVersion(String muleVersion) {
    this.minMuleVersion = muleVersion;
    return getThis();
  }

  public String getMinMuleVersion() {
    return minMuleVersion;
  }

  /**
   * @return a well formed model of {@link M} type
   */
  public abstract M build();
}
