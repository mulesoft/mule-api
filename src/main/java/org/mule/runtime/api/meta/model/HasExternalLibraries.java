/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.meta.model;

import org.mule.api.annotation.NoImplement;

import java.util.Set;

/**
 * Interface for a model which contains {@link ExternalLibraryModel external libraries}
 *
 * @since 1.0
 */
@NoImplement
public interface HasExternalLibraries {

  /**
   * @return A {@link Set} of {@link ExternalLibraryModel} which represent the extension's external libraries
   */
  Set<ExternalLibraryModel> getExternalLibraryModels();
}
