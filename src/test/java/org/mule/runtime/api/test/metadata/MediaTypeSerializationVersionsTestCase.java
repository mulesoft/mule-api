/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.test.metadata;

import static java.util.Arrays.asList;

import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;

import org.mule.runtime.api.metadata.MediaType;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class MediaTypeSerializationVersionsTestCase {

  @Parameter(0)
  public String versionFrom;
  @Parameter(1)
  public String versionTo;

  @Parameters(name = "from {0} to {1}")
  public static List<Object[]> params() {
    return asList(new Object[][] {{"V11", "V10"}});
  }

  private ClassLoader toClassLoader;
  private Class toClass;

  @Before
  public void before() throws ClassNotFoundException {
    toClassLoader = new ClassLoader(this.getClass().getClassLoader()) {

      private Class definedClass = null;

      @Override
      public Class<?> loadClass(String name) throws ClassNotFoundException {
        if (MediaType.class.getName().equals(name)) {
          if (definedClass != null) {
            return definedClass;
          }
          byte[] classBytes;
          try {
            classBytes =
                IOUtils.toByteArray(this.getClass()
                    .getResourceAsStream("/media_type/MediaType" + versionTo + ".class"));
            definedClass = this.defineClass(name, classBytes, 0, classBytes.length);
            return definedClass;
          } catch (Exception e) {
            return super.loadClass(name);
          }
        } else {
          return super.loadClass(name);
        }
      }
    };
    toClass = toClassLoader.loadClass(MediaType.class.getName());
    assertThat(toClass, not(sameInstance(MediaType.class)));
  }

  @Test
  public void serializeDeserializeDefault() throws ClassNotFoundException, IOException {
    final InputStream resourceAsStream =
        getClass().getResourceAsStream("/media_type/mediaType" + versionFrom + ".ser");
    final Object toMediaType = new ObjectWithClassLoaderInputStream(resourceAsStream, toClassLoader).readObject();
    assertThat(toMediaType.getClass(), sameInstance(toClass));
  }

  @Test
  public void serializeDeserializeDefinedInApp() throws ClassNotFoundException, IOException {
    final InputStream resourceAsStream =
        getClass().getResourceAsStream("/media_type/mediaType" + versionFrom + "DefinedInApp.ser");
    final Object toMediaType = new ObjectWithClassLoaderInputStream(resourceAsStream, toClassLoader).readObject();
    assertThat(toMediaType.getClass(), sameInstance(toClass));
  }

  private final class ObjectWithClassLoaderInputStream extends ObjectInputStream {

    private final ClassLoader classLoader;

    private ObjectWithClassLoaderInputStream(InputStream in, ClassLoader classLoader) throws IOException {
      super(in);
      this.classLoader = classLoader;
    }

    @Override
    protected Class<?> resolveClass(ObjectStreamClass desc) throws IOException, ClassNotFoundException {
      return classLoader.loadClass(desc.getName());
    }
  }
}
