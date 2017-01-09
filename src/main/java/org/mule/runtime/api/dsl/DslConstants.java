/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.dsl;

/**
 * Mule DSL constants.
 *
 * @since 1.0
 */
public interface DslConstants {

  /**
   * This is the namespace prefix for core elements in the configuration.
   */
  String CORE_NAMESPACE = "mule";

  /**
   * The name of the 'name' attribute of a DSL element
   */
  String NAME_ATTRIBUTE_NAME = "name";

  /**
   * The name of the 'config' attribute of an executable element in the DSL
   */
  String CONFIG_ATTRIBUTE_NAME = "config-ref";

  /**
   * The name of the 'key' element of a Map DSL entry
   */
  String KEY_ATTRIBUTE_NAME = "key";

  /**
   * The name of the 'value' element of a Map DSL entry
   */
  String VALUE_ATTRIBUTE_NAME = "value";

  /**
   * The identifier name of the {@code reconnectForever} implementation of
   * {@code reconnectionStrategy} infrastructure parameter
   */
  String RECONNECT_FOREVER_ELEMENT_IDENTIFIER = "reconnect-forever";

  /**
   * The identifier name of the {@code reconnectForever} implementation of
   * {@code reconnectionStrategy} infrastructure parameter
   */
  String RECONNECT_ELEMENT_IDENTIFIER = "reconnect";

  /**
   * The identifier name of the {@code redeliveryPolicy} infrastructure parameter
   */
  String REDELIVERY_POLICY_ELEMENT_IDENTIFIER = "redelivery-policy";

  /**
   * The identifier name of the {@code poolingProfile} infrastructure parameter
   */
  String POOLING_PROFILE_ELEMENT_IDENTIFIER = "pooling-profile";

}
