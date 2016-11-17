/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.deployment.persistence;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import org.mule.runtime.api.deployment.meta.MulePluginModel;
import org.mule.runtime.api.deployment.meta.MulePluginModelBuilder;
import org.mule.runtime.api.deployment.meta.MulePluginProperty;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;

public class MulePluginModelJsonSerializerTestCase {

  private MulePluginModelJsonSerializer mulePluginModelJsonSerializer;

  @Before
  public void setUp() {
    mulePluginModelJsonSerializer = new MulePluginModelJsonSerializer();
  }

  @Test
  public void serialize() throws IOException {
    String describerName = "plugin-name";
    String describerMinMuleVersion = "4.2.3";
    String describerExtensionModelId = "annotations";
    String classAttributeKey = "class";
    String valueAttributeKey = "org.mule.extension.internal.ExtensionConnector";

    MulePluginModelBuilder mulePluginModelBuilder =
        new MulePluginModelBuilder().setName(describerName).setMinMuleVersion(describerMinMuleVersion);

    mulePluginModelBuilder.withExtensionModelDescriber().setId(describerExtensionModelId).addProperty(classAttributeKey,
                                                                                                      valueAttributeKey);

    String actual = mulePluginModelJsonSerializer.serialize(mulePluginModelBuilder.build());
    final JsonObject actualElement = new JsonParser().parse(actual).getAsJsonObject();
    assertThat(actualElement.get("name").getAsString(), is(describerName));
    assertThat(actualElement.get("minMuleVersion").getAsString(), is(describerMinMuleVersion));
    JsonObject extensionModelDescriptor = actualElement.get("extensionModelLoaderDescriptor").getAsJsonObject();
    assertThat(extensionModelDescriptor.get("id").getAsString(), is(describerExtensionModelId));
    assertThat(extensionModelDescriptor.get("attributes").getAsJsonObject().get(classAttributeKey).getAsString(),
               is(valueAttributeKey));
  }

  @Test
  public void deserializeWithOptional() throws IOException {
    InputStream resource = getClass().getResourceAsStream("/descriptor/test-extension-mule-descriptor.json");

    MulePluginModel deserialize = mulePluginModelJsonSerializer.deserialize(IOUtils.toString(resource));

    assertThat(deserialize.getName(), is("plugin-name"));
    assertThat(deserialize.getMinMuleVersion(), is("4.2.3"));
    assertThat(deserialize.getExtensionModelLoaderDescriptor().isPresent(), is(true));
    MulePluginProperty extensionModelDescriptor = deserialize.getExtensionModelLoaderDescriptor().get();
    assertThat(extensionModelDescriptor.getId(), is("annotations"));
    assertThat(extensionModelDescriptor.getAttributes().size(), is(1));
    assertThat(extensionModelDescriptor.getAttributes().get("class"),
               is("org.mule.extension.internal.ExtensionConnector"));
  }

  @Test
  public void deserializeWithoutExtensionModelDescriptor() throws IOException {
    InputStream resource =
        getClass().getResourceAsStream("/descriptor/test-extension-mule-descriptor-without-ext-model-descriptor.json");

    MulePluginModel deserialize = mulePluginModelJsonSerializer.deserialize(IOUtils.toString(resource));

    assertThat(deserialize.getName(), is("plugin-name"));
    assertThat(deserialize.getMinMuleVersion(), is("4.2.3"));
    assertThat(deserialize.getExtensionModelLoaderDescriptor().isPresent(), is(false));
  }
}
