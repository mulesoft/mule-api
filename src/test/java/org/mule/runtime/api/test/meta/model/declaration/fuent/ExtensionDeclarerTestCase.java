/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.test.meta.model.declaration.fuent;

import static java.util.Optional.of;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.mule.metadata.api.annotation.TypeIdAnnotation;
import org.mule.metadata.api.model.ObjectType;
import org.mule.metadata.api.visitor.MetadataTypeVisitor;
import org.mule.runtime.api.meta.Category;
import org.mule.runtime.api.meta.model.declaration.fluent.ConstructDeclaration;
import org.mule.runtime.api.meta.model.declaration.fluent.ConstructDeclarer;
import org.mule.runtime.api.meta.model.declaration.fluent.ExtensionDeclarer;
import org.mule.runtime.api.meta.model.declaration.fluent.OperationDeclaration;
import org.mule.runtime.api.meta.model.declaration.fluent.OperationDeclarer;
import org.mule.runtime.api.meta.model.declaration.fluent.SourceDeclaration;
import org.mule.runtime.api.meta.model.declaration.fluent.SourceDeclarer;

import java.util.HashSet;
import java.util.Set;

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

  @Test
  public void setExtensionName() {
    ExtensionDeclarer declarer = new ExtensionDeclarer();
    String extensionName = "TestExtension";

    declarer.named(extensionName);

    assertThat(declarer.getDeclaration().getName(), is(extensionName));
  }

  @Test
  public void setExtensionVersion() {
    ExtensionDeclarer declarer = new ExtensionDeclarer();
    String version = "1.0.0";

    declarer.onVersion(version);

    assertThat(declarer.getDeclaration().getVersion(), is(version));
  }

  @Test
  public void setExtensionDescription() {
    ExtensionDeclarer declarer = new ExtensionDeclarer();
    String description = "This is a test extension.";

    declarer.describedAs(description);

    assertThat(declarer.getDeclaration().getDescription(), is(description));
  }

  @Test
  public void addPrivilegedPackage() {
    ExtensionDeclarer declarer = new ExtensionDeclarer();
    String packageName = "org.mule.runtime.privileged";

    declarer.withPrivilegedPackage(packageName);

    assertThat(declarer.getDeclaration().getPrivilegedPackages(), hasItem(packageName));
  }

  @Test
  public void addPrivilegedArtifact() {
    ExtensionDeclarer declarer = new ExtensionDeclarer();
    String artifactId = "privileged-artifact";

    declarer.withPrivilegedArtifact(artifactId);

    assertThat(declarer.getDeclaration().getPrivilegedArtifacts(), hasItem(artifactId));
  }

  @Test
  public void addSupportedJavaVersions() {
    ExtensionDeclarer declarer = new ExtensionDeclarer();
    Set<String> javaVersions = new HashSet<>();
    javaVersions.add("11");
    javaVersions.add("17");

    declarer.supportingJavaVersions(javaVersions);

    assertThat(declarer.getDeclaration().getSupportedJavaVersions(), is(javaVersions));
  }

  @Test
  public void setExtensionCategory() {
    ExtensionDeclarer declarer = new ExtensionDeclarer();
    Category category = Category.COMMUNITY;

    declarer.withCategory(category);

    assertThat(declarer.getDeclaration().getCategory(), is(category));
  }

  @Test
  public void handleNullExtensionName() {
    ExtensionDeclarer declarer = new ExtensionDeclarer();

    declarer.named(null);

    assertThat(declarer.getDeclaration().getName(), is(nullValue()));
  }

  @Test
  public void handleEmptyExtensionDescription() {
    ExtensionDeclarer declarer = new ExtensionDeclarer();
    String description = "";

    declarer.describedAs(description);

    assertThat(declarer.getDeclaration().getDescription(), is(description));
  }

  @Test
  public void handleDuplicatePrivilegedPackages() {
    ExtensionDeclarer declarer = new ExtensionDeclarer();
    String packageName = "org.mule.runtime.privileged";

    declarer.withPrivilegedPackage(packageName);
    declarer.withPrivilegedPackage(packageName);

    assertThat(declarer.getDeclaration().getPrivilegedPackages().size(), is(1));
  }

  @Test
  public void handleEmptySupportedJavaVersions() {
    ExtensionDeclarer declarer = new ExtensionDeclarer();
    Set<String> javaVersions = new HashSet<>();

    declarer.supportingJavaVersions(javaVersions);

    assertThat(declarer.getDeclaration().getSupportedJavaVersions().isEmpty(), is(true));
  }

  @Test
  public void handleNullCategory() {
    ExtensionDeclarer declarer = new ExtensionDeclarer();

    declarer.withCategory(null);

    assertThat(declarer.getDeclaration().getCategory(), is(nullValue()));
  }

  @Test
  public void testWithConstruct() {
    ConstructDeclarer constructDeclarer = mock(ConstructDeclarer.class);
    ConstructDeclaration constructDeclaration = mock(ConstructDeclaration.class);
    when(constructDeclarer.getDeclaration()).thenReturn(constructDeclaration);
    ExtensionDeclarer declarer = new ExtensionDeclarer();

    declarer.withConstruct(constructDeclarer);

    assertThat(declarer.getDeclaration().getConstructs(), hasItem(constructDeclaration));
  }

  @Test
  public void testWithOperation() {
    OperationDeclarer operationDeclarer = mock(OperationDeclarer.class);
    OperationDeclaration operationDeclaration = mock(OperationDeclaration.class);
    when(operationDeclarer.getDeclaration()).thenReturn(operationDeclaration);
    ExtensionDeclarer declarer = new ExtensionDeclarer();

    declarer.withOperation(operationDeclarer);

    assertThat(declarer.getDeclaration().getOperations(), hasItem(operationDeclaration));
  }

  @Test
  public void testWithMessageSource() {
    SourceDeclarer sourceDeclarer = mock(SourceDeclarer.class);
    SourceDeclaration sourceDeclaration = mock(SourceDeclaration.class);
    when(sourceDeclarer.getDeclaration()).thenReturn(sourceDeclaration);
    ExtensionDeclarer declarer = new ExtensionDeclarer();

    declarer.withMessageSource(sourceDeclarer);

    assertThat(declarer.getDeclaration().getMessageSources(), hasItem(sourceDeclaration));
  }
}
