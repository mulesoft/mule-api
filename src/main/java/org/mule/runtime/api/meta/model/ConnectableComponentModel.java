/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model;

import org.mule.api.annotation.NoImplement;
import org.mule.runtime.api.meta.model.operation.OperationModel;
import org.mule.runtime.api.meta.model.source.SourceModel;
import org.mule.runtime.api.meta.model.version.HasMinMuleVersion;

/**
 * A definition of an executable {@link ComponentModel} in an {@link ExtensionModel}. This model groups all the common contracts
 * between extension components that can be executed, producing or altering an event, like an {@link OperationModel} or
 * {@link SourceModel}.
 *
 * @since 1.0
 */
@NoImplement
public interface ConnectableComponentModel extends ComponentModel, HasOutputModel, HasMinMuleVersion {

  /**
   * @return whether this component has the ability to execute while joining a transaction
   */
  boolean isTransactional();

  /**
   * @return whether this component requires a connection in order to perform its execution
   */
  boolean requiresConnection();

  /**
   * Indicates if this component supports streaming.
   * <p>
   * Notice that supporting streaming doesn't necessarily mean that streaming will be performed each time the component is
   * executed
   *
   * @return whether this component supports streaming or not
   */
  boolean supportsStreaming();

}
