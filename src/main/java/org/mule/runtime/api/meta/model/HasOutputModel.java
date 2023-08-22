/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model;

import static java.util.Optional.empty;

import org.mule.api.annotation.NoImplement;
import org.mule.metadata.api.model.MetadataType;
import org.mule.runtime.api.message.Message;
import org.mule.runtime.api.meta.model.data.sample.SampleDataProviderModel;

import java.util.Optional;

/**
 * Interface for a model which declares an {@link OutputModel} for its resulting payload and attributes.
 *
 * @since 1.0
 */
@NoImplement
public interface HasOutputModel {

  /**
   * Returns a {@link MetadataType} for the value that this component sets on the output {@link Message#getPayload()} field.
   * <p>
   * If this executable component does not modify the payload of the {@link Message}, then a
   * {@link org.mule.metadata.api.model.VoidType} will be associated to the returned {@link OutputModel}. This, however, <b>does
   * not</b> mean that the property will be set to {@code null} on the message, it means that whatever value it had before the
   * component was executed will be preserved after it returns.
   *
   * @return a {@link MetadataType} representing the content type for the output messages
   */
  OutputModel getOutput();

  /**
   * Returns a {@link OutputModel} for the value that this component sets on the output {@link Message#getAttributes() attributes}
   * of the message.
   * <p>
   * If this executable component does not modify the attributes of the {@link Message}, then a
   * {@link org.mule.metadata.api.model.VoidType} will be associated to the returned {@link OutputModel}. This, however, <b>does
   * not</b> mean that the property will be set to {@code null} on the message, it means that whatever value it had before the
   * component was executed will be preserved after it returns.
   *
   * @return a {@link OutputModel} representing the attribute types for the output messages
   */
  OutputModel getOutputAttributes();

  /**
   * If the component supports providing sample data, this method returns an {@link Optional} {@link SampleDataProviderModel}
   * which describes the used provider.
   *
   * @return an {@link Optional} {@link SampleDataProviderModel}
   * @since 1.4.0
   */
  default Optional<SampleDataProviderModel> getSampleDataProviderModel() {
    return empty();
  }
}
