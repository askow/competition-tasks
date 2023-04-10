package pl.ing.e2e;

import com.fasterxml.jackson.databind.ObjectMapper;
import pl.ing.business.dto.RequestType;
import pl.ing.business.dto.Task;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class ATMTaskGenerator {
    static private final Random random = new Random();
    public static void main(String[] args) {
        final int N = 10_000 * 10_0;
        List<Task> tasks = generateTasks(N);
        try {
            File file = new File("/home/user/projects/atm_%s.json".formatted(N));
            new ObjectMapper().writeValue(file, tasks);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static List<Task> generateTasks(int n) {
        return IntStream.rangeClosed(0, n).mapToObj((i) -> new Task(
                randomRegion(),
                randomRequestType(),
                randomAtmId()
        )).toList();
    }

    private static int randomAtmId() {
        final int ATM_ID_MIN = 1;
        final int ATM_ID_MAX = 9999;
        return random.nextInt(ATM_ID_MAX - ATM_ID_MIN) + ATM_ID_MIN;
    }

    private static RequestType randomRequestType() {
        int idx = random.nextInt(RequestType.values().length);
        return RequestType.values()[idx];
    }

    private static int randomRegion() {
        final int REGION_ID_MIN = 1;
        final int REGION_ID_MAX = 9999;
        return random.nextInt(REGION_ID_MAX - REGION_ID_MIN) + REGION_ID_MIN;
    }
}
