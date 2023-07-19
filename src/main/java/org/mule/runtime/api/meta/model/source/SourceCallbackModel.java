/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.meta.model.source;

import org.mule.api.annotation.NoImplement;
import org.mule.runtime.api.meta.model.EnrichableModel;
import org.mule.runtime.api.meta.model.display.HasDisplayModel;
import org.mule.runtime.api.meta.model.parameter.ParameterizedModel;

/**
 * Specialization of {@link ParameterizedModel} to describe a response callback on a message source
 *
 * @since 1.0
 */
@NoImplement
public interface SourceCallbackModel extends ParameterizedModel, HasDisplayModel, EnrichableModel {

}
