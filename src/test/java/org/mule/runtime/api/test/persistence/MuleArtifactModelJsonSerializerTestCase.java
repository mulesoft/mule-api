/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.test.persistence;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mule.runtime.api.deployment.meta.AbstractMuleArtifactModel.BUNDLE_DESCRIPTOR_LOADER;
import static org.mule.runtime.api.deployment.meta.AbstractMuleArtifactModel.CLASS_LOADER_MODEL_LOADER_DESCRIPTOR;
import static org.mule.runtime.api.deployment.meta.AbstractMuleArtifactModel.ID;
import static org.mule.runtime.api.deployment.meta.AbstractMuleArtifactModel.MIN_MULE_VERSION;
import static org.mule.runtime.api.deployment.meta.AbstractMuleArtifactModel.NAME;
import static org.mule.runtime.api.deployment.meta.AbstractMuleArtifactModel.REQUIRED_PRODUCT;
import static org.mule.runtime.api.deployment.meta.Product.MULE;

import org.mule.runtime.api.deployment.meta.MuleDeployableModel;
import org.mule.runtime.api.deployment.meta.MuleServerPluginModel;
import org.mule.runtime.api.deployment.meta.MuleServiceModel;
import org.mule.runtime.api.deployment.persistence.AbstractMuleArtifactModelJsonSerializer;
import org.mule.runtime.api.deployment.persistence.MuleApplicationModelJsonSerializer;
import org.mule.runtime.api.deployment.persistence.MuleDomainModelJsonSerializer;
import org.mule.runtime.api.deployment.persistence.MuleServerPluginModelJsonSerializer;
import org.mule.runtime.api.deployment.persistence.MuleServiceModelJsonSerializer;

