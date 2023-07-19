/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.meta.model.deprecated;


import java.util.Optional;

/**
 * A model that fully describes the deprecation of a part of the extension model that is a {@link DeprecableModel}.
 * 
 * @since 1.2
 */
public interface DeprecationModel {

  /**
   * @return a {@link String} that describes why something was deprecated, what can be used as substitute, or both.
   */
  String getMessage();

  /**
   * @return a {@link String} which is the version of the extension in which the annotated member was deprecated.
   */
  String getDeprecatedSince();

  /**
   * @return a {@link String} which is the version of the extension in which the annotated member will be removed or was removed.
   */
  Optional<String> getToRemoveIn();

}
