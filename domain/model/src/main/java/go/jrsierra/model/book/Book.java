package go.jrsierra.model.book;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Book {
    private String id;
    private String title;
    private String subtitle;
    private String authors;
    private String description;
    private Long publishDate;
    private String snippet;
    private Integer pageCount;
    private Long price;
    private String textSearchVectorized;

}
