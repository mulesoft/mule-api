/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.version;

import static com.vdurmont.semver4j.Semver.SemverType.LOOSE;

import java.util.Objects;

import com.vdurmont.semver4j.Semver;
import com.vdurmont.semver4j.SemverException;

/**
 * This class represents Mule Software version scheme to identify the features provided by the Mule Runtime: {major}.{minor}
 * <p>
 * For example: 3.3 => major: 3, minor: 3
 *
 * @since 1.10
 */
public class MuleMinorVersion {

  private final int major;
  private final int minor;

  public MuleMinorVersion(int major, int minor) {
    this.major = major;
    this.minor = minor;
  }

  public MuleMinorVersion(String version) {
    try {
      Semver semver = new Semver(version, LOOSE);
      if (semver.getMajor() == null || semver.getMinor() == null) {
        throw new IllegalArgumentException("Invalid version " + version + ", expected 'x.y'");
      }

      this.major = semver.getMajor();
      this.minor = semver.getMinor();
    } catch (SemverException sve) {
      throw new IllegalArgumentException("Invalid version " + version + ", " + sve.getMessage());
    }
  }

  public int getMajor() {
    return major;
  }

  public int getMinor() {
    return minor;
  }

  @Override
  public String toString() {
    return major + "." + minor;
  }

  @Override
  public int hashCode() {
    return Objects.hash(major, minor);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof MuleMinorVersion)) {
      return false;
    }
    MuleMinorVersion other = (MuleMinorVersion) obj;
    return major == other.major && minor == other.minor;
  }

}
