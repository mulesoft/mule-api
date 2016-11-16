/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model.display;

import java.util.Optional;

/**
 * A model which optionally contains a {@link LayoutModel}
 *
 * @since 1.0
 */
public interface HasLayoutModel {

  /**
   * @return An {@link Optional} {@link LayoutModel} which contains directives
   * about how this parameter should be shown in the UI
   */
  Optional<LayoutModel> getLayoutModel();
}
