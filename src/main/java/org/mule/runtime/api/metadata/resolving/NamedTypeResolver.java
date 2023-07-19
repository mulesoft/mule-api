/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.metadata.resolving;

/**
 * A base interface for resolvers in which they specify it's category name.
 *
 * @since 1.0
 */
public interface NamedTypeResolver {

  /**
   * This is the name of the TypeResolver Category, that relates a given {@link TypeKeysResolver} with the
   * {@link InputTypeResolver input} and {@link OutputTypeResolver output} type resolvers. For any given component, all the
   * {@link InputTypeResolver input} and {@link OutputTypeResolver output} resolvers must belong to the same Category, that is,
   * have the same {@code categoryName}
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
