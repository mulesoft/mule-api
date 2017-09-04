/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mule.runtime.api.meta.model.stereotype.StereotypeModelBuilder.newStereotype;
import org.mule.runtime.api.meta.model.stereotype.StereotypeModel;

import org.junit.Test;

public class StereotypesTestCase {

  private StereotypeModel rootA = newStereotype("root", "A").build();
  private StereotypeModel rootB = newStereotype("root", "B").build();

  private StereotypeModel midA = newStereotype("mid", "A").withParent(rootA).build();
  private StereotypeModel midB = newStereotype("mid", "B").withParent(rootB).build();

  private StereotypeModel leafA = newStereotype("leaf", "A").withParent(midA).build();
  private StereotypeModel leafB = newStereotype("leaf", "B").withParent(midB).build();

  @Test
  public void assignableEquals() throws Exception {
    assertThat(rootA.isAssignableTo(rootA), is(true));
    assertThat(midA.isAssignableTo(midA), is(true));
    assertThat(leafA.isAssignableTo(leafA), is(true));
  }

  @Test
  public void assignableByParent() throws Exception {
    assertThat(midA.isAssignableTo(rootA), is(true));
    assertThat(leafA.isAssignableTo(rootA), is(true));
    assertThat(leafA.isAssignableTo(midA), is(true));
  }

  @Test
  public void nonAssignabletoOthersParent() throws Exception {
    assertThat(midA.isAssignableTo(midB), is(false));
    assertThat(leafA.isAssignableTo(leafB), is(false));
    assertThat(leafA.isAssignableTo(midB), is(false));
  }

  @Test
  public void nonAssignableDifferentLeaf() throws Exception {
    assertThat(midA.isAssignableTo(leafA), is(false));

    StereotypeModel otherLeaf = newStereotype("other", "A").withParent(midA).build();
    assertThat(leafA.isAssignableTo(otherLeaf), is(false));
  }
}
