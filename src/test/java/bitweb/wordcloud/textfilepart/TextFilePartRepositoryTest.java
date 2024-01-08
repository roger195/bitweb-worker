package bitweb.wordcloud.textfilepart;

import bitweb.wordcloud.TextFilePart.TextFilePart;
import bitweb.wordcloud.TextFilePart.TextFilePartRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class TextFilePartRepositoryTest {

    @Autowired
    private TextFilePartRepository textFilePartRepository;

    @Test
    void countAllByTextFileInfosIdEqualsOrderByPartNumberTest() {
        long textFileInfosId = 1L;

        textFilePartRepository.save(new TextFilePart("content", 1,textFileInfosId));
        textFilePartRepository.save(new TextFilePart("content", 2,textFileInfosId));
        textFilePartRepository.save(new TextFilePart("content", 3,textFileInfosId));

        Integer count = textFilePartRepository.countAllByTextFileInfosIdEqualsOrderByPartNumber(textFileInfosId);
        assertThat(count).isNotNull().isEqualTo(3);
    }
}

