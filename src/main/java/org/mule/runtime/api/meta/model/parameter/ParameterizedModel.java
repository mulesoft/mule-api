/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.meta.model.parameter;

import static java.util.Collections.unmodifiableList;
import static java.util.stream.Collectors.toList;
import org.mule.api.annotation.NoImplement;
import org.mule.runtime.api.meta.DescribedObject;
import org.mule.runtime.api.meta.NamedObject;

import java.util.List;

/**
 * Base interface for a model which contains {@link ParameterModel parameters}
 *
 * @since 1.0
 */
@NoImplement
public interface ParameterizedModel extends NamedObject, DescribedObject {

  /**
   * @return a {@Link List} of {@link ParameterGroupModel groups}
   */
  List<ParameterGroupModel> getParameterGroupModels();

  /**
   * Returns all the {@link ParameterModel parameters} on all groups.
   *
   * @return a flattened list of all the parameters in this model
   */
  default List<ParameterModel> getAllParameterModels() {
    return unmodifiableList(getParameterGroupModels().stream()
        .flatMap(g -> g.getParameterModels().stream())
        .collect(toList()));
  }
}
