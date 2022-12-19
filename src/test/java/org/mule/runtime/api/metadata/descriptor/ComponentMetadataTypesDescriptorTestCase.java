/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.metadata.descriptor;

import static java.util.Optional.empty;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.sameInstance;
import static org.mockito.Mockito.mock;

import org.mule.metadata.api.model.MetadataType;
import org.mule.runtime.api.metadata.descriptor.ComponentMetadataTypesDescriptor.ComponentMetadataTypesDescriptorBuilder;

import org.junit.Before;
import org.junit.Test;

public class ComponentMetadataTypesDescriptorTestCase {

  private MetadataType inputParamType;
  private MetadataType outputPayloadType;
  private MetadataType outputAttributesType;

  @Before
  public void setUp() {
    inputParamType = mock(MetadataType.class);
    outputPayloadType = mock(MetadataType.class);
    outputAttributesType = mock(MetadataType.class);
  }

  @Test
  public void keepsStaticMetadata() {
    ComponentMetadataTypesDescriptor descriptor = baseDescriptorBuilder(false)
        .keepNonDynamicMetadata(true)
        .build();

    assertThat(descriptor.getInputMetadata("param").get(), sameInstance(inputParamType));
    assertThat(descriptor.getOutputMetadata().get(), sameInstance(outputPayloadType));
    assertThat(descriptor.getOutputAttributesMetadata().get(), sameInstance(outputAttributesType));
  }

  @Test
  public void keepsNotStaticMetadata() {
    ComponentMetadataTypesDescriptor descriptor = baseDescriptorBuilder(false)
        .keepNonDynamicMetadata(false)
        .build();

    assertThat(descriptor.getInputMetadata("param"), is(empty()));
    assertThat(descriptor.getOutputMetadata(), is(empty()));
    assertThat(descriptor.getOutputAttributesMetadata(), is(empty()));
  }

  @Test
  public void keepsDynamicMetadata() {
    ComponentMetadataTypesDescriptor descriptor = baseDescriptorBuilder(true)
        .keepNonDynamicMetadata(true)
        .build();

    assertThat(descriptor.getInputMetadata("param").get(), sameInstance(inputParamType));
    assertThat(descriptor.getOutputMetadata().get(), sameInstance(outputPayloadType));
    assertThat(descriptor.getOutputAttributesMetadata().get(), sameInstance(outputAttributesType));
  }

  @Test
  public void keepsDynamicMetadataButNotStaticMetadata() {
    ComponentMetadataTypesDescriptor descriptor = baseDescriptorBuilder(true)
        .keepNonDynamicMetadata(false)
        .build();

    assertThat(descriptor.getInputMetadata("param").get(), sameInstance(inputParamType));
    assertThat(descriptor.getOutputMetadata().get(), sameInstance(outputPayloadType));
    assertThat(descriptor.getOutputAttributesMetadata().get(), sameInstance(outputAttributesType));
  }

  protected ComponentMetadataTypesDescriptorBuilder baseDescriptorBuilder(boolean isDynamic) {
    return ComponentMetadataTypesDescriptor.builder()
        .withInputMetadataDescriptor(InputMetadataDescriptor.builder()
            .withParameter("param", ParameterMetadataDescriptor.builder("param")
                .dynamic(isDynamic)
                .withType(inputParamType)
                .build())
            .build())
        .withOutputMetadataDescriptor(OutputMetadataDescriptor.builder()
            .withReturnType(TypeMetadataDescriptor.builder()
                .dynamic(isDynamic)
                .withType(outputPayloadType)
                .build())
            .withAttributesType(TypeMetadataDescriptor.builder()
                .dynamic(isDynamic)
                .withType(outputAttributesType)
                .build())
            .build());
  }
}
