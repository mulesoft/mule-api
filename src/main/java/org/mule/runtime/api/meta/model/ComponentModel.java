/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model;

import org.mule.metadata.api.model.MetadataType;
import org.mule.runtime.api.message.Message;
import org.mule.runtime.api.meta.DescribedObject;
import org.mule.runtime.api.meta.NamedObject;
import org.mule.runtime.api.meta.model.display.HasDisplayModel;
import org.mule.runtime.api.meta.model.operation.OperationModel;
import org.mule.runtime.api.meta.model.parameter.ParameterizedModel;
import org.mule.runtime.api.meta.model.source.SourceModel;

/**
 * A definition of an component in a {@link ExtensionModel}. This model groups all the common contracts between extension
 * components like {@link OperationModel}, {@link SourceModel}, etc.
 *
 * @since 1.0
 */
public interface ComponentModel extends NamedObject, DescribedObject, EnrichableModel, ParameterizedModel, HasDisplayModel {

  /**
   * Returns a {@link MetadataType} for the value that this component sets
   * on the output {@link Message#getPayload()} field.
   *
   * @return a {@link MetadataType} representing the content type for the output messages
   */
  OutputModel getOutput();

  /**
   * Returns a {@link MetadataType} for the value that this component sets
   * on the output {@link Message#getAttributes()} field.
   *
   * @return a {@link MetadataType} representing the attribute types for the output messages
   */
  OutputModel getOutputAttributes();
}
