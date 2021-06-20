package ru.otus.jdbc.mapper;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import ru.otus.crm.service.annotations.Id;

public class EntityClassMetaDataImpl<T> implements EntityClassMetaData<T> {

  private final Class<T> clazz;

  public EntityClassMetaDataImpl(Class<T> clazz) {
    this.clazz = clazz;
  }

  @Override
  public String getName() {
    return clazz.getSimpleName();
  }

  @Override
  public <T> Constructor<T> getConstructor() {
    try {
      return (Constructor<T>) clazz.getConstructor(getAllFields().stream().map(Field::getType).toArray(Class[]::new));
    } catch (NoSuchMethodException e) {
      throw new RuntimeException("All args constructor wasn't found for class: " + clazz.getName());
    }
  }

  @Override
  public Field getIdField() {
    List<Field> idFields = Arrays.stream(clazz.getDeclaredFields())
        .filter(f -> f.isAnnotationPresent(Id.class)).collect(Collectors.toList());
    if (idFields.size() != 1) {
      throw new RuntimeException("Model should contains only 1 field marked as @Id.");
    }
    return idFields.get(0);
  }

  @Override
  public List<Field> getAllFields() {
    return List.of(clazz.getDeclaredFields());
  }

  @Override
  public List<Field> getFieldsWithoutId() {
    return Arrays.stream(clazz.getDeclaredFields())
        .filter(f -> !f.isAnnotationPresent(Id.class)).collect(Collectors.toList());
  }
}
