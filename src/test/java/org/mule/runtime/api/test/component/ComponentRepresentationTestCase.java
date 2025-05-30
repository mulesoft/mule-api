/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.test.component;

import static org.mule.runtime.api.component.AbstractComponent.LOCATION_KEY;
import static org.mule.runtime.api.component.AbstractComponent.ROOT_CONTAINER_NAME_KEY;
import static org.mule.runtime.api.component.Component.Annotations.REPRESENTATION_ANNOTATION_KEY;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.sameInstance;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;

import org.mule.runtime.api.component.AbstractComponent;
import org.mule.runtime.api.component.Component;
import org.mule.runtime.api.component.location.ComponentLocation;
import org.mule.runtime.api.component.location.Location;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import javax.xml.namespace.QName;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class ComponentRepresentationTestCase {

  @Parameters(name = "{index}: fib[{0}]={1}")
  public static Iterable<Supplier<? extends Component>> data() {
    List<Supplier<? extends Component>> params = new ArrayList<>();

    params.add(TestAbstractComponent::new);
    params.add(TestComponent::new);

    return params;
  }

  @Parameter(0)
  public Supplier<? extends Component> componentFactory;

  @Test
  public void representations() {
    final Component comp = componentFactory.get();

    final Map<QName, Object> annotations = new HashMap<>();
    annotations.put(REPRESENTATION_ANNOTATION_KEY, "representation");
    comp.setAnnotations(annotations);

    assertThat(comp.getRepresentation(), is("representation"));
  }

  @Test
  public void locations() {
    final Component comp = componentFactory.get();

    final Map<QName, Object> annotations = new HashMap<>();
    final ComponentLocation location = mock(ComponentLocation.class);
    annotations.put(LOCATION_KEY, location);
    annotations.put(ROOT_CONTAINER_NAME_KEY, "rootContainer");
    comp.setAnnotations(annotations);

    assertThat(comp.getLocation(), sameInstance(location));
    assertThat(comp.getRootContainerLocation().toString(), is("rootContainer"));
  }

  private static class TestAbstractComponent extends AbstractComponent {

  }

  private static class TestComponent implements Component {

    private Map<QName, Object> annotations;

    @Override
    public Object getAnnotation(QName name) {
      return annotations.get(name);
    }

    @Override
    public Map<QName, Object> getAnnotations() {
      return annotations;
    }

    @Override
    public void setAnnotations(Map<QName, Object> annotations) {
      this.annotations = annotations;
    }

    @Override
    public ComponentLocation getLocation() {
      return (ComponentLocation) getAnnotation(LOCATION_KEY);
    }

    @Override
    public Location getRootContainerLocation() {
      return Location.builder().globalName((String) getAnnotation(ROOT_CONTAINER_NAME_KEY)).build();
    }

  }
}
