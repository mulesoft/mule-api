/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.tls;

import org.mule.runtime.api.lifecycle.CreateException;

import java.security.KeyStore;

import javax.net.ssl.KeyManagerFactory;

/**
 * Builder for {@link TlsContextFactory}. To configure a trust store, at least it's path and password must be provided. To configure
 * a key store, at least it's path, password and key password must be provided.
 *
 * @since 1.0
 */
public interface TlsContextFactoryBuilder {

  /**
   * @return a {@link TlsContextFactory} with the jvm default certificates.
   */
  TlsContextFactory buildDefault();

  /**
   * Defines a name for the {@link TlsContextFactory}. Recommended for troubleshooting purposes.
   *
   * @param name the name of the context
   * @return this builder
   */
  TlsContextFactoryBuilder setName(String name);

  /**
   * Defines the enabled TLS protocols, which must be a subset of the global enabled ones, otherwise a {@link CreateException} will
   * occur upon {@link #build()}. By default, those global ones will be used.
   *
   * @param protocols a comma separated {@link String} with the protocols, for example "TLSv1.1,TLSv1.2"
   * @return this builder
   */
  TlsContextFactoryBuilder setEnabledProtocols(String protocols);

  /**
   * Defines the enabled TLS cipher suites, which must be a subset of the global enabled ones, otherwise a {@link CreateException}
   * will occur upon {@link #build()}. By default, those global ones will be used.
   *
   * @param cipherSuites a comma separated {@link String} with the cipher suites, for example "TLS_DHE_DSS_WITH_AES_128_CBC_SHA,TLS_DHE_DSS_WITH_AES_256_CBC_SHA".
   * @return this builder
   */
  TlsContextFactoryBuilder setEnabledCipherSuites(String cipherSuites);

  /**
   * Defines the location (which will be resolved relative to the current classpath and file system, if possible) where the trust
   * store to use should be looked for, requires {@link #setTrustStorePassword(String)} too.
   *
   * @param path the file path to the trust store
   * @return this builder
   */
  TlsContextFactoryBuilder setTrustStorePath(String path);

  /**
   * Defines the password to access the trust store defined in {@link #setTrustStorePath(String)}.
   *
   * @param password the password
   * @return this builder
   */
  TlsContextFactoryBuilder setTrustStorePassword(String password);

  /**
   * Defines the type of the trust store (such as jks, jceks or pkcs12). By default, {@link KeyStore#getDefaultType()} will be used.
   *
   * @param type
   * @return this builder
   */
  TlsContextFactoryBuilder setTrustStoreType(String type);

  /**
   * Defines whether the trust store should be insecure, meaning no certificate validations should be performed. Default value is
   * {@code false}.
   *
   * @param insecure
   * @return this builder
   */
  TlsContextFactoryBuilder setInsecureTrustStore(boolean insecure);

  /**
   * Defines the trust store algorithm. By default, {@link KeyManagerFactory#getDefaultAlgorithm()} will be used.
   *
   * @param algorithm the algorithm to use
   * @return this builder
   */
  TlsContextFactoryBuilder setTrustStoreAlgorithm(String algorithm);

  /**
   * Defines the location (which will be resolved relative to the current classpath and file system, if possible) where the key
   * store to use should be looked for. Requires {@link #setKeyStorePassword(String)} too.
   *
   * @param path the file path to the key store
   * @return this builder
   */
  TlsContextFactoryBuilder setKeyStorePath(String path);

  /**
   * Defines the password to access the key store defined in {@link #setKeyStorePath(String)}.
   *
   * @param password the password used to protect the key store
   * @return this builder
   */
  TlsContextFactoryBuilder setKeyStorePassword(String password);

  /**
   * Defines the alias of the key to use when the key store contains many private keys. By default, the first key in the file will
   * be used.
   *
   * @param alias the alias of the key
   * @return this builder
   */
  TlsContextFactoryBuilder setKeyAlias(String alias);

  /**
   * Defines the password used to protect the private key.
   *
   * @param password the password
   * @return this builder
   */
  TlsContextFactoryBuilder setKeyPassword(String password);

  /**
   * Defines the type of the key store (such as jks, jceks or pkcs12). By default, {@link KeyStore#getDefaultType()} will be used.
   *
   * @param type
   * @return this builder
   */
  TlsContextFactoryBuilder setKeyStoreType(String type);

  /**
   * Defines the key store algorithm. By default, {@link KeyManagerFactory#getDefaultAlgorithm()} will be used.
   *
   * @param algorithm
   * @return this builder
   */
  TlsContextFactoryBuilder setKeyStoreAlgorithm(String algorithm);


  /**
   * Returns a {@link TlsContextFactory} based on the configured properties.
   *
   * @return a newly built {@link TlsContextFactory}.
   * @throws CreateException if there's any trouble regarding protocols and cipher validation or store loading (such as the files not being found)
   */
  TlsContextFactory build() throws CreateException;

}
