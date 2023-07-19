/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.value;

import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;
import static org.apache.commons.lang3.builder.ToStringStyle.JSON_STYLE;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.exception.ExceptionUtils;

/**
 * Immutable representation of an error that occurred during {@link Value} resolution.
 *
 * @since 1.0
 */
public final class ResolvingFailure {

  private final String message;
  private final String reason;
  private final String failureCode;

  private ResolvingFailure(String message,
                           String reason,
                           String failureCode) {
    this.message = message;
    this.reason = reason;
    this.failureCode = failureCode;
  }

  /**
   * @return a custom message describing the context in which the failure occurred
   */
  public String getMessage() {
    return message;
  }

  /**
   * @return the {@link String} of the error that occurred
   */
  public String getFailureCode() {
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
    private String code = "UNKNOWN";

    private Builder() {}

    public static Builder newFailure() {
      return new Builder();
    }

    public static Builder newFailure(Throwable e) {
      Builder builder = new Builder();
      builder.message = e.getMessage();
      builder.reason = ExceptionUtils.getStackTrace(e);
      return builder;
    }

    public ResolvingFailure build() {
      return new ResolvingFailure(message, reason, code);
    }

    public Builder withMessage(String message) {
      this.message = message;
      return this;
    }

    public Builder withReason(String reason) {
      this.reason = reason;
      return this;
    }

    public Builder withFailureCode(String failureCode) {
      this.code = failureCode;
      return this;
    }
  }

  @Override
  public String toString() {
    return reflectionToString(this, JSON_STYLE);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;

    if (o == null || getClass() != o.getClass())
      return false;

    ResolvingFailure that = (ResolvingFailure) o;

    return new EqualsBuilder()
        .append(message, that.message)
        .append(reason, that.reason)
        .append(failureCode, that.failureCode)
        .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
        .append(message)
        .append(reason)
        .append(failureCode)
        .toHashCode();
  }
}
