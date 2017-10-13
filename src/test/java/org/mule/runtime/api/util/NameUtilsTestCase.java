/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.util;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

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

}
