/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model.display;

import org.mule.api.annotation.NoImplement;

import java.util.Optional;

/**
 * A model which optionally contains a {@link LayoutModel}
 *
 * @since 1.0
 */
@NoImplement
public interface HasLayoutModel {

  /**
   * @return An {@link Optional} {@link LayoutModel} which contains directives about how this parameter should be shown in the UI
   */
  Optional<LayoutModel> getLayoutModel();
}
