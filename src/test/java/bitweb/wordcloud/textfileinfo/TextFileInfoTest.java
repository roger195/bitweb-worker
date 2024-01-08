package bitweb.wordcloud.textfileinfo;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class TextFileInfoTest {

    @Test
    public void testAllArgsConstructor() {
        long id = 1L;
        UUID identifier = UUID.randomUUID();
        UploadStatus uploadStatus = UploadStatus.PROCESSING;
        int partsAmount = 5;

        TextFileInfo textFileInfo = new TextFileInfo(id, identifier, uploadStatus, partsAmount);

        assertThat(textFileInfo.getId()).isEqualTo(id);
        assertThat(textFileInfo.getIdentifier()).isEqualTo(identifier);
        assertThat(textFileInfo.getUploadStatus()).isEqualTo(uploadStatus);
        assertThat(textFileInfo.getPartsAmount()).isEqualTo(partsAmount);
    }

    @Test
    public void testNoArgsConstructor() {
        TextFileInfo textFileInfo = new TextFileInfo();

        assertThat(textFileInfo).isNotNull();
    }
}
