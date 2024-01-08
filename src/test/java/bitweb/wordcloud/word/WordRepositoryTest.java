package bitweb.wordcloud.word;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class WordRepositoryTest {

    @Autowired
    private WordRepository wordRepository;

    @Test
    void wordEntityTest() {
        Word word = new Word();
        word.setWord("TestWord");

        Word savedWord = wordRepository.save(word);

        assertThat(savedWord.getId()).isGreaterThan(0);
        assertThat(savedWord.getWord()).isEqualTo("TestWord");

        Word retrievedWord = wordRepository.findById((int) savedWord.getId()).orElse(null);
        assertThat(retrievedWord).isNotNull();
        assertThat(retrievedWord.getId()).isEqualTo(savedWord.getId());
        assertThat(retrievedWord.getWord()).isEqualTo("TestWord");
    }
}
