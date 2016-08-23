/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.metadata;

import java.util.Optional;

/**
 * Represents a component inside a mule application. The component might be inside a flow (like message processors or sources), or
 * it can be in a more global scope like a configuration.
 *
 * @since 1.0
 */
public interface ComponentId {

  /**
   * @return Flow name that holds the Component
   */
  Optional<String> getFlowName();

  /**
   * @return Path of the Component
   */
  String getComponentPath();

}
