/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.message;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Represents a payload of a {@link Message} composed of many different parts. Each parts is in itself a {@link Message}, and has
 * {@code attributes} specific to those parts (such as the headers of a single HTTP part).
 * 
 * @since 1.0
 */
public interface MultiPartPayload {

  /**
   * @return the contained parts.
   */
  List<Message> getParts();

  /**
   * @return the names of the contained parts.
   */
  List<String> getPartNames();

  /**
   * Looks up the part with the passed {@code partName}.
   * 
   * @param partName the name of the part to look for.
   * @return the part with the given name.
   * @throws NoSuchElementException if no part with the given name exists.
   */
  Message getPart(String partName);

  /**
   * @return the contained parts by their given name.
   */
  Map<String, Message> getNamedParts();

}
