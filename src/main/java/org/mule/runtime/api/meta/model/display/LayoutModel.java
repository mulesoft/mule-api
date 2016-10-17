/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.runtime.api.meta.model.display;

import java.util.Objects;

/**
 * Contains directives about how this parameter should be shown in the UI.
 * <p>
 * Instances should be created through a builder obtained through either the {@link #builder()} or
 * {@link #builderFrom(LayoutModel)}
 *
 * @since 1.0
 */
public final class LayoutModel {

  /**
   * Creates instances of {@link LayoutModel}
   */
  public static final class LayoutModelBuilder {

    private final LayoutModel product = new LayoutModel();

    private LayoutModelBuilder() {}

    private LayoutModelBuilder(LayoutModel template) {
      if (template != null) {
        product.order = template.order;
        product.text = template.text;
        product.query = template.query;
        product.password = template.password;
        product.groupName = template.groupName;
        product.tabName = template.tabName;
      }
    }

    /**
     * Indicates that the model represents a password and should be masked in the UI
     *
     * @return {@code this} builder
     */
    public LayoutModelBuilder asPassword() {
      product.password = true;
      return this;
    }

    /**
     * Indicates that the model should use a multi line string editor
     *
     * @return {@code this} builder
     */
    public LayoutModelBuilder asText() {
      product.text = true;
      return this;
    }

    /**
     * Indicates that the model represents a query and should be treated accordingly in the UI. Invoking this method also sets the
     * model {@link #asText() as text}
     *
     * @return {@code this} builder
     */
    public LayoutModelBuilder asQuery() {
      product.query = true;
      return asText();
    }

    /**
     * Sets the tab element name where the model and its group it's going to be located.
     *
     * @param tabName the name of the tab
     * @return {@code this} builder
     */
    public LayoutModelBuilder tabName(String tabName) {
      product.tabName = tabName;
      return this;
    }

    /**
     * Sets the group element name where the model is going to be located.
     *
     * @param groupName the name of the group
     * @return {@code this} builder
     */
    public LayoutModelBuilder groupName(String groupName) {
      product.groupName = groupName;
      return this;
    }

    /**
     * Sets the order of the model within its group.
     *
     * @param order the position in the group
     * @return {@code this} builder
     */
    public LayoutModelBuilder order(int order) {
      product.order = order;
      return this;
    }

    /**
     * @return The built {@link LayoutModel}
     */
    public LayoutModel build() {
      return product;
    }
  }

  /**
   * @return a new {@link LayoutModelBuilder}
   */
  public static LayoutModelBuilder builder() {
    return new LayoutModelBuilder();
  }

  /**
   * Creates a new {@link LayoutModelBuilder} which state is already initialised to match that of the given {@code template}.
   *
   * @param template a {@link LayoutModel} to be used as a template
   * @return a new {@link LayoutModelBuilder}
   */
  public static LayoutModelBuilder builderFrom(LayoutModel template) {
    return new LayoutModelBuilder(template);
  }

  private boolean password = false;
  private boolean text = false;
  private boolean query = false;
  private int order;
  private String groupName;
  private String tabName;

  private LayoutModel() {}

  /**
   * @return Whether this model represents a password and should be masked in the UI
   */
  public boolean isPassword() {
    return password;
  }

  /**
   * @return Whether this model should use a multi line string editor in the UI or not
   */
  public boolean isText() {
    return text;
  }

  /**
   * @return Whether this model represents a query and should be treated accordingly in the UI
   */
  public boolean isQuery() {
    return query;
  }

  /**
   * @return The order of the model within its group.
   */
  public int getOrder() {
    return order;
  }

  /**
   * @return The group element name where the model is going to be located.
   */
  public String getGroupName() {
    return groupName;
  }

  /**
   * @return The tab element name where the model and its group it's going to be located.
   */
  public String getTabName() {
    return tabName;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof LayoutModel) {
      LayoutModel other = ((LayoutModel) obj);
      return other.isText() == this.isText() &&
          other.isQuery() == this.isQuery() &&
          other.isPassword() == this.isPassword() &&
          other.getOrder() == this.getOrder() &&
          Objects.equals(other.getGroupName(), this.getGroupName()) &&
          Objects.equals(other.getTabName(), this.getTabName());
    }

    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(text, query, password, order, groupName, tabName);
  }
}

