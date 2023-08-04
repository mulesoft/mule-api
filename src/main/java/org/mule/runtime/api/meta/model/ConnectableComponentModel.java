/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.meta.model;

import org.mule.api.annotation.NoImplement;
import org.mule.runtime.api.meta.model.operation.OperationModel;
import org.mule.runtime.api.meta.model.source.SourceModel;

/**
 * A definition of an executable {@link ComponentModel} in an {@link ExtensionModel}. This model groups all the common contracts
 * between extension components that can be executed, producing or altering an event, like an {@link OperationModel} or
 * {@link SourceModel}.
 *
 * @since 1.0
 */
@NoImplement
public interface ConnectableComponentModel extends ComponentModel, HasOutputModel {

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
