/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.deployment.meta;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.mule.runtime.api.deployment.meta.MulePluginModel.MulePluginModelBuilder;

import org.junit.Before;
import org.junit.Test;

public class MulePluginModelBuilderTestCase {

  private static final String ARTIFACT_NAME = "artifact-name";
  private static final String MULE_VERSION = "4.0.0";
  private static final String DESCRIBER_ID = "ID-1";
  private MulePluginModelBuilder builder;

  @Before
  public void before() {
    builder = new MulePluginModelBuilder();
  }

  @Test
  public void buildWithExtensionModelDescriptor() {
    builder.setName(ARTIFACT_NAME).setMinMuleVersion(MULE_VERSION);
    builder.withExtensionModelDescriber().setId(DESCRIBER_ID);
    MulePluginModel mulePluginModel = builder.build();

    assertThat(mulePluginModel.getName(), is(ARTIFACT_NAME));
    assertThat(mulePluginModel.getMinMuleVersion(), is(MULE_VERSION));
    assertThat(mulePluginModel.getExtensionModelLoaderDescriptor().isPresent(), is(true));
    assertThat(mulePluginModel.getExtensionModelLoaderDescriptor().get().getId(), is(DESCRIBER_ID));
    assertThat(mulePluginModel.getExtensionModelLoaderDescriptor().get().getAttributes().size(), is(0));
  }

  @Test
  public void buildWithoutExtensionModelDescriptor() {
    builder.setName(ARTIFACT_NAME).setMinMuleVersion(MULE_VERSION);
    MulePluginModel mulePluginModel = builder.build();

    assertThat(mulePluginModel.getName(), is(ARTIFACT_NAME));
    assertThat(mulePluginModel.getMinMuleVersion(), is(MULE_VERSION));
    assertThat(mulePluginModel.getExtensionModelLoaderDescriptor().isPresent(), is(false));
  }

  @Test(expected = IllegalArgumentException.class)
  public void blankName() {
    builder.setName(null);
    builder.build();
  }

  @Test(expected = IllegalArgumentException.class)
  public void blankVersion() {
    builder.setMinMuleVersion(null);
    builder.build();
  }

  @Test(expected = IllegalArgumentException.class)
  public void blankExtensionModelDescriberId() {
    builder.withExtensionModelDescriber().setId(null);
    builder.build();
  }

  @Test(expected = IllegalArgumentException.class)
  public void blankExtensionModelDescriberAttribute() {
    builder.withExtensionModelDescriber().setId("id").addProperty(null, "a value");
    builder.build();
  }
}
