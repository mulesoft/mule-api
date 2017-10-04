/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.deployment.persistence;

import static java.util.Arrays.asList;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import org.mule.runtime.api.deployment.meta.MuleDeployableModel;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class MuleArtifactModelJsonSerializerTestCase {

  private final AbstractMuleArtifactModelJsonSerializer serializer;

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

}
