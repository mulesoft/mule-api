/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.internal.util.xmlsecurity;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.stream.XMLInputFactory;
import javax.xml.transform.TransformerFactory;
import javax.xml.validation.SchemaFactory;

/**
 * Avoid configuring factories each time they are used. Since we started using setFeature to avoid security issues, getting a new
 * XML factory object is very expensive.
 *
 * @since 1.4.0
 */
public class XMLSecureFactoriesCache {

  private LoadingCache<XMLFactoryConfig, Object> cache = null;

  private static volatile XMLSecureFactoriesCache instance;

  public static XMLSecureFactoriesCache getInstance() {
    if (instance == null) {
      synchronized (XMLSecureFactoriesCache.class) {
        if (instance == null) {
          instance = new XMLSecureFactoriesCache();
        }
      }
    }

    return instance;
  }

  private XMLSecureFactoriesCache() {
    cache = CacheBuilder.newBuilder()
        .build(new CacheLoader<XMLFactoryConfig, Object>() {

          @Override
          public Object load(XMLFactoryConfig key) throws Exception {
            return key.createFactory();
          }
        });
  }

  public DocumentBuilderFactory getDocumentBuilderFactory(final DefaultXMLSecureFactories secureFactories) {
    XMLFactoryConfig config = new XMLFactoryConfig(secureFactories, null, DocumentBuilderFactory.class.toString()) {

      @Override
      public Object createFactory() {
        return secureFactories.createDocumentBuilderFactory();
      }
    };

    return (DocumentBuilderFactory) cache.getUnchecked(config);
  }

  public SAXParserFactory getSAXParserFactory(final DefaultXMLSecureFactories secureFactories) {
    XMLFactoryConfig config = new XMLFactoryConfig(secureFactories, null, SAXParserFactory.class.toString()) {

      @Override
      public Object createFactory() {
        return secureFactories.createSaxParserFactory();
      }
    };

    return (SAXParserFactory) cache.getUnchecked(config);
  }

  public XMLInputFactory getXMLInputFactory(final DefaultXMLSecureFactories secureFactories) {
    XMLFactoryConfig config = new XMLFactoryConfig(secureFactories, null, XMLInputFactory.class.toString()) {

      @Override
      public Object createFactory() {
        return secureFactories.createXMLInputFactory();
      }
    };

    return (XMLInputFactory) cache.getUnchecked(config);
  }

  public TransformerFactory getTransformerFactory(final DefaultXMLSecureFactories secureFactories) {
    XMLFactoryConfig config = new XMLFactoryConfig(secureFactories, null, TransformerFactory.class.toString()) {

      @Override
      public Object createFactory() {
        return secureFactories.createTransformerFactory();
      }
    };

    return (TransformerFactory) cache.getUnchecked(config);
  }

  public SchemaFactory getSchemaFactory(final DefaultXMLSecureFactories secureFactories, String schemaLanguage) {
    XMLFactoryConfig config = new XMLFactoryConfig(secureFactories, schemaLanguage, SchemaFactory.class.toString()) {

      @Override
      public Object createFactory() {
        return secureFactories.createSchemaFactory(schemaLanguage);
      }
    };

    return (SchemaFactory) cache.getUnchecked(config);
  }
}
