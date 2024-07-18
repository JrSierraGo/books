package go.jrsierra.api.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Item {
    private String id;
    private VolumeInfo volumeInfo;
    private SaleInfo saleInfo;
    private SearchInfo searchInfo;
}
