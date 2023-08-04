/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.el;

import java.util.Set;

/**
 * Describes a supported Data Format (XML, CSV, etc) by the current expression language
 *
 * @since 1.2.0
 */
public interface DataFormat {

  /**
   * Returns the default mime type
   *
   * @return The mime type
   */
  String getDefaultMimeType();

  /**
   * Returns the list of all supported mime types
   *
   * @return The list of supported mime types
   */
  Set<String> getSupportedMimeTypes();

  /**
   * Returns the list of all supported reader options
   *
   * @return The list of reader options
   */
  Set<DataFormatConfigOption> getReaderOptions();

  /**
   * The list of all supported writer options
   *
   * @return The list of writer options
   */
  Set<DataFormatConfigOption> getWriterOptions();

  /**
   * Returns true if this is data format is represented in a binary representation instead of text
   *
   * @return true if binary
   */
  boolean isBinaryFormat();
}
