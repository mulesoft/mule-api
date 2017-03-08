/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model.declaration.fluent;

import org.mule.runtime.api.meta.model.ComponentModel;
import org.mule.runtime.api.meta.model.Stereotype;

import java.util.HashSet;
import java.util.Set;

/**
 * A declaration object for a {@link ComponentModel}. It contains raw, unvalidated
 * data which is used to declare the structure of a {@link ComponentModel}
 *
 * @since 1.0
 */
public abstract class ComponentDeclaration<T extends ComponentDeclaration> extends ParameterizedDeclaration<T> {

  private OutputDeclaration outputContent;
  private OutputDeclaration outputAttributes;
  private boolean transactional = false;
  private boolean requiresConnection = false;
  private Set<Stereotype> stereotypes = new HashSet<>();

  /**
   * {@inheritDoc}
   */
  ComponentDeclaration(String name) {
    super(name);
  }

  public OutputDeclaration getOutput() {
    return outputContent;
  }

  public void setOutput(OutputDeclaration content) {
    this.outputContent = content;
  }

  public OutputDeclaration getOutputAttributes() {
    return outputAttributes;
  }

  public void setOutputAttributes(OutputDeclaration attributes) {
    this.outputAttributes = attributes;
  }

  public boolean isTransactional() {
    return transactional;
  }

  public void setTransactional(boolean transactional) {
    this.transactional = transactional;
  }

  public boolean isRequiresConnection() {
    return requiresConnection;
  }

  public void setRequiresConnection(boolean requiresConnection) {
    this.requiresConnection = requiresConnection;
  }

  public Set<Stereotype> getStereotypes() {
    return stereotypes;
  }

  public void addStereotype(Stereotype stereotype) {
    stereotypes.add(stereotype);
  }
}
