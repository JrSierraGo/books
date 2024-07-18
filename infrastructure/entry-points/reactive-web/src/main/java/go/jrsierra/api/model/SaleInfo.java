package go.jrsierra.api.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class SaleInfo{
    private RetailPrice retailPrice;
}
