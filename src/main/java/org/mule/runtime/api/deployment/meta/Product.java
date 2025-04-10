/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.deployment.meta;

import static java.lang.String.format;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;

import java.util.List;

/**
 * The set of products available
 *
 * @since 1.0
 */
public enum Product {

  /**
   * Mule Runtime CE Edition
   */
  MULE(emptyList()),

  /**
   * Mule Runtime EE Edition
   */
  MULE_EE(asList(MULE));

  private List<Product> supportedProducts;

  /**
   * @param supportedProducts list of supported products
   */
  Product(List<Product> supportedProducts) {
    this.supportedProducts = supportedProducts;
  }

  /**
   *
   * @param product the product to verify if it's supported.
   * @return true if the product is supported, false otherwise
   */
  public boolean supports(Product product) {
    return product.equals(this) || supportedProducts.stream().filter(product::equals).findAny()
        .isPresent();
  }

  /**
   * @param coreLibraryName the core library name
   * @return the product represented by the core library name
   */
  public static Product getProductByName(String coreLibraryName) {
    // this only happens during test cases.
    if (coreLibraryName == null) {
      return MULE_EE;
    }
    if (coreLibraryName.equals("Mule Core")
        || coreLibraryName.equals("Mule 4 Runtime Extension model")
        || coreLibraryName.equals("Mule Manifest")) {
      return MULE;
    } else if (coreLibraryName.equals("Mule EE Core")
        || coreLibraryName.equals("Mule 4 EE Runtime Extension model")
        || coreLibraryName.equals("Mule EE Manifest")) {
      return MULE_EE;
    }
    throw new IllegalArgumentException(format("Core library name %s could not be recognized", coreLibraryName));
  }
}
