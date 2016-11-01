/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.metadata;


import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.mule.metadata.api.model.NumberType;
import org.mule.metadata.api.model.StringType;
import org.mule.runtime.api.metadata.builder.MuleEventMetadataTypeBuilder;

public class MuleEventMetadataTypeBuilderTestCase {

  @Test
  public void MuleEventBuilderShouldBuildEventCorrectly() {
    MuleEventMetadataTypeBuilder event = MuleEventMetadataType.builder();
    event.withMessage().withPayload().stringType();
    event.addVariable("password").numberType();
    MuleEventMetadataType build = event.build();
    Assert.assertThat(build.getMessageType().getPayloadType(), CoreMatchers.instanceOf(StringType.class));
    Assert.assertTrue(build.getVariableType("password").isPresent());
    Assert.assertThat(build.getVariableType("password").get(), CoreMatchers.instanceOf(NumberType.class));
  }
}
