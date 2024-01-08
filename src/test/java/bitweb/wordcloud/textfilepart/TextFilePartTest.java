package bitweb.wordcloud.textfilepart;

import bitweb.wordcloud.TextFilePart.TextFilePart;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class TextFilePartTest {

    @Test
    void textFilePartEntityTest() {
        TextFilePart textFilePart = new TextFilePart("Test content", 1, 123L);

        assertThat(textFilePart.getId()).isZero();
        assertThat(textFilePart.getContent()).isEqualTo("Test content");
        assertThat(textFilePart.getPartNumber()).isEqualTo(1);
        assertThat(textFilePart.getTextFileInfosId()).isEqualTo(123L);
    }
}
