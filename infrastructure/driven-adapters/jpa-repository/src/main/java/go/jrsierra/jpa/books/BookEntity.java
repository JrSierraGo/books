package go.jrsierra.jpa.books;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Entity(name = "book")
public class BookEntity {

    @Id
    private String id;
    private String title;
    private String subtitle;
    private String authors;
    private String description;
    @Column(name = "page_count")
    private long pageCount;
    private String snippet;
    @Column(name = "text_search_vectorized")
    private String textSearchVectorized;
}
