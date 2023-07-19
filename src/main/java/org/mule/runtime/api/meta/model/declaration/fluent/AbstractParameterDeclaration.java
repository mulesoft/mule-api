/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.meta.model.declaration.fluent;

import org.mule.runtime.api.meta.model.display.LayoutModel;

/**
 * Abstract declaration object for parameters. It contains raw, unvalidated data which is used to declare the common structure of
 * both parameters and parameter groups.
 * <p>
 *
 * @since 1.0
 */
public abstract class AbstractParameterDeclaration<T extends AbstractParameterDeclaration> extends NamedDeclaration<T> {

  protected LayoutModel layoutModel;

  /**
   * Creates a new instance
   *
   * @param name the name of the component being declared
   */
  public AbstractParameterDeclaration(String name) {
    super(name);
  }

  public LayoutModel getLayoutModel() {
    return layoutModel;
  }

  public void setLayoutModel(LayoutModel layoutModel) {
    this.layoutModel = layoutModel;
  }
}
