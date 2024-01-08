package bitweb.wordcloud.textfileword;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TextFileWordRepository extends JpaRepository<TextFileWord, Integer> {
    @Modifying
    @Query(value= """
            insert into text_files_words (counter, text_file_infos_id, words_id)
            values (?1, ?2, ?3)
            on conflict(text_file_infos_id, words_id) do update
            set counter = text_files_words.counter + ?1""",
            nativeQuery = true)
    void saveTextFileWord(int counter, long textFileInfosId, long wordsId);
}