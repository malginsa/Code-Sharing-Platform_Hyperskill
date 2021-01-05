package platform;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class CodeRepository {
    public static final int LATEST_SIZE = 10;

    private static int nextId = 1;

    List<CodeSnippet> repo = new ArrayList<>();

    Comparator<CodeSnippet> inverseDateComparator = new Comparator<>() {
        @Override
        public int compare(CodeSnippet codeSnippet1, CodeSnippet codeSnippet2) {
            return codeSnippet2.getDate().compareTo(codeSnippet1.getDate());
        }
    };

    public void clear() {
        repo.clear();
        nextId = 1;
    }

    public Optional<CodeSnippet> get(int id) {
        return repo.stream().filter(el -> el.getId() == id).findFirst();
    }

    public int add(CodeDTO codeDTO) {
        repo.add(new CodeSnippet(nextId, codeDTO.getCode(), LocalDateTime.now()));
        return nextId++;
    }

    public List<CodeSnippet> getTenLatest() {
        return repo.stream()
                .sorted(inverseDateComparator)
                .limit(LATEST_SIZE)
                .collect(Collectors.toList());
    }
}
