package ru.otus.appcontainer;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.appcontainer.api.AppComponent;
import ru.otus.appcontainer.api.AppComponentsContainer;
import ru.otus.appcontainer.api.AppComponentsContainerConfig;

public class AppComponentsContainerImpl implements AppComponentsContainer {

  private final List<Object> appComponents = new ArrayList<>();
  private final Map<String, Object> appComponentsByName = new HashMap<>();

  private static final Logger log = LoggerFactory.getLogger(AppComponentsContainerImpl.class);

  public AppComponentsContainerImpl(Class<?> initialConfigClass) {
    processConfig(initialConfigClass);
  }

  private void processConfig(Class<?> configClass) {
    log.info("Config processing...");
    checkConfigClass(configClass);

    Object configObj = createConfig(configClass);
    List<Method> sortedMethods = getSortedMethods(configClass);

    for (Method method : sortedMethods) {
      List<Object> paramObj = getInputParams(method);
      createAppComponent(configObj, method, paramObj);
    }
  }

  private void checkConfigClass(Class<?> configClass) {
    if (!configClass.isAnnotationPresent(AppComponentsContainerConfig.class)) {
      throw new IllegalArgumentException(String.format("Given class is not config %s", configClass.getName()));
    }
  }

  private List<Method> getSortedMethods(Class<?> configClass) {
    return Arrays.stream(configClass.getDeclaredMethods())
        .filter(m -> m.getAnnotation(AppComponent.class) != null)
        .sorted(Comparator.comparingInt(m -> m.getAnnotation(AppComponent.class).order()))
        .collect(Collectors.toList());
  }

  private Object createConfig(Class<?> configClass) {
    log.info("Create config instance...");
    Object configObj = null;
    try {
      configObj = configClass.getConstructor().newInstance();
    } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
      e.printStackTrace();
    }
    return configObj;
  }

  private List<Object> getInputParams(Method method) {
    log.info("Get input params for app component...");
    Class<?>[] paramTypes = method.getParameterTypes();
    List<Object> paramObj = new ArrayList<>();

    for (Class paramType : paramTypes) {
      Object param = null;
      for (Object appComponent : appComponents) {
        if (appComponent.getClass().getInterfaces()[0].getName().equals(paramType.getName())) {
          param = appComponent;
          break;
        }
      }
      paramObj.add(param);
    }
    return paramObj;
  }

  private void createAppComponent(Object configObj, Method method, List<Object> inputParams) {
    log.info("Create app component instance: {}...", method.getName());
    try {
      Object appObj = method.invoke(configObj, inputParams.toArray());
      appComponents.add(appObj);
      appComponentsByName.put(method.getAnnotation(AppComponent.class).name(), appObj);
    } catch (IllegalAccessException | InvocationTargetException e) {
      e.printStackTrace();
      throw new RuntimeException("Application object creation fialure:" + method.getName());
    }
  }

  @Override
  public <C> C getAppComponent(Class<C> componentClass) {
    for (Object appComponent : appComponents) {
      if (componentClass.getInterfaces().length != 0) {
        if (appComponent.getClass().getInterfaces()[0].getName().equals(componentClass.getInterfaces()[0].getName())) {
          return (C) appComponent;
        }
      } else {
        if (appComponent.getClass().getInterfaces()[0].getName().equals(componentClass.getName())) {
          return (C) appComponent;
        }
      }
    }
    throw new RuntimeException("componentClass wasn't found: " + componentClass);
  }

  @Override
  public <C> C getAppComponent(String componentName) {
    return (C) appComponentsByName.get(componentName);
  }
}
