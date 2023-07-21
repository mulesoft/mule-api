/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.meta;

/**
 * Enum that stores a list of valid categories that a plugin can be assigned to.
 * <p>
 * <ul>
 * <li>{@link Category#COMMUNITY}: Represents that the plugin does not requires an Enterprise Mule Runtime to work.</li>
 * <li>{@link Category#SELECT}: Represents that the plugin requires an Enterprise Mule Runtime to work.</li>
 * <li>{@link Category#PREMIUM}: Represents that the plugin requires an Enterprise Mule Runtime to work and also an plugin
 * entitlement to work</li>
 * <li>{@link Category#CERTIFIED}: Represent that the plugin is MuleSoft certified and requires an Enterprise Mule Runtime to
 * work</li>
 * </ul>
 *
 * @since 1.0
 */
public enum Category {
  COMMUNITY, SELECT, PREMIUM, CERTIFIED
}
