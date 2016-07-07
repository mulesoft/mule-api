/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.message;

import java.io.Serializable;

/**
 * The {@code Attributes} attributes object is specifc to the connector that was the source of the current message and
 * is used for obtaining message properties or headers if applicable plus additional information that provides context
 * for the current message such as file size, file name and last modified date for FILE, and origin ip address, query
 * parameters etc. for HTTP.
 * <p>
 * Attribute objects should be both immutable and {@link Serializable}.
 */
public interface Attributes extends Serializable
{


}
