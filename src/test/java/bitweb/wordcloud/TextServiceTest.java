package bitweb.wordcloud;

import bitweb.wordcloud.TextFilePart.TextFilePartRepository;
import bitweb.wordcloud.TextFileWord.TextFileWordRepository;
import bitweb.wordcloud.textfileinfo.TextFileInfo;
import bitweb.wordcloud.textfileinfo.TextFileInfoRepository;
import bitweb.wordcloud.textfileinfo.UploadStatus;
import bitweb.wordcloud.word.Word;
import bitweb.wordcloud.word.WordRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
class TextServiceTest {

    @Mock
    private TextFileInfoRepository textFileInfoRepository;

    @Mock
    private WordRepository wordRepository;

    @InjectMocks
    private TextService textService;

    @Test
    void setTextFileInfoStatusTest() {
        TextDTO textDTO = new TextDTO();
        TextFileInfo textFileInfo = new TextFileInfo();

        when(textFileInfoRepository.getFirstById(textDTO.getId())).thenReturn(textFileInfo);
        when(textFileInfoRepository.save(any(TextFileInfo.class))).thenReturn(textFileInfo);

        TextFileInfo result = textService.setTextFileInfoStatus(textDTO, UploadStatus.COMPLETED);

        assertThat(result).isNotNull();
        assertThat(result.getUploadStatus()).isEqualTo(UploadStatus.COMPLETED);
    }

    @Test
    void getWordsMapTest() {
        List<String> words = Arrays.asList("word1", "word2", "word3");

        when(wordRepository.findAllByWordIn(words)).thenReturn(Arrays.asList(
                new Word(1L, "word1"),
                new Word(2L, "word2"),
                new Word(3L, "word3")
        ));

        Map<String, Long> result = textService.getWordsMap(words);

        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(3);
    }
}
