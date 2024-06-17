/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.metadata.resolving;

import static java.util.Optional.ofNullable;
import static org.mule.runtime.api.metadata.resolving.FailureCode.UNKNOWN;
import static org.mule.runtime.api.metadata.resolving.MetadataComponent.COMPONENT;
import static org.mule.runtime.api.metadata.resolving.MetadataComponent.ENTITY;
import static org.mule.runtime.api.metadata.resolving.MetadataComponent.INPUT;
import static org.mule.runtime.api.metadata.resolving.MetadataComponent.KEYS;
import static org.mule.runtime.api.metadata.resolving.MetadataComponent.OUTPUT_ATTRIBUTES;
import static org.mule.runtime.api.metadata.resolving.MetadataComponent.OUTPUT_PAYLOAD;
import org.mule.runtime.api.metadata.MetadataResolvingException;

import java.util.Optional;

import org.apache.commons.lang3.exception.ExceptionUtils;

/**
 * Immutable representation of an error that occurred during Metadata resolution.
 *
 * @since 1.0
 */
public final class MetadataFailure {

  private final MetadataComponent failingComponent;
  private final String failingElement;
  private final String message;
  private final String reason;
  private final FailureCode failureCode;

  private MetadataFailure(MetadataComponent failingComponent, String failingElement,
                          String message,
                          String reason,
                          FailureCode failureCode) {
    this.failingComponent = failingComponent;
    this.failingElement = failingElement;
    this.message = message;
    this.reason = reason;
    this.failureCode = failureCode;
  }

  /**
   * @return the component where the failure occurred
   */
  public MetadataComponent getFailingComponent() {
    return failingComponent;
  }

  /**
   * @return the element that failed in the component.
   */
  public Optional<String> getFailingElement() {
    return ofNullable(failingElement);
  }

  /**
   * @return a custom message describing the context in which the failure occurred
   */
  public String getMessage() {
    return message;
  }

  /**
   * @return the {@link FailureCode} of the error that occurred
   */
  public FailureCode getFailureCode() {
    return failureCode;
  }

  /**
   * @return the original message of the error that produced this failure
   */
  public String getReason() {
    return reason;
  }

  public static class Builder {

    private String message;
    private String reason;
    private FailureCode code;

    private Builder() {}

    public static Builder newFailure() {
      return new Builder();
    }

    public static Builder newFailure(Exception e) {
      Builder builder = new Builder();
      builder.code = e instanceof MetadataResolvingException ? ((MetadataResolvingException) e).getFailure() : UNKNOWN;
      builder.message = e.getMessage();
      builder.reason = ExceptionUtils.getStackTrace(e);
      return builder;
    }

    /**
     * @return creates a new {@link MetadataFailure} that occurred when fetching metadata keys.
     */
    public MetadataFailure onKeys() {
      return build(KEYS);
    }

    /**
     * @return creates a new {@link MetadataFailure} that occurred when fetching the metadata for a given {@code parameter}.
     */
    public MetadataFailure onParameter(String parameter) {
      return build(INPUT, parameter);
    }

    /**
     * @return creates a new {@link MetadataFailure} that occurred when fetching the output payload metadata.
     */
    public MetadataFailure onOutputPayload() {
      return build(OUTPUT_PAYLOAD);
    }

    /**
     * @return creates a new {@link MetadataFailure} that occurred when fetching the output attributes metadata.
     */
    public MetadataFailure onOutputAttributes() {
      return build(OUTPUT_ATTRIBUTES);
    }

    /**
     * @return creates a new {@link MetadataFailure} that occurred when fetching the component metadata.
     */
    public MetadataFailure onComponent() {
      return build(COMPONENT);
    }

    /**
     * @return creates a new {@link MetadataFailure} that occurred when fetching a DSQL entity.
     */
    public MetadataFailure onEntity() {
      return build(ENTITY);
    }

    private MetadataFailure build(MetadataComponent failingPart) {
      return build(failingPart, null);
    }

    private MetadataFailure build(MetadataComponent failingPart, String failureComponent) {
      return new MetadataFailure(failingPart, failureComponent, message, reason, code);
    }

    public Builder withMessage(String message) {
      this.message = message;
      return this;
    }

    public Builder withReason(String reason) {
      this.reason = reason;
      return this;
    }

    public Builder withFailureCode(FailureCode failureCode) {
      this.code = failureCode;
      return this;
    }
  }

  @Override
  public String toString() {
    return "{"
      + "\"failingComponent\":" + (getFailingComponent() != null ? getFailingComponent().toString() : "null") + ", "
      + "\"failingElement\":\"" + (getFailingElement().isPresent() ? getFailingElement().toString() : "null") + "\", "
      + "\"message\":\"" + (getMessage() != null ? getMessage() : "null") + "\", "
      + "\"reason\":\"" + (getReason() != null ? getReason() : "null") + "\", "
      + "\"failureCode\":" + (getFailureCode() != null ? getFailureCode().toString() : "null")
      + "}";
  }
}
