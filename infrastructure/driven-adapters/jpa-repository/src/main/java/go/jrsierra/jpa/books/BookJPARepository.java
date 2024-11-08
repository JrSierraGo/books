package go.jrsierra.jpa.books;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import java.util.List;

public interface BookJPARepository extends CrudRepository<BookEntity, String>, QueryByExampleExecutor<BookEntity> {

    @Query(value = "SELECT b.*, ts_rank_cd(text_search_vectorized , query) AS rank " +
            "FROM book b, plainto_tsquery('spanish', ?1) query " +
            "WHERE query @@ text_search_vectorized " +
            "ORDER BY rank DESC",
            nativeQuery = true)
    List<BookEntity> findByText(String criteria);
}
