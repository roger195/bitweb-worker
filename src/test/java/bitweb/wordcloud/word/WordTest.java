package bitweb.wordcloud.word;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class WordTest {

    @Test
    void wordEntityTest() {
        long id = 1L;
        String wordValue = "TestWord";

        Word word = new Word();
        word.setId(id);
        word.setWord(wordValue);

        assertThat(word.getId()).isEqualTo(id);
        assertThat(word.getWord()).isEqualTo(wordValue);
    }
}
