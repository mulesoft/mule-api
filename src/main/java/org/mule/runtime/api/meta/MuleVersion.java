/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class represents Mule Software version scheme:
 * {major}.{minor}.{revision}-{suffix}
 * <p>
 * For example:
 * 3.3.2 => major: 3, minor: 3, revision: 2, suffix: ''
 * 3.3.1-SNAPSHOT => major: 3, minor: 3, revision: 1, suffix: SNAPSHOT
 * 3.4-M2 => major: 3, minor: 4, suffix: M2
 *
 * @since 1.0
 */
public class MuleVersion {

  private static final Pattern pattern = Pattern.compile("([0-9]+)(\\.)([0-9]+)(\\.([0-9]*))?(-(.+))?");

  private int major = 0;
  private int minor = 0;
  private int revision = -1;
  private String suffix;

  public MuleVersion(String version) {
    parse(version);
  }

  @Override
  public String toString() {
    StringBuilder v = new StringBuilder(major + "." + minor);

    if (revision >= 0) {
      v.append(".").append(revision);
    }

    if (suffix != null && suffix.trim().length() > 0) {
      v.append("-").append(suffix);
    }

    return v.toString();

  }

  private void parse(String version) {
    Matcher m = pattern.matcher(version);

    if (!m.matches()) {
      throw new IllegalArgumentException("Invalid version " + version);
    }

    try {
      major = Integer.parseInt(m.group(1));
      minor = Integer.parseInt(m.group(3));

      if (m.group(4) != null && m.group(4).startsWith(".")) {
        revision = Integer.parseInt(m.group(5));
      }

      if (m.group(6) != null && m.group(6).startsWith("-")) {
        suffix = m.group(7);
      }
    } catch (NumberFormatException nfe) {
      throw new IllegalArgumentException("Invalid version " + version);
    }

    if (!toString().equals(version)) {
      throw new IllegalArgumentException("Invalid version " + version);
    }
  }

  /**
   * Returns true if the version represented by the current object is
   * equals newer to the version sent as parameter.
   *
   * @param version
   * @return
   */
  public boolean atLeast(String version) {
    return atLeast(new MuleVersion(version));
  }

  public boolean atLeast(MuleVersion version) {
    return equals(version) || newerThan(version);
  }

  public boolean atLeastBase(String baseVersion) {
    return getBaseVersion().atLeastBase(new MuleVersion(baseVersion));
  }

  public boolean atLeastBase(MuleVersion baseVersion) {
    return getBaseVersion().atLeast(baseVersion.getBaseVersion());
  }

  public boolean sameAs(String version) {
    return sameAs(new MuleVersion(version));
  }

  public boolean sameAs(MuleVersion version) {
    return equals(version);
  }

  public boolean priorTo(String version) {
    return priorTo(new MuleVersion(version));
  }

  public boolean priorTo(MuleVersion version) {
    return !atLeast(version);
  }

  public boolean newerThan(String version) {
    return newerThan(new MuleVersion(version));
  }

  public boolean newerThan(MuleVersion version) {
    if (getMajor() > version.getMajor()) {
      return true;
    } else if (getMajor() == version.getMajor()) {
      if (getMinor() > version.getMinor()) {
        return true;
      } else if (getMinor() == version.getMinor()) {
        if (getRevision() > version.getRevision()) {
          return true;
        } else if (getRevision() == version.getRevision() || (getRevision() <= 0 && version.getRevision() <= 0)) {
          if (!hasSuffix() && version.hasSuffix()) {
            return true;
          } else if (hasSuffix() && version.hasSuffix()) {
            return getSuffix().compareToIgnoreCase(version.getSuffix()) > 0;
          }
        }
      }
    }
    return false;
  }

  /**
   * Returns a string representing the complete numeric version, what means the
   * 3 numbers that represent major.minor.revision. If revision is not present, then it will
   * be set to 0 (zero).<br/>
   * Examples:<br/>
   * <ol>
   * <li>3.4.1-SNAPSHOT -> returns 3.4.1</li>
   * <li>3.4 -> returns 3.4.0</li>
   * <li>3.4.1 -> returns 3.4.1</li>
   * </ol>
   *
   * @return Complete numeric version: major.minor.revision
   */
  public String toCompleteNumericVersion() {
    StringBuilder v = new StringBuilder(major + "." + minor + ".");

    if (revision >= 0) {
      v.append(revision);
    } else {
      v.append("0");
    }

    return v.toString();
  }

  public boolean hasSuffix() {
    return getSuffix() != null && getSuffix().length() > 0;
  }

  private MuleVersion getBaseVersion() {
    return new MuleVersion(getMajor() + "." + getMinor());
  }

  @Override
  public boolean equals(Object otherObject) {
    if (this == otherObject) {
      return true;
    }

    if (!(otherObject instanceof MuleVersion)) {
      return false;
    }

    return toString().equals(otherObject.toString());
  }

  @Override
  public int hashCode() {
    return toString().hashCode();
  }

  public int getMajor() {
    return major;
  }

  public void setMajor(int major) {
    this.major = major;
  }

  public int getMinor() {
    return minor;
  }

  public void setMinor(int minor) {
    this.minor = minor;
  }

  public int getRevision() {
    return revision;
  }

  public void setRevision(int revision) {
    this.revision = revision;
  }

  public String getSuffix() {
    return suffix;
  }

  public void setSuffix(String suffix) {
    this.suffix = suffix;
  }
}
