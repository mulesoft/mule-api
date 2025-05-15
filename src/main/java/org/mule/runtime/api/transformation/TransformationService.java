/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.transformation;

import org.mule.api.annotation.NoImplement;
import org.mule.runtime.api.message.Message;
import org.mule.runtime.api.metadata.DataType;

@NoImplement
public interface TransformationService {

  /**
   * Given a {@code value) it will try to transform it to the expected type defined in the {@code expectedDataType}
   *
   * @param value the value to transform
   *
   * @param valueDataType    the value's {@link DataType }
   * @param expectedDataType the expected type's {@link DataType}
   * @return the transformed value
   */
  Object transform(Object value, DataType valueDataType, DataType expectedDataType);

  /**
   * Attempts to obtain the payload of this message with the desired Class type. This will try and resolve a transformer that can
   * do this transformation. If a transformer cannot be found an exception is thrown. Any transformers added to the registry will
   * be checked for compatibility
   * <p/>
   * If the existing payload is consumable (i.e. can't be read twice) then the existing payload of the message will be replaced
   * with a byte[] representation as part of this operations.
   * <p/>
   *
   * @param message        the message to transform
   * @param outputDataType the desired return type
   */
  Message transform(Message message, DataType outputDataType);

}
