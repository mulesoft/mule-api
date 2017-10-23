/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.util;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;
import static org.mule.runtime.api.util.NameUtils.COMPONENT_NAME_SEPARATOR;

import org.hamcrest.core.IsNull;
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

}
