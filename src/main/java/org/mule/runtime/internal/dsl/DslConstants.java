/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.internal.dsl;

import static java.lang.String.format;

/**
 * Mule DSL constants.
 *
 * This is an internal utils class, not to be considered part of the API. Backwards compatibility not guaranteed.
 *
 * @since 1.0
 */
public interface DslConstants {

  /**
   * This is the namespace prefix for core elements in the configuration.
   */
  String CORE_PREFIX = "mule";

  /**
   * Format mask for the default location of a schema
   */
  String DEFAULT_NAMESPACE_URI_MASK = "http://www.mulesoft.org/schema/mule/%s";

  /**
   * Namespace for Mule core elements
   */
  String CORE_NAMESPACE = format(DEFAULT_NAMESPACE_URI_MASK, "core");

  /**
   * This is the namespace prefix for core domain elements in the configuration.
   */
  String DOMAIN_PREFIX = "domain";

  /**
   * Namespace for Mule core domain elements
   */
  String DOMAIN_NAMESPACE = format(DEFAULT_NAMESPACE_URI_MASK, "domain");

  /**
   * This is the namespace prefix for EE elements in the configuration.
   */
  String EE_PREFIX = "ee";

  /**
   * Namespace for EE elements
   */
  String EE_NAMESPACE = format(DEFAULT_NAMESPACE_URI_MASK, "ee/core");

  /**
   * This is the namespace prefix for EE domain elements in the configuration.
   */
  String EE_DOMAIN_PREFIX = "ee-domain";

  /**
   * Namespace for EE domain elements
   */
  String EE_DOMAIN_NAMESPACE = format(DEFAULT_NAMESPACE_URI_MASK, "ee/domain");

  /**
   * The identifier name of a {@code flow}
   */
  String FLOW_ELEMENT_IDENTIFIER = "flow";

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
   * The identifier name of the {@code reconnectForever} implementation of {@code reconnectionStrategy} infrastructure parameter
   */
  String RECONNECT_FOREVER_ELEMENT_IDENTIFIER = "reconnect-forever";

  /**
   * The identifier name of the {@code reconnectForever} implementation of {@code reconnectionStrategy} infrastructure parameter
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

  /**
   * The identifier name of the {@code tls:context} infrastructure parameter
   */
  String TLS_CONTEXT_ELEMENT_IDENTIFIER = "context";

  /**
   * The prefix name of the {@code tls:context} infrastructure parameter
   */
  String TLS_PREFIX = "tls";

  /**
   * The identifier name of the {@code transform} operation
   */
  String TRANSFORM_IDENTIFIER = "transform";
}
