package go.jrsierra.usecase.bookhandler;

import go.jrsierra.model.book.Book;
import go.jrsierra.model.book.gateways.BookRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
public class BookHandlerUseCase {

    private final BookRepository bookRepository;

    public Flux<Book> findBooksByText(String criteria) {
        return bookRepository.findBooksByText(criteria);
    }
}
