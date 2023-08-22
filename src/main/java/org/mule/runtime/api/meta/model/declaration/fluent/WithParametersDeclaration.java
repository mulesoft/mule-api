/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model.declaration.fluent;

import static java.util.Collections.unmodifiableList;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.mule.runtime.api.util.Preconditions.checkArgument;

import org.mule.api.annotation.NoImplement;

import java.util.ArrayList;
import java.util.List;

/**
 * Contract interface for a {@link BaseDeclaration} in which it's possible to add/get {@link SourceDeclaration} objects
 *
 * @param <T> the generic type of the {@link BaseDeclaration} which is implementing the interface
 * @since 1.0
 */
@NoImplement
public interface WithParametersDeclaration {

  /**
   * @return an unmodifiable {@link List} with the {@link ParameterGroupDeclaration declarations}
   */
  List<ParameterGroupDeclaration> getParameterGroups();

  /**
   * @param groupName the name of the group which declaration you seek
   * @return the {@link ParameterGroupDeclaration} of the given {@code groupName}.
   */
  ParameterGroupDeclaration getParameterGroup(String groupName);

  /**
   * Returns all the parameter declarations declared on all groups.
   *
   * @return a flattened list of all the parameters in this declaration
   */
  List<ParameterDeclaration> getAllParameters();

}
