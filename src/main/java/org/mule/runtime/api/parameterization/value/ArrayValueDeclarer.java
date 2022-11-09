/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.parameterization.value;

import java.util.function.Consumer;

import org.mule.api.annotation.NoImplement;

/**
 * Describes an Array value to be created.
 *
 * This should be used the same way regardless of its actual format (Java object, Json/XML document, CSV line, etc).
 * 
 * @since 1.5.0
 */
@NoImplement
public interface ArrayValueDeclarer {

  /**
   * Describe an item of the array
   *
   * @param value the value to be associated with the entry
   * @return this declarer
   */
  ArrayValueDeclarer withItem(Object value);

  /**
   * Describe an item of the array
   *
   * @param valueDeclarerConsumer a consumer to configure the value of the item
   * @return this declarer
   */
  ArrayValueDeclarer withItem(Consumer<ValueDeclarer> valueDeclarerConsumer);

}
