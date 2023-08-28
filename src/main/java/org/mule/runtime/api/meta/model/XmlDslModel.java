/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model;

/**
 * Represents an extension's capability to be mapped and usable from a Mule XML config.
 * <p>
 * Instances of this interface are to be created by the builder obtained through {@link #builder()}
 *
 * @since 1.0
 */
public final class XmlDslModel {

  /**
   * A builder which allows creating instances of {@link XmlDslModel}
   *
   * @since 1.0
   */
  public static final class XmlDslModelBuilder {

    private XmlDslModel product = new XmlDslModel();

    private XmlDslModelBuilder() {}

    /**
     * Sets the version of the schema
     *
     * @param schemaVersion the version
     * @return {@code this} builder
     */
    public XmlDslModelBuilder setSchemaVersion(String schemaVersion) {
      product.schemaVersion = schemaVersion;
      return this;
    }

    /**
     * Sets the extension's prefix
     *
     * @param prefix the schema prefix
     * @return {@code this} builder
     */
    public XmlDslModelBuilder setPrefix(String prefix) {
      product.prefix = prefix;
      return this;
    }

    /**
     * Sets the namespace
     *
     * @param namespace the namespace
     * @return {@code this} builder
     */
    public XmlDslModelBuilder setNamespace(String namespace) {
      product.namespace = namespace;
      return this;
    }

    /**
     * Sets the XSD file name
     *
     * @param xsdFileName the name of the XSD schema
     * @return {@code this} builder
     */
    public XmlDslModelBuilder setXsdFileName(String xsdFileName) {
      product.xsdFileName = xsdFileName;
      return this;
    }

    /**
     * The location of the schema
     *
     * @param schemaLocation the schema location
     * @return {@code this} builder
     */
    public XmlDslModelBuilder setSchemaLocation(String schemaLocation) {
      product.schemaLocation = schemaLocation;
      return this;
    }

    /**
     * @return the generated model
     */
    public XmlDslModel build() {
      return product;
    }
  }

  /**
   * @return a new {@link XmlDslModelBuilder}
   */
  public static XmlDslModelBuilder builder() {
    return new XmlDslModelBuilder();
  }

  private String xsdFileName = "";
  private String schemaVersion = "";
  private String prefix = "";
  private String namespace = "";
  private String schemaLocation = "";

  /**
   * @return The version of the schema. Defaults to 1.0.
   */
  public String getSchemaVersion() {
    return schemaVersion;
  }

  /**
   * @return The extension's prefix
   */
  public String getPrefix() {
    return prefix;
  }

  /**
   * @return The namespace URI
   */
  public String getNamespace() {
    return namespace;
  }

  /**
   * @return The name of the schema file
   */
  public String getXsdFileName() {
    return xsdFileName;
  }

  /**
   * @return the location for the XSD schema
   */
  public String getSchemaLocation() {
    return schemaLocation;
  }
}
