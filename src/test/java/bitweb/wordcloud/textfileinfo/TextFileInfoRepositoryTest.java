package bitweb.wordcloud.textfileinfo;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class TextFileInfoRepositoryTest {

    @Autowired
    private TextFileInfoRepository textFileInfoRepository;

    @Test
    @DirtiesContext
    void testGetFirstById() {
        UUID identifier = UUID.randomUUID();
        TextFileInfo textFileInfo = new TextFileInfo();
        textFileInfo.setIdentifier(identifier);
        textFileInfo.setUploadStatus(UploadStatus.PROCESSING);
        textFileInfo.setPartsAmount(3);

        textFileInfoRepository.save(textFileInfo);

        Optional<TextFileInfo> optionalTextFileInfo = textFileInfoRepository.findById((int) textFileInfo.getId());

        assertTrue(optionalTextFileInfo.isPresent());
        TextFileInfo retrievedTextFileInfo = optionalTextFileInfo.get();
        assertEquals(identifier, retrievedTextFileInfo.getIdentifier());
        assertEquals(UploadStatus.PROCESSING, retrievedTextFileInfo.getUploadStatus());
        assertEquals(3, retrievedTextFileInfo.getPartsAmount());
    }
}

