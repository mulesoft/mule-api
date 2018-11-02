/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model.deprecated;


/**
 * A model that fully describes the deprecation of a part of the extension model that is a {@link DeprecatableModel}.
 * 
 * @since 1.2
 */
public interface DeprecatedModel {

  /**
   * @return a {@link String} that describes why something was deprecated, what can be used as substitute, or both.
   */
  String getMessage();

}
