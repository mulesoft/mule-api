/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.test.persistence;


import static java.util.Collections.emptyMap;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;

import org.mule.runtime.api.deployment.meta.MuleArtifactLoaderDescriptor;
import org.mule.runtime.api.deployment.meta.MulePolicyModel;
import org.mule.runtime.api.deployment.meta.MulePolicyModel.MulePolicyModelBuilder;
import org.mule.runtime.api.deployment.persistence.MulePolicyModelJsonSerializer;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;

public class MulePolicyModelJsonSerializerTestCase extends AbstractMuleArtifactModelJsonSerializerTestCase {

  private MulePolicyModelJsonSerializer mulePolicyModelJsonSerializer;

  @Before
  public void setUp() {
    mulePolicyModelJsonSerializer = new MulePolicyModelJsonSerializer();
  }

  @Test
  public void serializesModel() throws IOException {
    final String describerName = "policy-name";
    final String describerMinMuleVersion = "4.2.3";
    final String describerClassLoaderModelId = "classLoaderModelLoaderID";

    Map<String, Object> bundleDescriptorAttributes = new HashMap<>();
    bundleDescriptorAttributes.put(BUNDLE_DESCRIPTOR_LOADER_KEY_1, BUNDLE_DESCRIPTOR_LOADER_VALUE_1);
    bundleDescriptorAttributes.put(BUNDLE_DESCRIPTOR_LOADER_KEY_2, BUNDLE_DESCRIPTOR_LOADER_VALUE_2);

    final MulePolicyModelBuilder mulePolicyModelBuilder =
        new MulePolicyModelBuilder().setName(describerName).setMinMuleVersion(describerMinMuleVersion)
            .withBundleDescriptorLoader(new MuleArtifactLoaderDescriptor(BUNDLE_DESCRIPTOR_LOADER_ID,
                                                                         bundleDescriptorAttributes));
    mulePolicyModelBuilder
        .withClassLoaderModelDescriptorLoader(new MuleArtifactLoaderDescriptor(describerClassLoaderModelId, emptyMap()));

    String actual = mulePolicyModelJsonSerializer.serialize(mulePolicyModelBuilder.build());
    final JsonObject actualElement = new JsonParser().parse(actual).getAsJsonObject();
    assertThat(actualElement.get("name").getAsString(), is(describerName));
    assertThat(actualElement.get("minMuleVersion").getAsString(), is(describerMinMuleVersion));

    JsonObject classLoaderModelDescriptor = actualElement.get("classLoaderModelLoaderDescriptor").getAsJsonObject();
    assertThat(classLoaderModelDescriptor.get(ID).getAsString(), is(describerClassLoaderModelId));
    assertThat(classLoaderModelDescriptor.get(ATTRIBUTES).getAsJsonObject().entrySet(), is(empty()));

    JsonObject bundleDescriptor = actualElement.get("bundleDescriptorLoader").getAsJsonObject();
    assertThat(bundleDescriptor.get("id").getAsString(), is(BUNDLE_DESCRIPTOR_LOADER_ID));
    assertThat(bundleDescriptor.get(ATTRIBUTES).getAsJsonObject().entrySet().size(), equalTo(2));
    assertThat(bundleDescriptor.get(ATTRIBUTES).getAsJsonObject().get(BUNDLE_DESCRIPTOR_LOADER_KEY_1).getAsString(),
               equalTo(BUNDLE_DESCRIPTOR_LOADER_VALUE_1));
    assertThat(bundleDescriptor.get(ATTRIBUTES).getAsJsonObject().get(BUNDLE_DESCRIPTOR_LOADER_KEY_2).getAsString(),
               equalTo(BUNDLE_DESCRIPTOR_LOADER_VALUE_2));
  }

  @Test
  public void deserializesModel() throws IOException {
    InputStream resource = getClass().getResourceAsStream("/descriptor/policy-descriptor-with-classloader-model.json");

    MulePolicyModel deserialized = mulePolicyModelJsonSerializer.deserialize(IOUtils.toString(resource));

    assertNameAndMinMuleVersion(deserialized);
    assertClassLoaderModel(deserialized);
    assertBundleDescriptorLoader(deserialized.getBundleDescriptorLoader());
  }

  private void assertNameAndMinMuleVersion(MulePolicyModel deserialize) {
    assertThat(deserialize.getName(), is("plugin-name"));
    assertThat(deserialize.getMinMuleVersion(), is("4.2.3"));
  }

  private void assertClassLoaderModel(MulePolicyModel deserialize) {
    final MuleArtifactLoaderDescriptor extensionModelDescriptor = deserialize.getClassLoaderModelLoaderDescriptor();
    assertThat(extensionModelDescriptor.getId(), is("maven"));
    assertThat(extensionModelDescriptor.getAttributes().size(), is(2));
    final Object exportedPackages = extensionModelDescriptor.getAttributes().get("exportedPackages");
    assertThat(exportedPackages, is(instanceOf(List.class)));
    assertThat((List<String>) exportedPackages, containsInAnyOrder("org.mule.extension.api", "org.mule.extension.api.exception"));
    final Object exportedResources = extensionModelDescriptor.getAttributes().get("exportedResources");
    assertThat(exportedResources, is(instanceOf(List.class)));
    assertThat((List<String>) exportedResources, containsInAnyOrder("/META-INF/some.file", "/META-INF/other.file"));
  }

}
