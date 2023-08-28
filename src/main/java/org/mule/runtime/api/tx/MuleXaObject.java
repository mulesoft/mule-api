/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.tx;

/**
 * Contract for objects to be considered as XA Objects.
 *
 * @since 1.0
 */
public interface MuleXaObject {

  void close() throws Exception;

  void setReuseObject(boolean reuseObject);

  boolean isReuseObject();

  boolean enlist() throws TransactionException;

  boolean delist() throws Exception;

  /**
   * Get XAConnection or XASession from wrapper / proxy
   *
   * @return return javax.sql.XAConnection for jdbc or javax.jms.XASession for jms
   */
  Object getTargetObject();

  String SET_REUSE_OBJECT_METHOD_NAME = "setReuseObject";
  String IS_REUSE_OBJECT_METHOD_NAME = "isReuseObject";
  String DELIST_METHOD_NAME = "delist";
  String ENLIST_METHOD_NAME = "enlist";
  String GET_TARGET_OBJECT_METHOD_NAME = "getTargetObject";
  String CLOSE_METHOD_NAME = "close";
}
