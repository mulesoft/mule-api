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
   *  //FIXME improve this jdoc
   * @return name of the category
   */
  String getCategoryName();
}
