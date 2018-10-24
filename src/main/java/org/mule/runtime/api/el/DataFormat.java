/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.el;

import java.util.List;

/**
 * Describes a supported Data Format (XML, CSV, etc) by the current expression language
 *
 * @since 1.2.0
 */
public class DataFormat {

  private String defaultMimeType;
  private List<String> supportedMimeTypes;
  private List<DataFormatConfigOption> readerOptions;
  private List<DataFormatConfigOption> writerOptions;
  private boolean isBinaryFormat;


  public DataFormat(String defaultMimeType, List<String> supportedMimeTypes, List<DataFormatConfigOption> readerOptions,
                    List<DataFormatConfigOption> writerOptions, boolean isBinaryFormat) {
    this.defaultMimeType = defaultMimeType;
    this.supportedMimeTypes = supportedMimeTypes;
    this.readerOptions = readerOptions;
    this.writerOptions = writerOptions;
    this.isBinaryFormat = isBinaryFormat;
  }

  /**
   * Returns the default mime type
   *
   * @return The mime type
   */
  public String getDefaultMimeType() {
    return defaultMimeType;
  }

  /**
   * Returns the list of all supported mime types
   *
   * @return The list of supported mime types
   */
  public List<String> getSupportedMimeTypes() {
    return supportedMimeTypes;
  }

  /**
   * Returns the list of all supported reader options
   *
   * @return The list of reader options
   */
  public List<DataFormatConfigOption> getReaderOptions() {
    return readerOptions;
  }

  /**
   * The list of all supported writer options
   *
   * @return The list of writer options
   */
  public List<DataFormatConfigOption> getWriterOptions() {
    return writerOptions;
  }

  /**
   * Returns true if this is data format is represented in a binary representation instead of text
   *
   * @return true if binary
   */
  public boolean isBinaryFormat() {
    return isBinaryFormat;
  }
}
