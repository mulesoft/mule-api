/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.i18n;

import java.util.Locale;
import java.util.ResourceBundle;

class ReloadControl {

  // since java 6 only
  public static class Always extends ResourceBundle.Control {

    boolean needsReload = true;

    @Override
    public boolean needsReload(String baseName, Locale locale, String format, ClassLoader loader, ResourceBundle bundle,
                               long loadTime) {
      // don't cache, always reload
      return true;
    }

    @Override
    public long getTimeToLive(String baseName, Locale locale) {
      if (needsReload) {
        // must be zero, as other 'DONT_CACHE' constant doesn't work here, and is -1
        return 0;
      }

      return ResourceBundle.Control.TTL_NO_EXPIRATION_CONTROL;
    }
  }
}
