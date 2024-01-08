package bitweb.wordcloud.word;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WordRepository extends JpaRepository<Word, Integer> {
    List<Word> findAllByWordIn(List<String> wordList);
}