package bitweb.wordcloud.textfilepart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TextFilePartRepository extends JpaRepository<TextFilePart, Integer> {

    Integer countAllByTextFileInfosIdEqualsOrderByPartNumber(long textFileInfosId);
}