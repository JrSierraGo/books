package go.jrsierra.api;

import go.jrsierra.model.book.Book;
import go.jrsierra.usecase.bookhandler.BookHandlerUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping(value = "/books", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class BookHandler {

    private final BookHandlerUseCase bookHandlerUseCase;

    @GetMapping(path = "/search")
    Flux<Book> getBooksByText(@RequestParam String criteria) {
        return bookHandlerUseCase.findBooksByText(criteria);
    }
}
