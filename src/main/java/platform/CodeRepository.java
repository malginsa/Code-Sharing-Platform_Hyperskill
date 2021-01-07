package platform;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface CodeRepository extends CrudRepository<CodeSnippet, UUID> {

    void deleteAll();

    List<CodeSnippet> findTop10ByIsRestrictedByTimeFalseAndIsRestrictedByViewsFalseOrderByDateDesc();

    default CodeSnippet decreaseViews(CodeSnippet codeSnippet) {
        if(codeSnippet.getViews() > 0) {
            codeSnippet.decreaseViews();
            return save(codeSnippet);
        } else {
            return codeSnippet;
        }
    }
}
