package bitweb.wordcloud;

import bitweb.wordcloud.textfilepart.TextFilePart;
import bitweb.wordcloud.textfilepart.TextFilePartRepository;
import bitweb.wordcloud.textfileword.TextFileWordRepository;
import bitweb.wordcloud.word.Word;
import bitweb.wordcloud.textfileinfo.TextFileInfo;
import bitweb.wordcloud.textfileinfo.TextFileInfoRepository;
import bitweb.wordcloud.textfileinfo.UploadStatus;
import bitweb.wordcloud.word.WordRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class TextService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TextService.class);
    private final TextFileInfoRepository textFileInfoRepository;
    private final TextFilePartRepository textFilePartRepository;
    private final WordRepository wordRepository;
    private final TextFileWordRepository textFileWordRepository;
    private static final String[] filteredWords =  {"a", "an", "the", "and", "or", "in", "on",
            "at", "by", "of", "to", "i", "s"};

    public TextService(TextFileInfoRepository textFileInfoRepository,
                       TextFilePartRepository textFilePartRepository,
                       TextFileWordRepository textFileWordRepository,
                       WordRepository wordRepository) {
        this.textFileInfoRepository = textFileInfoRepository;
        this.textFilePartRepository = textFilePartRepository;
        this.textFileWordRepository = textFileWordRepository;
        this.wordRepository = wordRepository;
    }

    private void checkUploadStatus(TextDTO textDTO) {
        int savedFilePartsCount = textFilePartRepository
                .countAllByTextFileInfosIdEqualsOrderByPartNumber(textDTO.getId());
        if (savedFilePartsCount == textDTO.getPartsTotal()) {
            setTextFileInfoStatus(textDTO, UploadStatus.COMPLETED);
        }
    }

    public Map<String, Long> getWordsMap(List<String> words) {
        List<Word> wordsList = wordRepository.findAllByWordIn(words);
        return wordsList.stream()
                .collect(
                        HashMap::new,
                        (map, word) -> map.put(word.getWord(), word.getId()),
                        HashMap::putAll
                );
    }

    private HashMap<String, Integer> getStringIntegerHashMap(String content) {
        String[] wordsArray = content
                .replaceAll("-\\r?\\n", "")
                .split("\\W+");
        HashMap<String, Integer> wordsMap = new HashMap<>();
        for (String word : wordsArray) {
            String lowerCaseWord = word.toLowerCase();
            int currentCount = wordsMap.getOrDefault(lowerCaseWord, 0);
            wordsMap.put(lowerCaseWord, currentCount+1);
        }
        for (String word : filteredWords) {
            wordsMap.remove(word);
        }
        return wordsMap;
    }

    @Transactional
    public void processMessage(TextDTO textDTO){
        saveTextFilePart(textDTO);

        HashMap<String, Integer> wordsMap = getStringIntegerHashMap(textDTO.getText());
        List<String> textWords = new ArrayList<>(wordsMap.keySet());
        Map<String, Long> databaseWordsMap = getWordsMap(textWords);

        for (Map.Entry<String,Integer> wordEntry : wordsMap.entrySet()) {
            Long databaseWordKey = databaseWordsMap.get(wordEntry.getKey());
            if (databaseWordKey != null) {
                textFileWordRepository
                        .saveTextFileWord(
                                wordEntry.getValue(),
                                textDTO.getId(),
                                databaseWordKey
                        );
            }
        }
        checkUploadStatus(textDTO);
    }

    public TextFilePart saveTextFilePart(TextDTO textDTO){
        return textFilePartRepository.save(
            new TextFilePart(
                    textDTO.getText(),
                    textDTO.getPartNumber(),
                    textDTO.getId()
            )
        );
    }

    public TextFileInfo setTextFileInfoStatus(TextDTO textDTO, UploadStatus uploadStatus){
        TextFileInfo textFileInfo = textFileInfoRepository.getFirstById(textDTO.getId());
        if (!(textFileInfo.getUploadStatus() == uploadStatus)) {
            textFileInfo.setUploadStatus(uploadStatus);
            textFileInfo = textFileInfoRepository.save(textFileInfo);
            LOGGER.info(String.format(
                    "File(%s) upload %s!",
                    textFileInfo.getIdentifier(),
                    uploadStatus.toString().toLowerCase())
            );
        }
        return textFileInfo;
    }
}
