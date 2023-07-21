/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.internal.util;

import static org.mule.runtime.api.i18n.I18nMessageFactory.createStaticMessage;
import org.mule.runtime.api.exception.MuleRuntimeException;

import java.io.File;
import java.io.IOException;

public class FileUtils {

  /**
   * Workaround for JDK bug <a href="http://bugs.sun.com/bugdatabase/view_bug.do;:YfiG?bug_id=4117557"> 4117557</a>. More
   * in-context information at <a href="http://mule.mulesoft.org/jira/browse/MULE-1112">MULE-1112</a>
   * <p/>
   * Factory methods correspond to constructors of the <code>java.io.File class</code>. No physical file created in this method.
   *
   * @see File
   */
  public static File newFile(String pathName) {
    try {
      return new File(pathName).getCanonicalFile();
    } catch (IOException e) {
      throw new MuleRuntimeException(createStaticMessage("Unable to create a canonical file for " + pathName),
                                     e);
    }
  }

}
