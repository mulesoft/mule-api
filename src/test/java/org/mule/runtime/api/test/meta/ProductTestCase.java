/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.test.meta;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mule.runtime.api.deployment.meta.Product.MULE;
import static org.mule.runtime.api.deployment.meta.Product.MULE_EE;
import static org.mule.runtime.api.deployment.meta.Product.getProductByName;

import org.junit.Test;

public class ProductTestCase {

  @Test
  public void productSupport() {
    assertThat(MULE.supports(MULE), is(true));
    assertThat(MULE.supports(MULE_EE), is(false));
    assertThat(MULE_EE.supports(MULE), is(true));
    assertThat(MULE_EE.supports(MULE_EE), is(true));
  }

  @Test
  public void productByProductName() {
    assertThat(getProductByName(null), is(MULE_EE));
    assertThat(getProductByName("Mule Core"), is(MULE));
    assertThat(getProductByName("Mule EE Core"), is(MULE_EE));
  }

}
