package go.jrsierra.jpa.books;

import go.jrsierra.jpa.helper.TsVectorType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;
import org.postgresql.util.PGobject;

import java.sql.SQLException;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    @Column(name = "publish_date")
    private Long publishDate;
    private String snippet;
    @Column(name = "page_count")
    private Integer pageCount;
    private Long price;

    @Type(TsVectorType.class)
    @Column(name = "text_search_vectorized", columnDefinition = "tsvector")
    private PGobject textSearchVectorized;

    @PrePersist
    @PreUpdate
    private void preInsertOrUpdate() throws SQLException {
        String data = getPlainTextFieldsFromSearch();

        PGobject pgo = new PGobject();
        pgo.setType("tsvector");
        pgo.setValue(toTsvectorFormat(data));

        this.textSearchVectorized = pgo;
    }

    private String getPlainTextFieldsFromSearch() {
        return Stream.of(this.title,
                        this.subtitle,
                        this.authors,
                        this.description
                )
                .filter(Objects::nonNull)
                .map(s -> s.concat(" "))
                .collect(Collectors.joining())
                .replace("'", "''");
    }

    private String toTsvectorFormat(String data) {
        return String.format("to_tsvector('spanish', '%s') ", data);
    }

}
