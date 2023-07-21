/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.test.util;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;
import static org.mule.runtime.api.util.NameUtils.COMPONENT_NAME_SEPARATOR;

import org.mule.runtime.api.util.NameUtils;

import org.junit.Test;

public class NameUtilsTestCase {


  @Test
  public void hyphenize() {
    assertThat(NameUtils.hyphenize("listenerConfig"), is("listener-config"));
    assertThat(NameUtils.hyphenize("listener-Config"), is("listener-config"));
    assertThat(NameUtils.hyphenize("listener_Config"), is("listener_config"));
    assertThat(NameUtils.hyphenize("LISTENER-CONFIG"), is("listener-config"));
  }

  @Test
  public void underscorize() {
    assertThat(NameUtils.underscorize("LISTENER_CONFIG"), is("listener_config"));
    assertThat(NameUtils.underscorize("listener_config"), is("listener_config"));
    assertThat(NameUtils.underscorize("listener-Config"), is("listener-config"));
  }

  @Test
  public void toCamelCase() {
    assertThat(NameUtils.toCamelCase("LISTENER-CONFIG", COMPONENT_NAME_SEPARATOR), is("listenerConfig"));
    assertThat(NameUtils.toCamelCase("listener_config", "_"), is("listenerConfig"));
    assertThat(NameUtils.toCamelCase("listener-Config", COMPONENT_NAME_SEPARATOR), is("listenerConfig"));
    assertThat(NameUtils.toCamelCase("another-CONFIG-StyLE", COMPONENT_NAME_SEPARATOR), is("anotherConfigStyle"));
    assertThat(NameUtils.toCamelCase("listener", COMPONENT_NAME_SEPARATOR), is("listener"));
    assertThat(NameUtils.toCamelCase(null, COMPONENT_NAME_SEPARATOR), nullValue());
  }

  @Test
  public void titleize() {
    assertThat(NameUtils.titleize("this is a service"), is("This Is A Service"));
    assertThat(NameUtils.titleize("ThisIsAService"), is("This Is A Service"));
    assertThat(NameUtils.titleize("Route-66-Service"), is("Route 66 Service"));
    assertThat(NameUtils.titleize("AN_AMAZING_SERVICE"), is("An Amazing Service"));
    assertThat(NameUtils.titleize("Issue_Management"), is("Issue Management"));
  }
}
