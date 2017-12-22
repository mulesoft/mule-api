/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model.source;

import org.mule.runtime.api.meta.model.EnrichableModel;
import org.mule.runtime.api.meta.model.display.HasDisplayModel;
import org.mule.runtime.api.meta.model.parameter.ParameterizedModel;

/**
 * Specialization of {@link ParameterizedModel} to describe a response callback on a message source
 * <p>
 * Do not create custom implementations of this interface. The Mule Runtime should be
 * the only one providing implementations of it.
 *
 * @since 1.0
 */
public interface SourceCallbackModel extends ParameterizedModel, HasDisplayModel, EnrichableModel {

}
