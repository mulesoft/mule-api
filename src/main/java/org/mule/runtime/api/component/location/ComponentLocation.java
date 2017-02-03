/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.component.location;

import org.mule.runtime.api.component.ComponentIdentifier;
import org.mule.runtime.api.component.TypedComponentIdentifier;

import java.util.List;
import java.util.Optional;

/**
 * Provides information about the location of a component within an application.
 *
 * A component location describes where the component is defined in the configuration of the artifact.
 *
 * For instance:
 * <ul>
 * <li>COMPONENT_NAME - global component defined with name COMPONENT_NAME</li>
 * <li>FLOW_NAME/source - a source defined within a flow</li>
 * <li>FLOW_NAME/processors/0 - the first processor defined within a flow with name FLOW_NAME</li>
 * <li>FLOW_NAME/processors/4/1 - the first processors defined inside another processor which is positioned fifth within a flow
 * with name FLOW_NAME</li>
 * <li>FLOW_NAME/errorHandler/0 - the first on-error within the error handler</li>
 * <li>FLOW_NAME/0/errorHandler/3 - the third on-error within the error handler of the first element of the flow with name
 * FLOW_NAME</li>
 * </ul>
 *
 * The different {@link LocationPart}s in FLOW_NAME/processors/1 are:
 * <ul>
 * <li>'processors' as partPath and no component identifier since this part is synthetic to indicate the part of the flow
 * referenced by the next index</li>
 * <li>'1' as partPath and 'mule:payload' as component identifier assuming that the second processor of the flow was a set-payload
 * component</li>
 * </ul>
 *
 *
 * @since 1.0
 */
public interface ComponentLocation {

  /**
   * @return the unique absolute path of the component in the application.
   */
  String getLocation();

  /**
   * @return the config file of the application where this component is defined, if it was defined in a config file.
   */
  Optional<String> getFileName();

  /**
   * @return the line number in the config file of the application where this component is defined, if it was defined in a config
   *         file.
   */
  Optional<Integer> getLineInFile();

  /**
   * @return the list of parts for the location. The location starts with the global element containing the component and
   *         continues with the next elements within the global element until the last part which is the component specific part.
   */
  List<LocationPart> getParts();

  /**
   * @return the {@link ComponentIdentifier} of the component associated with this location
   */
  TypedComponentIdentifier getComponentIdentifier();

}
