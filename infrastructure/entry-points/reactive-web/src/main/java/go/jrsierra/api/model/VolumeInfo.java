package go.jrsierra.api.model;

import lombok.*;

import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class VolumeInfo{
    private String title;
    private String subtitle;
    private ArrayList<String> authors;
    private String publishedDate;
    private String description;
    private Integer pageCount;
}
