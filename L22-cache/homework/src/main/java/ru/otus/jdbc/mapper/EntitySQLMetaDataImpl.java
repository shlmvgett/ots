package ru.otus.jdbc.mapper;

import java.lang.reflect.Field;
import java.util.stream.Collectors;
import org.flywaydb.core.internal.util.StringUtils;

public class EntitySQLMetaDataImpl<T> implements EntitySQLMetaData {

  private final EntityClassMetaData<T> classMetaData;

  public EntitySQLMetaDataImpl(EntityClassMetaData<T> classMetaData) {
    this.classMetaData = classMetaData;
  }

  @Override
  public String getInsertSql() {
    String fields = StringUtils.collectionToCommaDelimitedString(
        classMetaData.getFieldsWithoutId().stream().map(Field::getName).collect(Collectors.toList()));
    String placeholders = "?,".repeat(classMetaData.getFieldsWithoutId().size());

    String query = String.format("INSERT INTO %s (%s) VALUES (%s)",
        classMetaData.getName().toLowerCase(),
        fields,
        placeholders.substring(0, placeholders.length() - 1)
    );
    System.out.println("Insert SQL:" + query);
    return query;
  }

  @Override
  public String getSelectAllSql() {
    return String.format("SELECT * FROM %s", classMetaData.getName());
  }

  @Override
  public String getSelectByIdSql() {
    return String.format("SELECT * FROM %s WHERE %s=?",
        classMetaData.getName(), classMetaData.getIdField().getName());
  }

  @Override
  public String getUpdateSql() {
    throw new UnsupportedOperationException();
  }
}
