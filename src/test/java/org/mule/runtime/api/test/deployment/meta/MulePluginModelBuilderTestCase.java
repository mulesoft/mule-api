/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.test.deployment.meta;

import static java.util.Collections.emptyMap;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.rules.ExpectedException.none;

import org.mule.runtime.api.deployment.meta.MuleArtifactLoaderDescriptor;
import org.mule.runtime.api.deployment.meta.MulePluginModel;
import org.mule.runtime.api.deployment.meta.MulePluginModel.MulePluginModelBuilder;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class MulePluginModelBuilderTestCase {

  private static final String ARTIFACT_NAME = "artifact-name";
  private static final String MULE_VERSION = "4.0.0";
  private static final String DESCRIBER_ID = "ID-1";
  private static final String BUNDLE_DESCRIPTOR_LOADER_ID = "descriptorLoaderId";
  private static final String CLASSLOADER_DESCRIPTOR_LOADER_ID = "classLoaderLoaderId";

  private MulePluginModelBuilder builder;

  @Rule
  public ExpectedException expectedException = none();

  @Before
  public void before() {
    builder = new MulePluginModelBuilder();
  }

  @Test
  public void buildWithExtensionModelDescriptor() {
    builder.setName(ARTIFACT_NAME).setMinMuleVersion(MULE_VERSION);
    builder.withExtensionModelDescriber().setId(DESCRIBER_ID);
    builder.withBundleDescriptorLoader(new MuleArtifactLoaderDescriptor(BUNDLE_DESCRIPTOR_LOADER_ID, emptyMap()));
    builder.withClassLoaderModelDescriptorLoader(new MuleArtifactLoaderDescriptor(CLASSLOADER_DESCRIPTOR_LOADER_ID, emptyMap()));
    MulePluginModel mulePluginModel = builder.build();

    assertThat(mulePluginModel.getName(), is(ARTIFACT_NAME));
    assertThat(mulePluginModel.getMinMuleVersion(), is(MULE_VERSION));
    assertThat(mulePluginModel.getExtensionModelLoaderDescriptor().isPresent(), is(true));
    assertThat(mulePluginModel.getExtensionModelLoaderDescriptor().get().getId(), is(DESCRIBER_ID));
    assertThat(mulePluginModel.getExtensionModelLoaderDescriptor().get().getAttributes().size(), is(0));
    assertThat(mulePluginModel.getBundleDescriptorLoader().getId(), equalTo(BUNDLE_DESCRIPTOR_LOADER_ID));
    assertThat(mulePluginModel.getBundleDescriptorLoader().getAttributes(), is(emptyMap()));
    assertThat(mulePluginModel.getClassLoaderModelLoaderDescriptor().getId(), equalTo(CLASSLOADER_DESCRIPTOR_LOADER_ID));
    assertThat(mulePluginModel.getClassLoaderModelLoaderDescriptor().getAttributes(), is(emptyMap()));
  }

  @Test
  public void buildWithoutExtensionModelDescriptor() {
    builder.setName(ARTIFACT_NAME).setMinMuleVersion(MULE_VERSION);
    builder.withBundleDescriptorLoader(new MuleArtifactLoaderDescriptor(BUNDLE_DESCRIPTOR_LOADER_ID, emptyMap()));
    builder.withClassLoaderModelDescriptorLoader(new MuleArtifactLoaderDescriptor(CLASSLOADER_DESCRIPTOR_LOADER_ID, emptyMap()));
    MulePluginModel mulePluginModel = builder.build();

    assertThat(mulePluginModel.getName(), is(ARTIFACT_NAME));
    assertThat(mulePluginModel.getMinMuleVersion(), is(MULE_VERSION));
    assertThat(mulePluginModel.getExtensionModelLoaderDescriptor().isPresent(), is(false));
    assertThat(mulePluginModel.getBundleDescriptorLoader().getId(), equalTo(BUNDLE_DESCRIPTOR_LOADER_ID));
    assertThat(mulePluginModel.getBundleDescriptorLoader().getAttributes(), is(emptyMap()));
    assertThat(mulePluginModel.getClassLoaderModelLoaderDescriptor().getId(), equalTo(CLASSLOADER_DESCRIPTOR_LOADER_ID));
    assertThat(mulePluginModel.getClassLoaderModelLoaderDescriptor().getAttributes(), is(emptyMap()));
  }

  @Test
  public void buildWithoutBundleDescriptorLoader() {
    builder.setName(ARTIFACT_NAME).setMinMuleVersion(MULE_VERSION);
    builder.withExtensionModelDescriber().setId(DESCRIBER_ID);

    expectedException.expect(IllegalArgumentException.class);
    builder.build();
  }

  @Test
  public void blankName() {
    builder.setName(null);

    expectedException.expect(IllegalArgumentException.class);
    builder.build();
  }

  @Test
  public void blankVersion() {
    builder.setMinMuleVersion(null);

    expectedException.expect(IllegalArgumentException.class);
    builder.build();
  }

  @Test
  public void blankExtensionModelDescriberId() {
    builder.withExtensionModelDescriber().setId(null);

    expectedException.expect(IllegalArgumentException.class);
    builder.build();
  }

  @Test
  public void blankExtensionModelDescriberAttribute() {
    expectedException.expect(IllegalArgumentException.class);
    builder.withExtensionModelDescriber().setId("id").addProperty(null, "a value");
  }
}
