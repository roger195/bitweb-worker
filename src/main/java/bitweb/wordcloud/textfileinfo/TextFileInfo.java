package bitweb.wordcloud.textfileinfo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "text_file_infos")
public class TextFileInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "identifier", nullable = false)
    private UUID identifier;

    @Enumerated(EnumType.STRING)
    @Column(name="status")
    private UploadStatus uploadStatus;

    @Column(name = "parts_amount", nullable = false)
    private int partsAmount;
}