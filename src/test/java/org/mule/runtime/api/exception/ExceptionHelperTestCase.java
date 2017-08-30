/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.exception;

import static java.lang.Thread.currentThread;
import static java.util.Collections.emptyMap;
import static org.apache.commons.io.IOUtils.toByteArray;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.rules.ExpectedException.none;
import static org.mule.runtime.api.exception.ExceptionHelper.getExceptionReader;
import static org.mule.runtime.api.exception.ExceptionHelper.registerExceptionReader;
import static org.mule.runtime.api.exception.ExceptionHelper.registerGlobalExceptionReader;
import static org.mule.runtime.api.exception.ExceptionHelper.unregisterExceptionReader;

import org.mule.runtime.api.legacy.exception.ExceptionReader;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Map;

public class ExceptionHelperTestCase {

  @Rule
  public ExpectedException expected = none();

  private ClassLoader originalTccl;

  private ClassLoader cl1;
  private ClassLoader cl2;

  private ExceptionReader cl1Reader;
  private ExceptionReader cl2Reader;

  @Before
  public void before() throws Exception {
    originalTccl = currentThread().getContextClassLoader();
    cl1 = new TestChildClassLoader(ExceptionHelperTestCase.class.getClassLoader());
    cl2 = new TestChildClassLoader(ExceptionHelperTestCase.class.getClassLoader());

    cl1Reader = (ExceptionReader) cl1.loadClass(TestExceptionReader.class.getName()).newInstance();
    cl2Reader = (ExceptionReader) cl2.loadClass(TestExceptionReader.class.getName()).newInstance();

    registerExceptionReader(cl1Reader);
    registerExceptionReader(cl2Reader);
  }

  @After
  public void after() {
    unregisterExceptionReader(cl1Reader);
    unregisterExceptionReader(cl2Reader);
    currentThread().setContextClassLoader(originalTccl);
  }

  @Test
  public void differentClassLoaders() throws Exception {
    assertThat(getExceptionReader(new Exception()), is(instanceOf(DefaultExceptionReader.class)));
  }

  @Test
  public void sameRegisterClassLoaderClassLoader() throws Exception {
    currentThread().setContextClassLoader(cl1);
    assertThat(getExceptionReader(new Exception()), is(cl1Reader));

    currentThread().setContextClassLoader(cl2);
    assertThat(getExceptionReader(new Exception()), is(cl2Reader));
  }

  @Test
  public void childRegisterClassLoaderClassLoader() throws Exception {
    currentThread().setContextClassLoader(new ClassLoader(cl1) {});
    assertThat(getExceptionReader(new Exception()), is(cl1Reader));

    currentThread().setContextClassLoader(new ClassLoader(cl2) {});
    assertThat(getExceptionReader(new Exception()), is(cl2Reader));
  }

  @Test
  public void registerOverridesGlobal() throws Exception {
    registerGlobalExceptionReader(new TestAbstractExceptionReader() {

      @Override
      public Class<?> getExceptionType() {
        return TestException.class;
      }
    });

    expected.expect(IllegalArgumentException.class);
    registerExceptionReader(new TestAbstractExceptionReader() {

      @Override
      public Class<?> getExceptionType() {
        return TestException.class;
      }
    });
  }

  @Test
  public void registerGlobalNotFromRuntime() throws Exception {
    expected.expect(IllegalArgumentException.class);
    registerGlobalExceptionReader(cl1Reader);
  }

  private static final class TestChildClassLoader extends ClassLoader {

    private TestChildClassLoader(ClassLoader parent) {
      super(parent);
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
      if (name.endsWith("TestExceptionReader")) {
        byte[] classBytes;
        try {
          classBytes = toByteArray(this.getClass().getResourceAsStream("/" + name.replaceAll("\\.", "/") + ".class"));
          return this.defineClass(null, classBytes, 0, classBytes.length);
        } catch (Exception e) {
          return super.loadClass(name);
        }
      } else {
        return super.loadClass(name);
      }
    }
  }

  public static abstract class TestAbstractExceptionReader implements ExceptionReader {

    @Override
    public String getMessage(Throwable t) {
      return t.getMessage();
    }

    @Override
    public Throwable getCause(Throwable t) {
      return t.getCause();
    }

    @Override
    public Map<String, Object> getInfo(Throwable t) {
      return emptyMap();
    }

  }

  public static class TestExceptionReader extends TestAbstractExceptionReader {

    @Override
    public Class<?> getExceptionType() {
      return Exception.class;
    }

  }

  public static class TestException extends Exception {

  }
}
