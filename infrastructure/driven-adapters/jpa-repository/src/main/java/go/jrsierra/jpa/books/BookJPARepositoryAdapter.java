package go.jrsierra.jpa.books;

import go.jrsierra.jpa.helper.AdapterOperations;
import go.jrsierra.model.book.Book;
import go.jrsierra.model.book.gateways.BookRepository;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public class BookJPARepositoryAdapter extends AdapterOperations<BookEntity, Book, String, BookJPARepository> implements BookRepository {


    protected BookJPARepositoryAdapter(BookJPARepository repository, ObjectMapper mapper) {
        super(repository, mapper);
    }

    @Override
    public Flux<Book> findBooksByText(String criteria) {
        return Flux.fromIterable(repository.findByText(criteria))
                .map(this::toData);
    }
}
