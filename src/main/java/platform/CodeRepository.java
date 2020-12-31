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

    List<Code> repo = new ArrayList<>();

    Comparator<Code> inverseDateComparator = new Comparator<>() {
        @Override
        public int compare(Code code1, Code code2) {
            return code2.getDate().compareTo(code1.getDate());
        }
    };

    public Optional<Code> get(int id) {
        return repo.stream().filter(el -> el.getId() == id).findFirst();
    }

    public int add(CodeDTO codeDTO) {
        repo.add(new Code(nextId, codeDTO.getCode(), LocalDateTime.now()));
        return nextId++;
    }

    public List<Code> getTenLatest() {
        return repo.stream()
                .sorted(inverseDateComparator)
                .limit(LATEST_SIZE)
                .collect(Collectors.toList());
    }
}
