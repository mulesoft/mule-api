/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.metadata.resolving;

/**
 * A base interface for resolvers in which they specify it's category name.
 *
 * @since 1.0
 */
public interface NamedTypeResolver {

  /**
   * This is the name of the TypeResolver Category, that relates a given
   * {@link TypeKeysResolver} with the {@link InputTypeResolver input} and
   * {@link OutputTypeResolver output} type resolvers.
   * For any given component, all the {@link InputTypeResolver input} and
   * {@link OutputTypeResolver output} resolvers must belong to the same Category,
   * that is, have the same {@code categoryName}
   *
   * @return name of the category
   */
  String getCategoryName();

  /**
   * This is the name of the TypeResolver. It should not be repeated among {@link InputTypeResolver} and
   * {@link OutputTypeResolver} which are used together.
   * 
   * @return name of the resolver
   */
  String getResolverName();
}
