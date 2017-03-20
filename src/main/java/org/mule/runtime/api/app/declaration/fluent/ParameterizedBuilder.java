/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.app.declaration.fluent;

/**
 * Provides a way to enrich a parameterized element with a parameter
 * with an identifier of type {@link K}, and a value of type {@link V}
 * using a builder pattern with return type {@link R}
 *
 * @since 1.0
 */
public interface ParameterizedBuilder<K, V, R> {

  R withParameter(K key, V value);
}
