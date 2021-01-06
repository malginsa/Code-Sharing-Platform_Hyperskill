package platform;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CodeRepository extends CrudRepository<CodeSnippet, Integer> {

    void deleteAll();

    List<CodeSnippet> findTop10ByOrderByDateDesc();

}
