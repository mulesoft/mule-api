/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.meta;

import com.google.common.base.Joiner;
import com.vdurmont.semver4j.Semver;
import com.vdurmont.semver4j.SemverException;

import static com.vdurmont.semver4j.Semver.SemverType.LOOSE;

/**
 * This class represents Mule Software version scheme: {major}.{minor}.{revision}-{suffix}
 * <p>
 * For example: 3.3.2 => major: 3, minor: 3, revision: 2, suffix: '' 3.3.1-SNAPSHOT => major: 3, minor: 3, revision: 1, suffix:
 * SNAPSHOT 3.4-M2 => major: 3, minor: 4, suffix: M2
 *
 * @since 1.0
 */
public final class MuleVersion {

  public static final int NO_REVISION = -1;

  private Semver semver;

  public MuleVersion(String version) {
    parse(version);
  }

  @Override
  public String toString() {
    return this.semver.toString();
  }

  private void parse(String version) {
    try {
      this.semver = new Semver(version, LOOSE);
      if (this.semver.getMajor() == null || this.semver.getMinor() == null) {
        throw new IllegalArgumentException("Invalid version " + version);
      }
    } catch (SemverException sve) {
      throw new IllegalArgumentException("Invalid version " + version);
    }
  }

  /**
   * Returns true if the version represented by the current object is equals newer to the version sent as parameter.
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

  /**
   * @param otherVersion the other version
   * @return true if both have the same major and minor version, false otherwise
   */
  public boolean sameBaseVersion(MuleVersion otherVersion) {
    return getBaseVersion().sameAs(otherVersion.getBaseVersion());
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
   * Returns a string representing the complete numeric version, without suffixes. If the revision is not present, then it will be
   * set to 0 (zero).<br/>
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
    return this.semver.getMajor() + "." + semver.getMinor() + "."
        + (this.semver.getPatch() != null ? this.semver.getPatch() : 0);
  }

  /**
   * Returns a {@link MuleVersion} representing the complete numeric version, without suffixes. If the revision is not present,
   * then it will be set to 0 (zero).<br/>
   * Examples:<br/>
   * <ol>
   * <li>3.4.1-SNAPSHOT -> returns 3.4.1</li>
   * <li>3.4 -> returns 3.4.0</li>
   * <li>3.4.1 -> returns 3.4.1</li>
   * </ol>
   *
   * @return Complete numeric version: major.minor.revision
   */
  public MuleVersion withoutSuffixes() {
    return new MuleVersion(this.toCompleteNumericVersion());
  }

  public boolean hasSuffix() {
    return this.semver.getSuffixTokens() != null && this.semver.getSuffixTokens().length > 0;
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
    return this.semver.getMajor();
  }

  public void setMajor(int major) {
    this.semver.withIncMajor(-1 * this.semver.getMajor() + major);
  }

  public int getMinor() {
    return this.semver.getMinor();
  }

  public void setMinor(int minor) {
    this.semver.withIncMinor(-1 * this.semver.getMinor() + minor);
  }

  public int getRevision() {
    return this.semver.getPatch() != null ? this.semver.getPatch() : NO_REVISION;
  }

  public void setRevision(int revision) {
    this.semver.withIncPatch(-1 * this.semver.getPatch() + revision);
  }

  public String getSuffix() {
    return Joiner.on("").join(this.semver.getSuffixTokens());
  }

  public void setSuffix(String suffix) {
    this.semver.withSuffix(suffix);
  }
}
