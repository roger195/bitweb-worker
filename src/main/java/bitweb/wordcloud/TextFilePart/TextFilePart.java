package bitweb.wordcloud.TextFilePart;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "text_file_parts")
public class TextFilePart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "part_number", nullable = false)
    private int partNumber;

    @JoinColumn(name = "text_file_infos_id", nullable = false)
    private long textFileInfosId;

    public TextFilePart(String content, int partNumber, long textFileInfosId) {
        this.content = content;
        this.partNumber = partNumber;
        this.textFileInfosId = textFileInfosId;
    }
}
