/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.test.component;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import org.mule.runtime.api.component.ComponentIdentifier;
import org.mule.runtime.api.component.DefaultComponentIdentifier;

import io.qameta.allure.Issue;
import org.junit.Test;

public class DefaultComponentIdentifierTestCase {

  @Test
  @Issue("W-16237424")
  public void testBuilderWithValidParametersWithLeadingWhiteSpace() {
    String namespace = " namespace";
    String name = " name";

    ComponentIdentifier componentIdentifier = DefaultComponentIdentifier.builder().namespace(namespace).name(name).build();

    assertThat(componentIdentifier.getName(), is("name"));
    assertThat(componentIdentifier.getNamespace(), is("namespace"));
  }

  @Test
  @Issue("W-16237424")
  public void testBuilderWithValidParametersWithTrailingWhiteSpace() {
    String namespace = "namespace   ";
    String name = "name    ";

    ComponentIdentifier componentIdentifier = DefaultComponentIdentifier.builder().namespace(namespace).name(name).build();

    assertThat(componentIdentifier.getName(), is("name"));
    assertThat(componentIdentifier.getNamespace(), is("namespace"));
  }
}
