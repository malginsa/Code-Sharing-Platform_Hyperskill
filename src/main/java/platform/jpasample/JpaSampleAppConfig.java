package platform.jpasample;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

// mark this class as Configuration to run method runApplication
public class JpaSampleAppConfig {

    @Bean
    public CommandLineRunner runApplication(TaskRepository taskRepository) {
        return args -> {
            performCreateOperations(taskRepository);
            performUpdateOperations(taskRepository);
        };
    }

    private void performCreateOperations(TaskRepository taskRepository) {
        Task articleTask = new Task("Finish article", "Finish the article and send for review", true, 0);
        taskRepository.save(articleTask);

        Task speechTask = new Task("Meeting speech", "Prepare the speech for the meeting", false, 2);
        taskRepository.save(speechTask);

        Task drTask = new Task("Call dr Robbins", "Cancel the visit", true, 1);
        taskRepository.save(drTask);

        Task bookingTask = new Task("Book a hotel", "Book a hotel for vacation", true, 4);
        Task savedTask = taskRepository.save(bookingTask);

        System.out.println(" --- List of enabled tasks:");
        taskRepository.findBySummaryContaining("hotel").forEach(System.out::println);

        System.out.println(" --- Count of enabled tasks: " + taskRepository.countByEnabled(true));

        System.out.println(" --- between 2 and 4 priority: " + taskRepository.findByPriorityBetween(2, 4));

        System.out.println(" --- findByPriorityAndSummaryNotContains: " +
                taskRepository.findByPriorityAndSummaryNotContains(2, "meeting"));
    }

    private void performUpdateOperations(TaskRepository taskRepository) {
        Task task = taskRepository.findByTitle("Meeting speech");
        if (task != null) {
            task.setEnabled(true);
            taskRepository.save(task);
        }
    }

}
