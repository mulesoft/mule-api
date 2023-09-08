/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.metadata;

import static org.mule.runtime.api.util.MuleSystemProperties.MULE_KNOWN_MEDIA_TYPE_PARAM_NAMES;

import static java.lang.System.getProperty;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.emptyMap;
import static java.util.Collections.list;
import static java.util.Optional.ofNullable;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.activation.MimeType;
import javax.activation.MimeTypeParseException;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

/**
 * Immutable representation of Media Types as defined in <a href="https://www.ietf.org/rfc/rfc2046.txt">RFC-2046 Part Two</a>.
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
  private static final String SUBTYPE_FORM_DATA = "form-data";
  private static final String SUBTYPE_RELATED = "related";

  private static final ConcurrentMap<String, MediaType> cache = new ConcurrentHashMap<>();

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
  public static final MediaType MULTIPART_FORM_DATA = create(TYPE_MULTIPART, SUBTYPE_FORM_DATA);
  public static final MediaType MULTIPART_RELATED = create(TYPE_MULTIPART, SUBTYPE_RELATED);
  public static final MediaType MULTIPART_X_MIXED_REPLACE = create(TYPE_MULTIPART, "x-" + SUBTYPE_MIXED + "-replace");

  private static List<String> KNOWN_PARAM_NAMES =
      getProperty(MULE_KNOWN_MEDIA_TYPE_PARAM_NAMES) != null
          ? asList(getProperty(MULE_KNOWN_MEDIA_TYPE_PARAM_NAMES).split(","))
          : emptyList();

  private final String primaryType;
  private final String subType;
  private final Map<String, String> params;
  private boolean definedInApp = false;

  private transient Object withoutParamsLock = new Object();
  private transient volatile MediaType withoutParams;
  private transient Charset charset;

  private transient String rfcString;

  /**
   * Parses a media type from its string representation.
   *
   * @param mediaType String representation to be parsed
   * @throws IllegalArgumentException if the {@code mimeType} cannot be parsed.
   * @return {@link MediaType} instance for the parsed {@code mediaType} string.
   */
  public static MediaType parse(String mediaType) {
    MediaType cachedMediaType = cache.get(mediaType);
    if (cachedMediaType != null) {
      return cachedMediaType;
    }

    return parseMediaType(mediaType, false);
  }

  /**
   * Parses a media type, defined by the developer in the App, from its string representation.
   * <p>
   * <b>WARNING</b> This method should not be used if the source of the mediaType was not defined by the APP Developer. For
   * example if the source is a Transport user should use {@link MediaType#parse(String)}.
   *
   * @param mediaType String representation to be parsed
   * @throws IllegalArgumentException if the {@code mimeType} cannot be parsed.
   * @return {@link MediaType} instance for the parsed {@code mediaType} string.
   * @since 1.4, 1.3.1, 1.2.4, 1.1.7
   */
  public static MediaType parseDefinedInApp(String mediaType) {
    MediaType cachedMediaType = cache.get(mediaType);
    if (cachedMediaType != null) {
      return cachedMediaType;
    }

    return parseMediaType(mediaType, true);
  }

  private static MediaType parseMediaType(String mediaType, boolean definedInApp) {
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

      boolean isDefinedInApp = definedInApp || params.isEmpty();

      if (!isDefinedInApp && !KNOWN_PARAM_NAMES.isEmpty()) {
        final Set<String> paramNames = params.keySet();
        isDefinedInApp = KNOWN_PARAM_NAMES.containsAll(paramNames);
      }

      final MediaType value =
          new MediaType(mimeType.getPrimaryType(), mimeType.getSubType(), params, charset, isDefinedInApp);

      // multipart content types may have a random boundary, so we don't want to cache those (they won't be reused so no point
      // in caching them).
      // In order to make the cache take into account other similar scenarios, we use the presence of other parameters to
      // determine if the value is cached or not.
      if (params.isEmpty()) {
        return cacheMediaType(value, mediaType, definedInApp);
      } else {
        return value;
      }
    } catch (MimeTypeParseException e) {
      throw new IllegalArgumentException("MediaType cannot be parsed: " + mediaType, e);
    }
  }

  /**
   * Sets the well known media type param names
   * <p>
   * <b>WARNING</b> This is only for testing
   *
   * @param knownParamNames The list of params
   */
  static void setKnownParamNames(List<String> knownParamNames) {
    KNOWN_PARAM_NAMES = knownParamNames;
  }

  /**
   * Returns a media-type for. This would be the equivalent of the media type {@code "{primaryType}/{subType}"}.
   *
   * @param primaryType the left part of the represented type.
   * @param subType     the right part of the represented type.
   * @return {@link MediaType} instance for given parameters.
   */
  public static MediaType create(String primaryType, String subType) {
    return create(primaryType, subType, null);
  }

  /**
   * Returns a media-type for the given parameters. This would be the equivalent of the media type
   * {@code "{primaryType}/{subType}[; charset={charset}]"}.
   *
   * @param primaryType the left part of the represented type.
   * @param subType     the right part of the represented type.
   * @param charset     the value of the {@code charset} parameter.
   * @return {@link MediaType} instance for given parameters.
   */
  public static MediaType create(String primaryType, String subType, Charset charset) {
    final MediaType value = new MediaType(primaryType, subType, emptyMap(), charset, true);

    // multipart content types may have a random boundary, so we don't want to cache those (they won't be reused so no point
    // in caching them).
    // In order to make the cache take into account other similar scenarios, we use the presence of other parameters to
    // determine if the value is cached or not.
    MediaType cachedMediaType = cache.get(value.toRfcString());

    if (cachedMediaType != null) {
      return cachedMediaType;
    }

    return cacheMediaType(value, value.toRfcString(), false);
  }

  private static MediaType cacheMediaType(final MediaType type, String rfcString, boolean definedInApp) {
    final MediaType oldValue = cache.putIfAbsent(rfcString, type);
    return oldValue == null ? type : oldValue;
  }

  private MediaType(String primaryType, String subType, Map<String, String> params, Charset charset, boolean definedInApp) {
    this.primaryType = primaryType;
    this.subType = subType;
    this.params = params;
    this.charset = charset;
    this.definedInApp = definedInApp;
    this.rfcString = calculateRfcString();
  }

  private transient Cache<Optional<Charset>, MediaType> withCharsetCache = Caffeine.newBuilder().maximumSize(16).build();

  /**
   * Creates a new {@link MediaType} instance keeping the {@code type} and {@code sub-type} but replacing the {@code charset} with
   * the value passed.
   *
   * @param charset the new charset to use or {@code null} to clear the current {@code charset}.
   * @return new immutable {@link MediaType} instance.
   */
  public MediaType withCharset(Charset charset) {
    return withCharsetCache.get(ofNullable(charset),
                                c -> new MediaType(this.getPrimaryType(), this.getSubType(), params, c.orElse(null),
                                                   definedInApp));
  }

  /**
   * Creates a new {@link MediaType} instance with the same {@code type}, {@code sub-type} and {@code charset} but adding the
   * {@code params} passed.
   * <p>
   * If any key of the passed {@code params} is the {@code charset} or another param already contained in this instance, the newly
   * returned {@link MediaType} has those values replaced with the ones provided in {@code params}.
   *
   * @param params the key-value pairs of the additional parameter.
   * @return new immutable {@link MediaType} instance.
   */
  public MediaType withParamaters(Map<String, String> params) {
    if (!params.isEmpty()) {
      return new MediaType(this.getPrimaryType(), this.getSubType(), params, charset, definedInApp);
    } else {
      return this;
    }
  }

  /**
   * Creates a new {@link MediaType} instance keeping the {@code type} and {@code sub-type} but removing all the parameters (like
   * the {@code charset})
   *
   * @return new immutable {@link MediaType} instance.
   */
  public MediaType withoutParameters() {
    if (withoutParams == null) {
      synchronized (withoutParamsLock) {
        if (withoutParams == null) {
          withoutParams = create(this.getPrimaryType(), this.getSubType());
        }
      }
    }

    return withoutParams;
  }

  /**
   * Returns true if this mimeType was defined in the Mule App by the developer
   * 
   * @return True if defined in the Mule App
   * @since 1.2.4
   */
  public boolean isDefinedInApp() {
    return definedInApp;
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
   *         This may be not set, in which case it is up to the caller to determine the appropriate charset to use.
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
    return rfcString;
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
    return Objects.hash(primaryType, subType, charset, params, definedInApp);
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
        && Objects.equals(definedInApp, other.definedInApp)
        && Objects.equals(charset, other.charset);
  }

  private void readObject(ObjectInputStream in) throws Exception {
    in.defaultReadObject();
    String charsetStr = (String) in.readObject();
    if (charsetStr != null) {
      charset = Charset.forName(charsetStr);
    }

    this.withCharsetCache = Caffeine.newBuilder().maximumSize(16).build();
    this.withoutParamsLock = new Object();
    this.rfcString = calculateRfcString();
  }

  protected String calculateRfcString() {
    String typeSubtype = primaryType + "/" + subType;

    if (charset == null && params.isEmpty()) {
      return typeSubtype;
    }

    if (params.isEmpty()) {
      return typeSubtype + "; charset=" + charset.name();
    }

    final StringBuilder buffer = new StringBuilder();

    buffer.append(typeSubtype);

    if (charset != null) {
      buffer.append("; charset=" + charset.name());
    }

    if (!params.isEmpty()) {
      params.forEach((k, v) -> {
        buffer.append("; ").append(k).append("=\"").append(quote(v)).append("\"");
      });
    }

    return buffer.toString();
  }

  private void writeObject(ObjectOutputStream out) throws Exception {
    out.defaultWriteObject();
    if (charset != null) {
      out.writeObject(charset.name());
    } else {
      out.writeObject(null);
    }
  }

  private String quote(String value) {
    int length = value.length();
    StringBuffer buffer = new StringBuffer();

    buffer.ensureCapacity(new Double(length * 1.5D).intValue());

    for (int i = 0; i < length; i++) {
      char c = value.charAt(i);
      if (c == '\\' || c == '"') {
        buffer.append('\\');
      }
      buffer.append(c);
    }
    return buffer.toString();
  }
}

