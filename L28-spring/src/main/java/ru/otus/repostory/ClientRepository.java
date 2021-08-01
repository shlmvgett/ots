package ru.otus.repostory;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.otus.domain.Client;

public interface ClientRepository extends CrudRepository<Client, Long> {

  Optional<Client> findByName(@Param("name") String name);

  @Query("select * from users where upper(name) = upper(:name)")
  Optional<Client> findByNameIgnoreCase(@Param("name") String name);

  @Query("select * from users")
  List<Client> findAll();

  @Modifying
  @Query("update users set name = :newName where id = :id")
  void updateName(@Param("id") long id, @Param("newName") String newName);
}
