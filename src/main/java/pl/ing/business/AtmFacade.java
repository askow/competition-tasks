package pl.ing.business;

import pl.ing.business.dto.ATM;
import pl.ing.business.dto.RequestType;
import pl.ing.business.dto.Task;

import javax.enterprise.context.ApplicationScoped;
import java.util.*;
import java.util.stream.Collectors;

@ApplicationScoped
public class AtmFacade {
    private static final TaskRequestPriorityComparator TASK_REQUEST_PRIORITY_COMPARATOR = new TaskRequestPriorityComparator();
    public List<ATM> calculateOrder(List<Task> serviceTasks) {
        return serviceTasks.stream()
                .collect(Collectors.groupingBy(Task::getRegion))
                .values().stream()
                .map(this::processRegionTasks)
                .flatMap(Collection::stream)
                .map(Task::getATM)
                .toList();
    }

    private List<Task> processRegionTasks(List<Task> regionTasks) {
        Map<Integer, List<Task>> atmIdToTask = regionTasks.stream().collect(Collectors.groupingBy(Task::getAtmId));
        List<Task> highestPriorityTasks = atmIdToTask.values().stream()
                .map(this::selectHighestPriorityTask)
                .flatMap(Optional::stream)
                .toList();
        return highestPriorityTasks.stream().sorted(TASK_REQUEST_PRIORITY_COMPARATOR).toList();
    }

    private Optional<Task> selectHighestPriorityTask(List<Task> tasks) {
        return tasks.stream().max(TASK_REQUEST_PRIORITY_COMPARATOR);
    }

    private static class TaskRequestPriorityComparator implements Comparator<Task> {
        @Override
        public int compare(Task o1, Task o2) {
            final int difference = getRequestImportanceLevel(o1.getRequestType()) - getRequestImportanceLevel(o2.getRequestType());
            return -1 * difference;
        }

        private int getRequestImportanceLevel(RequestType requestType) {
            return switch (requestType) {
                case FAILURE_RESTART -> 3;
                case PRIORITY -> 2;
                case SIGNAL_LOW -> 1;
                case STANDARD -> 0;
            };
        }
    }
}