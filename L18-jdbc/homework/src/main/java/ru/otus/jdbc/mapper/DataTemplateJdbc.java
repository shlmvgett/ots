package ru.otus.jdbc.mapper;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import ru.otus.core.repository.DataTemplate;
import ru.otus.core.repository.DataTemplateException;
import ru.otus.core.repository.executor.DbExecutor;

/**
 * Сохратяет объект в базу, читает объект из базы
 */
public class DataTemplateJdbc<T> implements DataTemplate<T> {

  private final DbExecutor dbExecutor;
  private final EntityClassMetaData<T> classMetaData;
  private final EntitySQLMetaData sqlMetaData;

  public DataTemplateJdbc(DbExecutor dbExecutor, EntityClassMetaData<T> classMetaData) {
    this.dbExecutor = dbExecutor;
    this.classMetaData = classMetaData;
    this.sqlMetaData = new EntitySQLMetaDataImpl<>(classMetaData);
  }

  @Override
  public long insert(Connection connection, T object) {
    List<Object> params = classMetaData.getFieldsWithoutId().stream().map(f -> {
      try {
        f.setAccessible(true);
        return f.get(object);
      } catch (IllegalAccessException e) {
        throw new RuntimeException("Can't get value for:" + f.getName());
      }
    }).collect(Collectors.toList());

    try {
      return dbExecutor.executeStatement(connection, sqlMetaData.getInsertSql(), params);
    } catch (Exception e) {
      throw new DataTemplateException(e);
    }
  }

  @Override
  public Optional<T> findById(Connection connection, long id) {
    return dbExecutor.executeSelect(connection, sqlMetaData.getSelectByIdSql(), List.of(id),
        rs -> {
          try {
            if (rs.next()) {
              Object[] args = classMetaData.getAllFields().stream().map(f -> {
                try {
                  return rs.getObject(f.getName(), f.getType());
                } catch (SQLException e) {
                  throw new RuntimeException(e);
                }
              }).toArray();
              return (T) classMetaData.getConstructor().newInstance(args);
            }
            return null;
          } catch (SQLException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
          }
        });
  }

  @Override
  public List<T> findAll(Connection connection) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void update(Connection connection, T client) {
    throw new UnsupportedOperationException();
  }
}
