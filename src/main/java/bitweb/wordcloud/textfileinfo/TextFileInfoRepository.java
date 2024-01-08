package bitweb.wordcloud.textfileinfo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TextFileInfoRepository extends JpaRepository<TextFileInfo, Integer> {
    TextFileInfo getFirstById(long id);
}