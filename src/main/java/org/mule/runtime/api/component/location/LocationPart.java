/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.component.location;

import org.mule.api.annotation.NoImplement;
import org.mule.runtime.api.component.TypedComponentIdentifier;

import java.util.Optional;
import java.util.OptionalInt;

/**
 * A location part represent an specific location of a component within another component.
 *
 * @since 1.0
 */
@NoImplement
public interface LocationPart {

  /**
   * @return the string representation of the part
   */
  String getPartPath();

  /**
   * A location part represent an specific location of a component within another component.
   *
   * @since 4.0
   */
  Optional<TypedComponentIdentifier> getPartIdentifier();

  /**
   * @return the config file of the application where this component is defined, if it was defined in a config file.
   */
  Optional<String> getFileName();

  /**
   * @return the line number in the config file of the application where this component is defined, if it was defined in a config
   *         file.
   * 
   * @deprecated Use {@link #getLine()} instead.
   */
  @Deprecated
  Optional<Integer> getLineInFile();

  /**
   * @return the start column in the config file of the application where this component is defined, if it was defined in a config
   *         file.
   * 
   * @deprecated Use {@link #getColumn()} instead.
   */
  @Deprecated
  Optional<Integer> getStartColumn();

  /**
   * @return the line number in the config file of the application where this component is defined, if it was defined in a config
   *         file.
   */
  OptionalInt getLine();

  /**
   * @return the start column in the config file of the application where this component is defined, if it was defined in a config
   *         file.
   */
  OptionalInt getColumn();

}