import java.util.Collection;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class MuleArtifactModelJsonSerializerTestCase {

  private static final String DESCRIPTOR_NAME = "descriptor";
  private static final JsonObject VALID_JSON = new JsonObject();
  private static final JsonParser parser = new JsonParser();
  private static JsonObject json;

  private final AbstractMuleArtifactModelJsonSerializer<MuleDeployableModel> serializer;

  @Rule
  public ExpectedException expectedException = ExpectedException.none();

  @BeforeClass
  public static void setUp() {
    VALID_JSON.addProperty(NAME, "someName");
    VALID_JSON.addProperty(REQUIRED_PRODUCT, MULE.toString());
    VALID_JSON.addProperty(MIN_MULE_VERSION, "someVersion");
    JsonObject id = new JsonObject();
    id.addProperty(ID, "someId");
    VALID_JSON.add(CLASS_LOADER_MODEL_LOADER_DESCRIPTOR, id);
    VALID_JSON.add(BUNDLE_DESCRIPTOR_LOADER, id);
  }

  @Before
  public void resetJson() {
    json = parser.parse(VALID_JSON.toString()).getAsJsonObject();
  }

  @Parameterized.Parameters(name = "{0}")
  public static Collection<Object[]> data() {
    return asList(new Object[][] {
        {new MuleApplicationModelJsonSerializer()},
        {new MuleDomainModelJsonSerializer()}
    });
  }

  public MuleArtifactModelJsonSerializerTestCase(AbstractMuleArtifactModelJsonSerializer serializer) {
    this.serializer = serializer;
  }

  @Test
  public void redeploymentEnabledDefaultIfNotPresentIsTrue() {
    MuleDeployableModel muleArtifactModel = this.serializer.deserialize("{}");
    assertThat(muleArtifactModel.isRedeploymentEnabled(), is(true));
  }

  @Test
  public void configsAttributeIsNullIfNotPresent() throws Exception {
    MuleDeployableModel muleArtifactModel = this.serializer.deserialize("{}");
    assertThat(muleArtifactModel.getConfigs(), is(nullValue()));
  }

  @Test
  public void configsAttributeIsEmptyIfEmptyInSerialization() throws Exception {
    MuleDeployableModel muleArtifactModel = this.serializer.deserialize("{\'configs\':[]}");
    assertThat(muleArtifactModel.getConfigs(), is(notNullValue()));
    assertThat(muleArtifactModel.getConfigs(), is(empty()));
  }

  @Test
  public void logConfigFileAttributeIsDeserializedCorrectly() throws Exception {
    String logConfigFile = "../../../test-classes/resources/logging/custom-log4j2.xml";
    MuleDeployableModel muleArtifactModel = this.serializer.deserialize("{\"logConfigFile\":\"" + logConfigFile + "\"}");
    assertThat(muleArtifactModel.getLogConfigFile(), is(logConfigFile));
  }

  @Test
  public void mandatoryFieldsCheckIsTrueIfAllPresent() throws Exception {
    MuleDeployableModel muleArtifactModel = this.serializer.deserialize(json.toString());
    muleArtifactModel.validateModel(DESCRIPTOR_NAME);
  }

  @Test
  public void securePropertiesDeserialization() {
    MuleDeployableModel muleArtifactModel = this.serializer.deserialize("{" +
        "\"secureProperties\": [\"db.username\", \"db.password\"]" +
        "}");
    List<String> secretProperties = muleArtifactModel.getSecureProperties();
    assertThat(secretProperties, hasSize(2));
    assertThat(secretProperties, contains("db.username", "db.password"));
  }

  @Test
  public void mandatoryFieldsCheckIsFalseIfNameNotSet() throws Exception {
    json.remove("name");
    MuleDeployableModel muleArtifactModel = this.serializer.deserialize(json.toString());
    expectedException.expect(IllegalStateException.class);
    muleArtifactModel.validateModel(DESCRIPTOR_NAME);
  }

  @Test
  public void mandatoryFieldsCheckIsFalseIfMinMuleVersionNotSet() throws Exception {
    json.remove("minMuleVersion");
    MuleDeployableModel muleArtifactModel = this.serializer.deserialize(json.toString());
    expectedException.expect(IllegalStateException.class);
    muleArtifactModel.validateModel(DESCRIPTOR_NAME);
  }

  @Test
  public void mandatoryFieldsCheckIsFalseIfClassLoaderModelLoaderDescriptorNotSet() throws Exception {
    json.remove("classLoaderModelLoaderDescriptor");
    MuleDeployableModel muleArtifactModel = this.serializer.deserialize(json.toString());
    expectedException.expect(IllegalStateException.class);
    muleArtifactModel.validateModel(DESCRIPTOR_NAME);
  }

  @Test
  public void mandatoryFieldsCheckIsFalseIfBundleDescriptorLoaderNotSet() throws Exception {
    json.remove("bundleDescriptorLoader");
    MuleDeployableModel muleArtifactModel = this.serializer.deserialize(json.toString());
    expectedException.expect(IllegalStateException.class);
    muleArtifactModel.validateModel(DESCRIPTOR_NAME);
  }

  @Test
  public void mandatoryFieldsCheckIsFalseIfClassLoaderModelLoaderDescriptorWithNoId() throws Exception {
    json.remove("classLoaderModelLoaderDescriptor");
    json.add("classLoaderModelLoaderDescriptor", new JsonObject());
    MuleDeployableModel muleArtifactModel = this.serializer.deserialize(json.toString());
    expectedException.expect(IllegalStateException.class);
    muleArtifactModel.validateModel(DESCRIPTOR_NAME);
  }

  @Test
  public void mandatoryFieldsCheckIsFalseIfBundleDescriptorWithNoId() throws Exception {
    json.remove("bundleDescriptorLoader");
    json.add("bundleDescriptorLoader", new JsonObject());
    MuleDeployableModel muleArtifactModel = this.serializer.deserialize(json.toString());
    expectedException.expect(IllegalStateException.class);
    muleArtifactModel.validateModel(DESCRIPTOR_NAME);
  }

  @Test
  public void validateServerPluginModelMandatoryFields() throws Exception {
    MuleServerPluginModelJsonSerializer serializer = new MuleServerPluginModelJsonSerializer();
    MuleServerPluginModel muleServerPluginModel = serializer.deserialize(json.toString());
    try {
      muleServerPluginModel.validateModel(DESCRIPTOR_NAME);
      fail("Descriptor validation should have failed because of missing pluginClassName");
    } catch (Exception e) {
    }
    json.addProperty("pluginClassName", "somePluginClassName");
    muleServerPluginModel = serializer.deserialize(json.toString());
    muleServerPluginModel.validateModel(DESCRIPTOR_NAME);
  }

  @Test
  public void validateServiceModelMandatoryFields() throws Exception {
    MuleServiceModelJsonSerializer serializer = new MuleServiceModelJsonSerializer();
    MuleServiceModel muleServiceModel = serializer.deserialize(json.toString());
    try {
      muleServiceModel.validateModel(DESCRIPTOR_NAME);
      fail("Descriptor validation should have failed because of missing contract");
    } catch (Exception expected) {
    }
    JsonObject contract = new JsonObject();
    JsonArray contracts = new JsonArray();
    contracts.add(contract);
    json.add("contracts", contracts);
    muleServiceModel = serializer.deserialize(json.toString());
    try {
      muleServiceModel.validateModel(DESCRIPTOR_NAME);
      fail("Descriptor validation should have failed because of contract missing properties");
    } catch (Exception expected) {
    }

    contract.addProperty("serviceProviderClassName", "FooProvider");
    muleServiceModel = serializer.deserialize(json.toString());

    try {
      muleServiceModel.validateModel(DESCRIPTOR_NAME);
      fail("Descriptor validation should still have failed because of contract missing properties");
    } catch (Exception expected) {
    }

    contract.addProperty("contractClassName", "FooService");
    muleServiceModel = serializer.deserialize(json.toString());
    muleServiceModel.validateModel(DESCRIPTOR_NAME);
  }
}
