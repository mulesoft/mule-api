/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.metadata;

import org.mule.api.annotation.NoImplement;
import org.mule.metadata.api.TypeLoader;

/**
 * A {@link TypeLoader} that can load all the types of an artifact.
 *
 * @since 1.5
 */
@NoImplement
public interface ArtifactTypeLoader extends TypeLoader {
}
