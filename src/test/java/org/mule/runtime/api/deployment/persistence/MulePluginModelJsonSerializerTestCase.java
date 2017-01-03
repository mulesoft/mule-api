/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.deployment.persistence;


import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import org.mule.runtime.api.deployment.meta.MuleArtifactLoaderDescriptor;
import org.mule.runtime.api.deployment.meta.MulePluginModel;
import org.mule.runtime.api.deployment.meta.MulePluginModel.MulePluginModelBuilder;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;


public class MulePluginModelJsonSerializerTestCase {

  private static final String ID = "id";
  private static final String ATTRIBUTES = "attributes";

  private MulePluginModelJsonSerializer mulePluginModelJsonSerializer;

  @Before
  public void setUp() {
    mulePluginModelJsonSerializer = new MulePluginModelJsonSerializer();
  }

  @Test
  public void serialize() throws IOException {
    final String describerName = "plugin-name";
    final String describerMinMuleVersion = "4.2.3";
    final String describerExtensionModelId = "annotations";
    final String classAttributeKey = "class";
    final String classAttributeValue = "org.mule.extension.internal.ExtensionConnector";
    final String describerClassLoaderModelId = "annotations";
    final String exportedPackagesKey = "exportedPackages";
    final List<String> exportedPackagesAttributeValue = asList("org.mule.extension.api", "org.mule.extension.api.exception");

    final MulePluginModelBuilder mulePluginModelBuilder =
        new MulePluginModelBuilder().setName(describerName).setMinMuleVersion(describerMinMuleVersion);
    mulePluginModelBuilder.withExtensionModelDescriber().setId(describerExtensionModelId).addProperty(classAttributeKey,
                                                                                                      classAttributeValue);
    mulePluginModelBuilder.withClassLoaderModelDescriber().setId(describerClassLoaderModelId).addProperty(exportedPackagesKey,
                                                                                                          exportedPackagesAttributeValue);

    String actual = mulePluginModelJsonSerializer.serialize(mulePluginModelBuilder.build());
    final JsonObject actualElement = new JsonParser().parse(actual).getAsJsonObject();
    assertThat(actualElement.get("name").getAsString(), is(describerName));
    assertThat(actualElement.get("minMuleVersion").getAsString(), is(describerMinMuleVersion));
    JsonObject extensionModelDescriptor = actualElement.get("extensionModelLoaderDescriptor").getAsJsonObject();
    assertThat(extensionModelDescriptor.get("id").getAsString(), is(describerExtensionModelId));
    assertThat(extensionModelDescriptor.get("attributes").getAsJsonObject().get(classAttributeKey).getAsString(),
               is(classAttributeValue));

    JsonObject classLoaderModelDescriptor = actualElement.get("classLoaderModelLoaderDescriptor").getAsJsonObject();
    assertThat(classLoaderModelDescriptor.get(ID).getAsString(), is(describerClassLoaderModelId));
    final JsonArray attributes =
        classLoaderModelDescriptor.get(ATTRIBUTES).getAsJsonObject().get(exportedPackagesKey).getAsJsonArray();
    assertThat(attributes.size(), is(exportedPackagesAttributeValue.size()));
    List<String> list = new ArrayList<>();
    attributes.iterator().forEachRemaining(jsonElement -> list.add(jsonElement.getAsString()));
    assertThat(list, containsInAnyOrder(exportedPackagesAttributeValue.toArray()));
  }

  @Test
  public void deserializeWithExtensionModel() throws IOException {
    InputStream resource = getClass().getResourceAsStream("/descriptor/plugin-descriptor-with-ext-model.json");

    MulePluginModel deserialize = mulePluginModelJsonSerializer.deserialize(IOUtils.toString(resource));

    assertNameAndMinMuleVersion(deserialize);
    assertThat(deserialize.getClassLoaderModelLoaderDescriptor().isPresent(), is(false));
    assertExtensionModel(deserialize);
  }

  @Test
  public void deserializeWithClassLoaderModel() throws IOException {
    InputStream resource = getClass().getResourceAsStream("/descriptor/plugin-descriptor-with-classloader-model.json");

    MulePluginModel deserialize = mulePluginModelJsonSerializer.deserialize(IOUtils.toString(resource));

    assertNameAndMinMuleVersion(deserialize);
    assertThat(deserialize.getExtensionModelLoaderDescriptor().isPresent(), is(false));
    assertClassLoaderModel(deserialize);
  }

  @Test
  public void deserializeWithExtensionModelAndClassLoaderModel() throws IOException {
    InputStream resource =
        getClass().getResourceAsStream("/descriptor/plugin-descriptor-with-ext-model-and-classloader-model.json");

    MulePluginModel deserialize = mulePluginModelJsonSerializer.deserialize(IOUtils.toString(resource));

    assertNameAndMinMuleVersion(deserialize);
    assertExtensionModel(deserialize);
    assertClassLoaderModel(deserialize);
  }

  @Test
  public void deserializeWithoutExtensionModel() throws IOException {
    InputStream resource =
        getClass().getResourceAsStream("/descriptor/plugin-descriptor-clean.json");

    MulePluginModel deserialize = mulePluginModelJsonSerializer.deserialize(IOUtils.toString(resource));

    assertNameAndMinMuleVersion(deserialize);
    assertThat(deserialize.getExtensionModelLoaderDescriptor().isPresent(), is(false));
    assertThat(deserialize.getClassLoaderModelLoaderDescriptor().isPresent(), is(false));
  }

  private void assertNameAndMinMuleVersion(MulePluginModel deserialize) {
    assertThat(deserialize.getName(), is("plugin-name"));
    assertThat(deserialize.getMinMuleVersion(), is("4.2.3"));
  }

  private void assertExtensionModel(MulePluginModel deserialize) {
    assertThat(deserialize.getExtensionModelLoaderDescriptor().isPresent(), is(true));
    final MuleArtifactLoaderDescriptor extensionModelDescriptor = deserialize.getExtensionModelLoaderDescriptor().get();
    assertThat(extensionModelDescriptor.getId(), is("annotations"));
    assertThat(extensionModelDescriptor.getAttributes().size(), is(1));
    assertThat(extensionModelDescriptor.getAttributes().get("class"),
               is("org.mule.extension.internal.ExtensionConnector"));
  }

  private void assertClassLoaderModel(MulePluginModel deserialize) {
    assertThat(deserialize.getClassLoaderModelLoaderDescriptor().isPresent(), is(true));
    final MuleArtifactLoaderDescriptor extensionModelDescriptor = deserialize.getClassLoaderModelLoaderDescriptor().get();
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
