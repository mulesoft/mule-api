/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.tx;

import org.mule.runtime.api.config.DatabasePoolingProfile;

import javax.sql.DataSource;

/**
 * Decorates {@link DataSource} if required in order to work with XA transactions
 * 
 * @since 1.0
 */
public interface DataSourceDecorator {

  /**
   * Decorates a dataSource
   *
   * @param dataSource       dataSource to decorate. Non null
   * @param dataSourceName   dataSource bean name
   * @param dbPoolingProfile pooling profile use to create the wrapped dataSource
   * @return
   */
  DataSource decorate(DataSource dataSource, String dataSourceName, DatabasePoolingProfile dbPoolingProfile);

  /**
   * Indicates whether or not this decorator can decorate a given datasource
   *
   * @param dataSource dataSource to check
   * @return whether the {@code dataSource} can be decorated by {@code this} instance
   */
  boolean appliesTo(DataSource dataSource);

}
