/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.metadata;

import static java.util.Collections.emptyMap;
import static java.util.Collections.list;
import static java.util.Optional.ofNullable;
import static org.mule.metadata.internal.utils.StringUtils.isNotEmpty;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import javax.activation.MimeType;
import javax.activation.MimeTypeParseException;

/**
 * Immutable representation of Media Types as defined in
 * <a href="https://www.ietf.org/rfc/rfc2046.txt">RFC-2046 Part Two</a>.
 * <p>
 * Also provides constants for common media types used in Mule.
 *
 * @since 1.0
 */
public final class MediaType implements Serializable {

  private static final long serialVersionUID = -3626429370741009489L;

  private static final String CHARSET_PARAM = "charset";

  private static final String TYPE_TEXT = "text";
  private static final String TYPE_APPLICATION = "application";
  private static final String TYPE_MULTIPART = "multipart";
  private static final String SUBTYPE_JSON = "json";
  private static final String SUBTYPE_XML = "xml";
  private static final String SUBTYPE_PLAIN = "plain";
  private static final String SUBTYPE_HTML = "html";
  private static final String SUBTYPE_OCTET_STREAM = "octet-stream";
  private static final String SUBTYPE_MIXED = "mixed";
  private static final String SUBTYPE_RELATED = "related";

  public static final MediaType ANY = create("*", "*");

  public static final MediaType JSON = create(TYPE_TEXT, SUBTYPE_JSON);
  public static final MediaType APPLICATION_JSON = create(TYPE_APPLICATION, SUBTYPE_JSON);
  public static final MediaType APPLICATION_JAVA = create(TYPE_APPLICATION, "java");
  public static final MediaType ATOM = create(TYPE_APPLICATION, "atom+" + SUBTYPE_XML);
  public static final MediaType RSS = create(TYPE_APPLICATION, "rss+" + SUBTYPE_XML);
  public static final MediaType APPLICATION_XML = create(TYPE_APPLICATION, SUBTYPE_XML);
  public static final MediaType XML = create(TYPE_TEXT, SUBTYPE_XML);
  public static final MediaType TEXT = create(TYPE_TEXT, SUBTYPE_PLAIN);
  public static final MediaType HTML = create(TYPE_TEXT, SUBTYPE_HTML);

  public static final MediaType BINARY = create(TYPE_APPLICATION, SUBTYPE_OCTET_STREAM);
  public static final MediaType UNKNOWN = create("content", "unknown");
  public static final MediaType MULTIPART_MIXED = create(TYPE_MULTIPART, SUBTYPE_MIXED);
  public static final MediaType MULTIPART_RELATED = create(TYPE_MULTIPART, SUBTYPE_RELATED);
  public static final MediaType MULTIPART_X_MIXED_REPLACE = create(TYPE_MULTIPART, "x-" + SUBTYPE_MIXED + "-replace");

  private final String primaryType;
  private final String subType;
  private final Map<String, String> params;
  private transient Charset charset;

  /**
   * Parses a media type from its string representation.
   *
   * @param mediaType String representation to be parsed
   * @throws IllegalArgumentException if the {@code mimeType} cannot be parsed.
   * @return {@link MediaType} instance for the parsed {@code mediaType} string.
   */
  public static MediaType parse(String mediaType) {
    try {
      MimeType mimeType = new MimeType(mediaType);

      String charsetParam = mimeType.getParameter(CHARSET_PARAM);
      Charset charset = isNotEmpty(charsetParam) ? Charset.forName(charsetParam) : null;

      Map<String, String> params = new HashMap<>();
      for (String paramName : (List<String>) list(mimeType.getParameters().getNames())) {
        if (!CHARSET_PARAM.equals(paramName)) {
          params.put(paramName, mimeType.getParameter(paramName));
        }
      }

      return new MediaType(mimeType.getPrimaryType(), mimeType.getSubType(), params, charset);
    } catch (MimeTypeParseException e) {
      throw new IllegalArgumentException("MediaType cannot be parsed: " + mediaType, e);
    }
  }

  /**
   * Returns a media-type for. This would be the equivalent of the media type {@code "{primaryType}/{subType}"}.
   *
   * @param primaryType the left part of the represented type.
   * @param subType the right part of the represented type.
   * @return {@link MediaType} instance for given parameters.
   */
  public static MediaType create(String primaryType, String subType) {
    return new MediaType(primaryType, subType, emptyMap(), null);
  }

