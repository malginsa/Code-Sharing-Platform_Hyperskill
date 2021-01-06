package platform;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public interface CodeRepository extends CrudRepository<CodeSnippet, Integer> {

    void deleteAll();

    List<CodeSnippet> findTop10ByOrderByDateDesc();

}
