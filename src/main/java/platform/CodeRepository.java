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

    Comparator<Code> inverseIdComparator = new Comparator<>() {
        @Override
        public int compare(Code o1, Code o2) {
            return Integer.compare(o2.getId(), o1.getId());
        }
    };

    public Optional<Code> get(int id) {
        return repo.stream().filter(el -> el.getId() == id).findFirst();
    }

    public int add(CodeDTO codeDTO) {
        repo.add(new Code(nextId, codeDTO.getCode(), LocalDateTime.now().withNano(0)));
        return nextId++;
    }

    public List<Code> getTenLatest() {
        return repo.stream()
                .sorted(inverseIdComparator)
                .limit(LATEST_SIZE)
                .collect(Collectors.toList());
    }
}
