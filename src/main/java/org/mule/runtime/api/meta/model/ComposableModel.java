/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.meta.model;

import org.mule.api.annotation.NoImplement;
import org.mule.runtime.api.meta.NamedObject;
import org.mule.runtime.api.meta.model.nested.NestableElementModel;

import java.util.List;

/**
 * Base interface for a model which contains {@link NestableElementModel nested components}
 *
 * @see NestableElementModel
 * @since 1.0
 */
@NoImplement
public interface ComposableModel extends NamedObject {

  /**
   * @return the {@link List} of {@link NestableElementModel components} contained by {@code this} model
   */
  List<? extends NestableElementModel> getNestedComponents();

}
