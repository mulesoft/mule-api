/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model.function;

import org.mule.metadata.api.model.MetadataType;
import org.mule.runtime.api.message.Message;
import org.mule.runtime.api.meta.model.EnrichableModel;
import org.mule.runtime.api.meta.model.ExtensionModel;
import org.mule.runtime.api.meta.model.OutputModel;
import org.mule.runtime.api.meta.model.display.HasDisplayModel;
import org.mule.runtime.api.meta.model.parameter.ParameterizedModel;

/**
 * A definition of an function in a {@link ExtensionModel}.
 * <p>
 * Function models implement the flyweight pattern. This means
 * that a given function should only be represented by only
 * one instance of this class. Thus, if the same function is
 * contained by different {@link HasFunctionModels} instances,
 * then each of those containers should reference the same
 * function model instance.
 * <p>
 * Do not create custom implementations of this interface. The Mule Runtime should be
 * the only one providing implementations of it.
 *
 * @since 1.0
 */
public interface FunctionModel extends ParameterizedModel, EnrichableModel, HasDisplayModel {

  /**
   * Returns a {@link MetadataType} for the value that this component sets
   * on the output {@link Message#getPayload()} field.
   *
   * @return a {@link MetadataType} representing the content type for the output messages
   */
  OutputModel getOutput();

}
