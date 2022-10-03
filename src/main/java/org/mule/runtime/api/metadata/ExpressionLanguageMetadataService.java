/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.metadata;

import org.mule.metadata.api.TypeLoader;
import org.mule.metadata.api.model.MetadataFormat;
import org.mule.metadata.api.model.MetadataType;
import org.mule.metadata.message.api.MuleEventMetadataType;
import org.mule.metadata.message.api.MuleEventMetadataTypeBuilder;
import org.mule.metadata.message.api.el.ModuleDefinition;
import org.mule.metadata.message.api.el.TypeBindings;
import org.mule.runtime.api.el.ExpressionCompilationException;
import org.mule.runtime.api.service.Service;

import java.io.InputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * This service provides metadata interaction with the underlying expression language.
 *
 * @since 1.2.0
 */
public interface ExpressionLanguageMetadataService extends Service {

  /**
   * Infers the expected input mule event type {@link MuleEventMetadataType} for specified output type with the given script.
   *
   * @param expression The scripting text.
   * @param output     The expected output type
   * @param builder    The builder to be used to build the event type
   * @param callback   The callback
   */
  void getInputType(String expression, MetadataType output, MuleEventMetadataTypeBuilder builder, MessageCallback callback);

  /**
   * Returns the result type expression when invoked with the given {@link TypeBindings}.
   *
   * @param typeBindings The script bindings
   * @param expression   The scripting text.
   * @param callback     The callback
   * @return The return type of the expression.
   */
  MetadataType getOutputType(TypeBindings typeBindings, String expression, MessageCallback callback);


  /**
   * Returns the result type expression when invoked with the given {@link TypeBindings}.
   *
   * @param typeBindings   The script bindings
   * @param expression     The scripting text.
   * @param outputMimeType The output mimeType of the expression
   * @param callback       The callback
   * @return The return type of the expression.
   */
  MetadataType getOutputType(TypeBindings typeBindings, String expression, String outputMimeType, MessageCallback callback);

  /**
   * Infers the metadata out of a sample data
   *
   * @param sample           The sample data to be use
   * @param readerProperties The configuration properties to read the sample data
   * @param mimeType         The mimeType of the sample data
   * @return The infered MetadataType
   */
  MetadataType getMetadataFromSample(InputStream sample, Map<String, Object> readerProperties, String mimeType);

  /**
   * Returns if the assignment type can be assigned to the expected type
   *
   * @param assignment The type to be assigned
   * @param expected   The expected type
   * @param callback   Callback for error messages. All the reasons of why it was not able to assign
   * @return True if it can be assign
   */
  boolean isAssignable(MetadataType assignment, MetadataType expected, MessageCallback callback);

  /**
   * Returns the substitution that needs to be done in order for this two types can be assigned
   *
   * @param assignment The assignment type
   * @param expected   The expected type
   * @param callback   The callback for errors and warnings
   * @return The substitution
   */
  Map<String, MetadataType> resolveAssignment(MetadataType assignment, MetadataType expected, MessageCallback callback);

  /**
   * Returns a new type with the substitution being applied
   *
   * @param assignment   The type to be substituted
   * @param substitution The substitution
   * @return The new type
   */
  MetadataType substitute(MetadataType assignment, Map<String, MetadataType> substitution);

  /**
   * Unify all the specified types into one type. It will remove duplications and return a UnionType only if required
   *
   * @param metadataTypes The types to be unified
   * @return The new type
   */
  MetadataType unify(List<MetadataType> metadataTypes);

  /**
   * Intersects all the specified types into one type.
   *
   * @param metadataTypes The types to be intersected
   * @return The new type
   */
  MetadataType intersect(List<MetadataType> metadataTypes);

  /**
   * Returns the type serializer
   *
   * @return the type serializer
   */
  MetadataTypeSerializer getTypeSerializer();


  /**
   * Returns a new type loader. Loads a script that contains a collections of types serialized with the language syntax.
   *
   * @param content        The script
   * @param metadataFormat The format that will be used for the loaded types
   * @return The type loader
   */
  TypeLoader createTypeLoader(String content, MetadataFormat metadataFormat);


  /**
   * Returns a new type loader. Loads a script that contains a collections of types serialized with the language syntax.
   *
   * @param content        The script
   * @param metadataFormat The format that will be used for the loaded types
   * @param modules        Other modules available
   * @return The type loader
   * @since 1.5
   */
  TypeLoader createTypeLoader(String content, MetadataFormat metadataFormat, Collection<ModuleDefinition> modules);


  /**
   * Returns a module definition from a given NameIdentifier.
   *
   * @param nameIdentifier The NameIdentifier for the modules core::test::MyModule
   * @param modules        Other modules available
   * @return The ModuleDefinition
   * @throws ExpressionCompilationException if any problem occurs while trying to parse the expression
   * @since 1.4
   */
  ModuleDefinition moduleDefinition(String nameIdentifier, Collection<ModuleDefinition> modules)
      throws ExpressionCompilationException;


  /**
   * Returns a MetadataType from a given type expression.
   *
   * @param typeExpression The type expression to evaluate
   * @param modules        Other modules available
   * @return The resolved MetadataType
   * @throws ExpressionCompilationException if any problem occurs while trying to resolve the type expression
   * @since 1.5
   */
  MetadataType evaluateTypeExpression(String typeExpression, Collection<ModuleDefinition> modules)
      throws ExpressionCompilationException;

  /**
   * Serialize MetadataTypes in the type system of the given expression language
   */
  interface MetadataTypeSerializer {

    /**
     * Serialize all the types
     *
     * @param catalog The list of type
     * @return The script with all types serialized
     */
    String serialize(Map<String, MetadataType> catalog);

    /**
     * Serialize the given metadata type with the specified name
     *
     * @param name The name of the type
     * @param type The type
     * @return The script with the type serialized
     */
    default String serialize(String name, MetadataType type) {
      return serialize(Collections.singletonMap(name, type));
    }
  }


  /**
   * Callback from the resolver
   */
  interface MessageCallback {

    /**
     * Is called when a warning message happens while resolving metadata
     *
     * @param message  The message
     * @param location The location of the message
     */
    void warning(String message, MessageLocation location);

    /**
     * Is called when a error message happens while resolving metadata
     *
     * @param message  The message
     * @param location The location of the message
     */
    void error(String message, MessageLocation location);
  }


  /**
   * Represents a message location
   */
  class MessageLocation {

    private MessagePosition startPosition;
    private MessagePosition endPosition;

    public MessageLocation(MessagePosition startPosition, MessagePosition endPosition) {
      this.startPosition = startPosition;
      this.endPosition = endPosition;
    }

    /**
     * The start position of this location
     *
     * @return the position
     */
    public MessagePosition getStartPosition() {
      return this.startPosition;
    }

    /**
     * The end position of this location
     *
     * @return the position
     */
    public MessagePosition getEndPosition() {
      return this.endPosition;
    }
  }

  /**
   * A position with the line , column and offset in a document
   */
  class MessagePosition {

    private int line;
    private int column;
    private int offset;

    public MessagePosition(int line, int column, int offset) {
      this.line = line;
      this.column = column;
      this.offset = offset;
    }

    public int getLine() {
      return this.line;
    }

    public int getColumn() {
      return this.column;
    }

    public int getOffset() {
      return this.offset;
    }
  }

}
