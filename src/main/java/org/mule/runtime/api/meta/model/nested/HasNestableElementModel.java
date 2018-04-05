/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model.nested;

import java.util.List;
import java.util.Optional;

/**
 * A model that contains nestable elements models
 *
 * @since 1.2
 */
public interface HasNestableElementModel {

  /**
   * Returns a {@link List} of {@link NestableElementModel}s defined at the level
   * of the component implementing this interface.
   * <p>
   *
   * @return an immutable {@link List} of {@link NestableElementModel}
   */
  List<NestableElementModel> getNestableElementModels();

  /**
   * Returns the {@link NestableElementModel} that matches
   * the given name.
   *
   * @param name case sensitive nestable element name
   * @return an {@link Optional} {@link NestableElementModel}
   */
  Optional<NestableElementModel> getNestableElementModel(String name);


}
