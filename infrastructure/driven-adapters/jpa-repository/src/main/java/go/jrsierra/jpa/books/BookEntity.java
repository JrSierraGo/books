package go.jrsierra.jpa.books;

import go.jrsierra.jpa.helper.TsVectorType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;
import org.postgresql.util.PGobject;

import java.sql.SQLException;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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

        PGobject pgo = new PGobject();
        pgo.setType("tsvector");
        pgo.setValue(getTsVector());

        this.textSearchVectorized = pgo;
    }

    private String getTsVector() {
        return Map.of('A', normalizeText(this.title),
                        'B', normalizeText(this.subtitle),
                        'C', normalizeText(this.description),
                        'D', normalizeText(this.authors)

                )
                .entrySet()
                .stream()
                .map(entry -> setWeight(entry.getValue(), entry.getKey()))
                .collect(Collectors.joining(" || "));
    }

    private String normalizeText(String title) {
        return Optional.ofNullable(title)
                .map(s -> s.replace("'", "''"))
                .orElse("");
    }

    private String toTsvectorFormat(String data) {
        return String.format("to_tsvector('spanish', '%s') ", data);
    }

    private String setWeight(String data, Character weight){
        return String.format("setweight(%s, '%s') ", toTsvectorFormat(data),  weight);
    }

}
