package go.jrsierra.api;
import go.jrsierra.api.model.Item;
import go.jrsierra.api.model.RetailPrice;
import go.jrsierra.api.model.SearchInfo;
import go.jrsierra.model.book.Book;
import go.jrsierra.usecase.bookcommand.BookCommandUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/books", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class BookCommand {

    private final BookCommandUseCase commandUseCase;


    @PostMapping(path = "/create")
    public Flux<Book> commandName(@RequestBody List<Item> booksItems) {
        return Flux.fromIterable(booksItems)
                .map(book -> Book.builder()
                        .id(book.getId())
                        .title(book.getVolumeInfo().getTitle())
                        .subtitle(book.getVolumeInfo().getSubtitle())
                        .authors(Optional.ofNullable(book.getVolumeInfo().getAuthors()).map(Collection::stream).map(stringStream -> stringStream.collect(Collectors.joining("; "))).orElse(null))
                        .description(book.getVolumeInfo().getDescription())
                        .publishDate(getPublishDate(book.getVolumeInfo().getPublishedDate()))
                        .snippet(Optional.ofNullable(book.getSearchInfo()).map(SearchInfo::getTextSnippet).orElse(null))
                        .pageCount(book.getVolumeInfo().getPageCount())
                        .price(Optional.ofNullable(book.getSaleInfo().getRetailPrice()).map(RetailPrice::getAmount).map(Integer::longValue).orElse(0L))
                        .build()
                )
                .collectList()
                .flatMapMany(commandUseCase::createBooks);
    }

    private static long getPublishDate(String dateString) {
        try{
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate = LocalDate.parse(dateString, formatter);
            ZonedDateTime zonedDateTime = localDate.atStartOfDay(ZoneId.of(ZoneId.SHORT_IDS.get("EST")));
            return zonedDateTime.toInstant().toEpochMilli();
        }catch (Exception e){
            return 0L;
        }
    }
}
