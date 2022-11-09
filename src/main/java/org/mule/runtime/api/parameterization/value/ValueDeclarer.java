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
 * Describes a value to be created.
 *
 * @since 1.5.0
 */
@NoImplement
public interface ValueDeclarer {

  /**
   * Declares that the described value represents an Object value.
   *
   * @param objectValueDeclarerConsumer a consumer to configure the value of the object
   */
  void objectValue(Consumer<ObjectValueDeclarer> objectValueDeclarerConsumer);

  /**
   * Declares that the described value represents an Object value with the type that represents the value being declared, this is
   * necessary when the value has multiple types available and is needed to know which is chosen.
   *
   * @param objectValueDeclarerConsumer a consumer to configure the value of the object
   * @param typeIdentifier              the of the type the value represents.
   */
  void objectValue(Consumer<ObjectValueDeclarer> objectValueDeclarerConsumer, String typeIdentifier);

  /**
   * Declares that the described value represents an Array value.
   *
   * @param arrayValueDeclarerConsumer a consumer to configure the value of the array
   */
  void arrayValue(Consumer<ArrayValueDeclarer> arrayValueDeclarerConsumer);

  /**
   * Declares the value that is described by this declarer.
   *
   * @param value the actual value
   */
  void withValue(Object value);

}
