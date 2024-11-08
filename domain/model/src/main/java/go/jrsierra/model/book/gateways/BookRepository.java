package go.jrsierra.model.book.gateways;

import go.jrsierra.model.book.Book;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface BookRepository {

    Mono<Book> save(Book book);

    Flux<Book> saveAll(List<Book> books);

    Flux<Book> findBooksByText(String criteria);
}
