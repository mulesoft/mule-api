/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.deployment.persistence;

import static java.util.Arrays.asList;
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
import org.mule.runtime.api.deployment.meta.MuleDeployableModel;
import org.mule.runtime.api.deployment.meta.MuleServerPluginModel;
import org.mule.runtime.api.deployment.meta.MuleServiceModel;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.Collection;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class MuleArtifactModelJsonSerializerTestCase {

  private final AbstractMuleArtifactModelJsonSerializer serializer;
  private static final String DESCRIPTOR_NAME = "descriptor";
  private static final JsonObject VALID_JSON = new JsonObject();
  private static final JsonParser parser = new JsonParser();
  private JsonObject json;

  @Rule
  public ExpectedException expectedException = ExpectedException.none();

  @BeforeClass
  public static void setUp() {
    VALID_JSON.addProperty(NAME, "someName");
    VALID_JSON.addProperty(REQUIRED_PRODUCT, "MULE");
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
    AbstractMuleArtifactModelJsonSerializer model = this.serializer;
    MuleDeployableModel muleArtifactModel = (MuleDeployableModel) model.deserialize("{}");
    assertThat(muleArtifactModel.isRedeploymentEnabled(), is(true));
  }

  @Test
  public void configsAttributeIsNullIfNotPresent() throws Exception {
    MuleDeployableModel muleArtifactModel = (MuleDeployableModel) this.serializer.deserialize("{}");
    assertThat(muleArtifactModel.getConfigs(), is(nullValue()));
  }

  @Test
  public void configsAttributeIsEmptyIfEmptyInSerialization() throws Exception {
    MuleDeployableModel muleArtifactModel = (MuleDeployableModel) this.serializer.deserialize("{\'configs\':[]}");
    assertThat(muleArtifactModel.getConfigs(), is(notNullValue()));
    assertThat(muleArtifactModel.getConfigs(), is(empty()));
  }

  @Test
  public void mandatoryFieldsCheckIsTrueIfAllPresent() throws Exception {
    MuleDeployableModel muleArtifactModel = (MuleDeployableModel) this.serializer.deserialize(json.toString());
    muleArtifactModel.validateMandatoryFieldsSet(DESCRIPTOR_NAME);
  }

  @Test
  public void mandatoryFieldsCheckIsFalseIfNameNotSet() throws Exception {
    json.remove("name");
    MuleDeployableModel muleArtifactModel = (MuleDeployableModel) this.serializer.deserialize(json.toString());
    expectedException.expect(IllegalStateException.class);
    muleArtifactModel.validateMandatoryFieldsSet(DESCRIPTOR_NAME);
  }

  @Test
  public void mandatoryFieldsCheckIsFalseIfMinMuleVersionNotSet() throws Exception {
    json.remove("minMuleVersion");
    MuleDeployableModel muleArtifactModel = (MuleDeployableModel) this.serializer.deserialize(json.toString());
    expectedException.expect(IllegalStateException.class);
    muleArtifactModel.validateMandatoryFieldsSet(DESCRIPTOR_NAME);
  }

  @Test
  public void mandatoryFieldsCheckIsFalseIfClassLoaderModelLoaderDescriptorNotSet() throws Exception {
    json.remove("classLoaderModelLoaderDescriptor");
    MuleDeployableModel muleArtifactModel = (MuleDeployableModel) this.serializer.deserialize(json.toString());
    expectedException.expect(IllegalStateException.class);
    muleArtifactModel.validateMandatoryFieldsSet(DESCRIPTOR_NAME);
  }

  @Test
  public void mandatoryFieldsCheckIsFalseIfBundleDescriptorLoaderNotSet() throws Exception {
    json.remove("bundleDescriptorLoader");
    MuleDeployableModel muleArtifactModel = (MuleDeployableModel) this.serializer.deserialize(json.toString());
    expectedException.expect(IllegalStateException.class);
    muleArtifactModel.validateMandatoryFieldsSet(DESCRIPTOR_NAME);
  }

  @Test
  public void mandatoryFieldsCheckIsFalseIfClassLoaderModelLoaderDescriptorWithNoId() throws Exception {
    json.remove("classLoaderModelLoaderDescriptor");
    json.add("classLoaderModelLoaderDescriptor", new JsonObject());
    MuleDeployableModel muleArtifactModel = (MuleDeployableModel) this.serializer.deserialize(json.toString());
    expectedException.expect(IllegalStateException.class);
    muleArtifactModel.validateMandatoryFieldsSet(DESCRIPTOR_NAME);
  }

  @Test
  public void mandatoryFieldsCheckIsFalseIfBundleDescriptorWithNoId() throws Exception {
    json.remove("bundleDescriptorLoader");
    json.add("bundleDescriptorLoader", new JsonObject());
    MuleDeployableModel muleArtifactModel = (MuleDeployableModel) this.serializer.deserialize(json.toString());
    expectedException.expect(IllegalStateException.class);
    muleArtifactModel.validateMandatoryFieldsSet(DESCRIPTOR_NAME);
  }

  @Test
  public void validateServerPluginModelMandatoryFields() throws Exception {
    MuleServerPluginModelJsonSerializer serializer = new MuleServerPluginModelJsonSerializer();
    MuleServerPluginModel muleServerPluginModel = serializer.deserialize(json.toString());
    try {
      muleServerPluginModel.validateMandatoryFieldsSet(DESCRIPTOR_NAME);
      fail();
    } catch (Exception e) {
      //Do nothing
    }
    json.addProperty("pluginClassName", "somePluginClassName");
    muleServerPluginModel = serializer.deserialize(json.toString());
    muleServerPluginModel.validateMandatoryFieldsSet(DESCRIPTOR_NAME);
  }

  @Test
  public void validateServiceModelMandatoryFields() throws Exception {
    MuleServiceModelJsonSerializer serializer = new MuleServiceModelJsonSerializer();
    MuleServiceModel muleServiceModel = serializer.deserialize(json.toString());
    try {
      muleServiceModel.validateMandatoryFieldsSet(DESCRIPTOR_NAME);
    } catch (Exception e) {
      //Do Nothing
    }
    json.addProperty("serviceProviderClassName", "someServiceProviderClassName");
    muleServiceModel = serializer.deserialize(json.toString());
    muleServiceModel.validateMandatoryFieldsSet(DESCRIPTOR_NAME);
  }

}
