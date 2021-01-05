package platform.jpasample;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TaskRepository extends CrudRepository<Task, Long> {

    Task findByTitle(String title);

    List<Task> findAllByEnabled(boolean isEnabled);

    List<Task> findAllByEnabledOrderByPriorityAsc(boolean isEnabled);

    List<Task> findBySummaryContaining(String text);

    void deleteByTitle(String title);

    long countByEnabled(boolean enabled);

    List<Task> findByPriorityBetween(int fromPriority, int toPriority);

    List<Task> findByPriorityAndSummaryNotContains(int priority, String summary);
}
