package pl.ing.business;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.ing.business.dto.ATM;
import pl.ing.business.dto.RequestType;
import pl.ing.business.dto.Task;

import java.util.List;

import static com.google.common.truth.Truth.assertThat;

public class AtmFacadeTest {
    private AtmFacade facade;
    @BeforeEach
    void setUp() {
        facade = new AtmFacade();
    }
    @Test
    void sample1() {
        List<Task> tasks = List.of(
                new Task(4, RequestType.STANDARD, 1),
                new Task(1, RequestType.STANDARD, 1),
                new Task(2, RequestType.STANDARD, 1),
                new Task(3, RequestType.PRIORITY, 2),
                new Task(3, RequestType.STANDARD, 1),
                new Task(2, RequestType.SIGNAL_LOW, 1),
                new Task(5, RequestType.STANDARD, 2),
                new Task(5, RequestType.FAILURE_RESTART, 1)
        );
        List<ATM> atms = facade.calculateOrder(tasks);

        assertThat(atms).containsExactly(
                new ATM(1, 1),
                new ATM(2, 1),
                new ATM(3, 2),
                new ATM(3, 1),
                new ATM(4, 1),
                new ATM(5, 1),
                new ATM(5, 2)
        );
    }

    @Test
    void sample2() {
        List<Task> tasks = List.of(
                new Task(1, RequestType.STANDARD, 2),
                new Task(1, RequestType.STANDARD, 1),
                new Task(2, RequestType.PRIORITY, 3),
                new Task(3, RequestType.STANDARD, 4),
                new Task(4, RequestType.STANDARD, 5),
                new Task(5, RequestType.PRIORITY, 2),
                new Task(5, RequestType.STANDARD, 1),
                new Task(3, RequestType.SIGNAL_LOW, 2),
                new Task(2, RequestType.SIGNAL_LOW, 1),
                new Task(3, RequestType.FAILURE_RESTART, 1)
        );
        List<ATM> atms = facade.calculateOrder(tasks);

        assertThat(atms).containsExactly(
                new ATM(1, 2),
                new ATM(1, 1),
                new ATM(2, 3),
                new ATM(2, 1),
                new ATM(3, 1),
                new ATM(3, 2),
                new ATM(3, 4),
                new ATM(4, 5),
                new ATM(5, 2),
                new ATM(5, 1)
        );
    }
}
