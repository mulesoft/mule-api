/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.test.meta.model.declaration.fuent;

import static java.util.Optional.of;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.mule.metadata.api.annotation.TypeIdAnnotation;
import org.mule.metadata.api.model.ObjectType;
import org.mule.metadata.api.visitor.MetadataTypeVisitor;
import org.mule.runtime.api.meta.model.declaration.fluent.ExtensionDeclarer;

import org.junit.Test;

import io.qameta.allure.Issue;

public class ExtensionDeclarerTestCase {

  @Test
  @Issue("MULE-19701")
  public void registerGatewayTypes() {
    String gwTypeFqcn = "com.mulesoft.mule.runtime.gw.GatewayType";
    ObjectType gwObjectType = mock(ObjectType.class);
    when(gwObjectType.getAnnotation(TypeIdAnnotation.class))
        .thenReturn(of(new TypeIdAnnotation(gwTypeFqcn)));

    doAnswer(inv -> {
      MetadataTypeVisitor v = inv.getArgument(0);
      v.visitObject((ObjectType) inv.getMock());
      return null;
    }).when(gwObjectType).accept(any());

    ExtensionDeclarer declarer = new ExtensionDeclarer();
    declarer.withType(gwObjectType);

    assertThat(declarer.getDeclaration().getTypeById(gwTypeFqcn), sameInstance(gwObjectType));
  }
}
