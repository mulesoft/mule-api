/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.tls;

import org.mule.api.annotation.NoImplement;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocketFactory;

/**
 * A factory for TLS contexts. A TLS context is configured with a key store and a trust store. The key store stores the private
 * and public keys of the current host. The trust store stores the certificates of the other hosts that are trusted.
 * <p>
 * This factory provides methods for creating client and server socket factories that will be already configured according to the
 * current context.
 *
 * @since 1.0
 */
@NoImplement
public interface TlsContextFactory {

  /**
   * Provides a builder to create custom {@link TlsContextFactory} objects or obtain the default one.
   *
   * @return a new {@link TlsContextFactoryBuilder}
   */
  static TlsContextFactoryBuilder builder() {
    return AbstractTlsContextFactoryBuilderFactory.getDefaultFactory().create();
  }

  /**
   * Allows the creation of an {@link SSLContext} with the configured keystore and trust store. You must use
   * {@link #getEnabledProtocols()} and {@link #getEnabledCipherSuites()} to further configure your TLS environment. The resulting
   * {@link SSLContext} should not be used to create {@link SSLSocketFactory} or {@link SSLServerSocketFactory} directly, use
   * {@link #createSocketFactory()} and {@link #createServerSocketFactory()} instead, otherwise protocols and ciphers will not be
   * enforced.
   *
   * @return a new SSL Context
   * @throws KeyManagementException
   * @throws NoSuchAlgorithmException
   */
  SSLContext createSslContext() throws KeyManagementException, NoSuchAlgorithmException;

  /**
   * Allows the creation of a {@link SSLSocketFactory} that restricts the available protocols and cipher suites in the sockets
   * that are created according to the enabled ones (see {@link #getEnabledProtocols()} and {@link #getEnabledCipherSuites()}).
   *
   * @return a new {@link SSLSocketFactory}
   * @throws KeyManagementException
   * @throws NoSuchAlgorithmException
   */
  SSLSocketFactory createSocketFactory() throws KeyManagementException, NoSuchAlgorithmException;

  /**
   * Allows the creation of a {@link SSLServerSocketFactory} that restricts the available protocols and cipher suites in the
   * sockets that are created according to the enabled ones (see {@link #getEnabledProtocols()} and
   * {@link #getEnabledCipherSuites()}).
   *
   * @return a new {@link SSLServerSocketFactory}
   * @throws KeyManagementException
   * @throws NoSuchAlgorithmException
   */
  SSLServerSocketFactory createServerSocketFactory() throws KeyManagementException, NoSuchAlgorithmException;

  /**
   * The list of ciphers that must be used to restrict the creation of the SSL Sockets
   *
   * @return ths list of enabled cipher suites
   */
  String[] getEnabledCipherSuites();

  /**
   * The list of enabled protocols that must be used to restrict the creation of the SSL Sockets
   *
   * @return the list of enabled protocols
   */
  String[] getEnabledProtocols();

  /**
   * @return true if the keystore was configured, false otherwise
   */
  boolean isKeyStoreConfigured();

  /**
   * @return true if the trust store was configured, false otherwise
   */
  boolean isTrustStoreConfigured();

  /**
   * @return An object with the configuration of the key store.
   */
  TlsContextKeyStoreConfiguration getKeyStoreConfiguration();

  /**
   * @return An object with the configuration of the trust store.
   */
  TlsContextTrustStoreConfiguration getTrustStoreConfiguration();
}
