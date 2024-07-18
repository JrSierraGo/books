package go.jrsierra.api.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class RetailPrice{
    private Integer amount;
    private String currencyCode;
}