  /**
   * Returns a media-type for the given parameters. This would be the equivalent of the media type
   * {@code "{primaryType}/{subType}[; charset={charset}]"}.
   *
   * @param primaryType the left part of the represented type.
   * @param subType the right part of the represented type.
   * @param charset the value of the {@code charset} parameter.
   * @return {@link MediaType} instance for given parameters.
   */
  public static MediaType create(String primaryType, String subType, Charset charset) {
    return new MediaType(primaryType, subType, emptyMap(), charset);
  }

  private MediaType(String primaryType, String subType, Map<String, String> params, Charset charset) {
    this.primaryType = primaryType;
    this.subType = subType;
    this.params = params;
    this.charset = charset;
  }

  /**
   * Creates a new {@link MediaType} instance keeping the {@code type} and {@code sub-type} but replacing the
   * {@code charset} with the value passed.
   *
   * @param charset the new charset to use or {@code null} to clear the current {@code charset}.
   * @return new immutable {@link MediaType} instance.
   */
  public MediaType withCharset(Charset charset) {
    return new MediaType(this.getPrimaryType(), this.getSubType(), params, charset);
  }

  /**
   * Creates a new {@link MediaType} instance keeping the {@code type} and {@code sub-type} but removing all the
   * parameters (like the {@code charset})
   *
   * @return new immutable {@link MediaType} instance.
   */
  public MediaType withoutParameters() {
    return create(this.getPrimaryType(), this.getSubType());
  }

  /**
   * @return The left part of the represented type.
   */
  public String getPrimaryType() {
    return primaryType;
  }

  /**
   * @return The right part of the represented type.
   */
  public String getSubType() {
    return subType;
  }

  /**
   * @return The value of the {@code charset} parameter.
   *         <p>
   *         This may be not set, in which case it is up to the caller to determine the
   *         appropriate charset to use.
   */
  public Optional<Charset> getCharset() {
    return ofNullable(charset);
  }

  /**
   * @param paramName the name of the parameter to get.
   * @return The value of the {@code paramName} parameter.
   */
  public String getParameter(String paramName) {
    return params.get(paramName);
  }

  /**
   * Evaluates the type of this object against the ones of the {@code other} {@link MediaType}.
   * <p>
   * This will ignore any optional parameters, such as the charset.
   * 
   * @param other The {@link MediaType} to evaluate against.
   * @return {@code true} if the types are the same.
   */
  public boolean matches(MediaType other) {
    return Objects.equals(primaryType, other.primaryType)
        && Objects.equals(subType, other.subType);
  }

  /**
   * @return a textual representation of this object, in format {@code "{primaryType}/{subType}[; charset={charset}][;
   *         {params}]"}.
   */
  public String toRfcString() {
    final StringBuilder buffer = new StringBuilder();
    params.forEach((k, v) -> {
      buffer.append("; " + k + "=\"" + v + "\"");
    });

    return primaryType + "/" + subType
        + (getCharset().isPresent() ? "; charset=" + getCharset().get().name() : "")
        + (!params.isEmpty() ? buffer.toString() : "");
  }

  /**
   * @return a textual representation of this object.
   */
  @Override
  public String toString() {
    return toRfcString();
  }

  @Override
  public int hashCode() {
    // TODO MULE-9987 Check if it is actually needed to leave charset and params out.
    return Objects.hash(primaryType, subType);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (obj == this) {
      return true;
    }
    if (obj.getClass() != getClass()) {
      return false;
    }
    MediaType other = (MediaType) obj;

    return Objects.equals(primaryType, other.primaryType)
        && Objects.equals(subType, other.subType)
        && Objects.equals(params, other.params)
        && Objects.equals(charset, other.charset);
  }

  private void readObject(ObjectInputStream in) throws Exception {
    in.defaultReadObject();
    String charsetStr = (String) in.readObject();
    if (charsetStr != null) {
      charset = Charset.forName(charsetStr);
    }
  }

  private void writeObject(ObjectOutputStream out) throws Exception {
    out.defaultWriteObject();
    if (charset != null) {
      out.writeObject(charset.name());
    } else {
      out.writeObject(null);
    }
  }
}
