package go.jrsierra.usecase.bookcommand;

import go.jrsierra.model.book.Book;
import go.jrsierra.model.book.gateways.BookRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RequiredArgsConstructor
public class BookCommandUseCase {

    private final BookRepository bookRepository;

    public Flux<Book> createBooks(List<Book> books){
        return Mono.just(books)
                .flatMapMany(bookRepository::saveAll);
    }

}
