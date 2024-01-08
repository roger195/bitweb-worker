package bitweb.wordcloud;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TextDTO {
    private long id;
    private int partsTotal;
    private int partNumber;
    private String text;
}
