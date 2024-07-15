package go.jrsierra.jpa.books;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

public interface BookJPARepository extends CrudRepository<BookEntity, String>, QueryByExampleExecutor<BookEntity> {
}
