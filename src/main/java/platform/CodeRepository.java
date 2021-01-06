package platform;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface CodeRepository extends CrudRepository<CodeSnippet, UUID> {

    void deleteAll();

    List<CodeSnippet> findTop10ByOrderByDateDesc();

}
