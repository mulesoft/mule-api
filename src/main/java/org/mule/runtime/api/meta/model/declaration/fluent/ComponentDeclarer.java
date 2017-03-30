/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model.declaration.fluent;

import org.mule.runtime.api.meta.model.ModelProperty;
import org.mule.runtime.api.meta.model.Stereotype;

/**
 * Allows configuring a {@link ComponentDeclaration} through a fluent API
 *
 * @since 1.0
 */
public abstract class ComponentDeclarer<T extends ComponentDeclarer, D extends ComponentDeclaration>
    extends ConfigurableOutputDeclarer<D> implements HasModelProperties<ComponentDeclarer> {

  /**
   * Creates a new instance
   *
   * @param declaration the {@link ComponentDeclaration} which will be configured
   */
  public ComponentDeclarer(D declaration) {
    super(declaration);
  }

  /**
   * Adds a description
   *
   * @param description a description
   * @return {@code this} declarer
   */
  public T describedAs(String description) {
    declaration.setDescription(description);
    return (T) this;
  }

  /**
   * Specifies if this component has the ability to execute while joining a transaction
   *
   * @param transactional whether the component is transactional or not
   * @return {@code this} declarer
   */
  public T transactional(boolean transactional) {
    declaration.setTransactional(transactional);
    return (T) this;
  }

  /**
   * Specifies if this component requires a connection in order to perform its task
   *
   * @param requiresConnection whether the component requires a connection or not
   * @return {@code this} declarer
   */
  public T requiresConnection(boolean requiresConnection) {
    declaration.setRequiresConnection(requiresConnection);
    return (T) this;
  }

  /**
   * Specifies if this component supports streaming. Notice that supporting streaming
   * doesn't necessarily mean that streaming will be performed when the component
   * is actually executed.
   *
   * @param supportsStreaming whether the component supports streaming or not
   * @return {@code this} declarer
   */
  public T supportsStreaming(boolean supportsStreaming) {
    declaration.setSupportsStreaming(supportsStreaming);
    return (T) this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public T withModelProperty(ModelProperty modelProperty) {
    declaration.addModelProperty(modelProperty);
    return (T) this;
  }

  /**
   * Adds the given {@code stereotype}
   *
   * @param stereotype a {@link Stereotype}
   * @return {@code this} declarer
   */
  public T withStereotype(Stereotype stereotype) {
    declaration.addStereotype(stereotype);
    return (T) this;
  }
}
