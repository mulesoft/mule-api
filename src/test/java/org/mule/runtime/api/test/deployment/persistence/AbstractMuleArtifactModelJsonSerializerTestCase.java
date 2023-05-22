/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.runtime.api.test.deployment.persistence;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;
import org.mule.runtime.api.deployment.meta.MuleArtifactLoaderDescriptor;

public class AbstractMuleArtifactModelJsonSerializerTestCase {

  protected static final String ID = "id";
  protected static final String ATTRIBUTES = "attributes";
  protected static final String BUNDLE_DESCRIPTOR_LOADER_ID = "bundleDescriptorLoaderId";
  protected static final String BUNDLE_DESCRIPTOR_LOADER_KEY_1 = "bundleDescriptorLoaderKey1";
  protected static final String BUNDLE_DESCRIPTOR_LOADER_KEY_2 = "bundleDescriptorLoaderKey2";
  protected static final String BUNDLE_DESCRIPTOR_LOADER_VALUE_1 = "bundleDescriptorLoaderValue1";
  protected static final String BUNDLE_DESCRIPTOR_LOADER_VALUE_2 = "bundleDescriptorLoaderValue2";

  /**
   * Checks that a given bundle descriptor loader matches the expected one
   *
   * @param bundleDescriptorLoader loader to check.
   */
  protected void assertBundleDescriptorLoader(MuleArtifactLoaderDescriptor bundleDescriptorLoader) {
    assertThat(bundleDescriptorLoader, is(not(nullValue())));
    assertThat(bundleDescriptorLoader.getId(), is(BUNDLE_DESCRIPTOR_LOADER_ID));
    assertThat(bundleDescriptorLoader.getAttributes().size(), is(2));
    assertThat(bundleDescriptorLoader.getAttributes().get(BUNDLE_DESCRIPTOR_LOADER_KEY_1), is(BUNDLE_DESCRIPTOR_LOADER_VALUE_1));
    assertThat(bundleDescriptorLoader.getAttributes().get(BUNDLE_DESCRIPTOR_LOADER_KEY_2), is(BUNDLE_DESCRIPTOR_LOADER_VALUE_2));
  }
}
