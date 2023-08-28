/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.test.el;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import org.mule.runtime.api.el.ModuleNamespace;

import org.junit.Test;

public class ModuleNamespaceTestCase {

  @Test
  public void shouldNormalizeNamespaceFromCamelCase() throws Exception {
    assertThat(new ModuleNamespace("myCamelCase").toString(), is("MyCamelCase"));
  }

  @Test
  public void shouldNormalizeNamespaceFromHyphenized() throws Exception {
    assertThat(new ModuleNamespace("my-hyphen-Case").toString(), is("MyHyphenCase"));
  }

  @Test
  public void shouldNormalizeNamespaceWithNumbers() throws Exception {
    assertThat(new ModuleNamespace("my 29 name I18n with  num 3 rs or numb3rs").toString(),
               is("My29NameI18nWithNum3RsOrNumb3rs"));
  }

  @Test
  public void shouldNormalizeNamespaceFromBlanks() throws Exception {
    assertThat(new ModuleNamespace("my", "name", "I18n with  blanks").toString(), is("my::name::I18nWithBlanks"));
  }

  @Test
  public void shouldNormalizeNamespace() throws Exception {
    assertThat(new ModuleNamespace("_prefix", "munit-tools", "myName-with  22", "1o", "20", "mixedContent-pascal").toString(),
               is("prefix::munittools::mynamewith22::1o::20::MixedContentPascal"));
  }


}
