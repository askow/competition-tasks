package pl.ing.e2e;

import com.fasterxml.jackson.databind.ObjectMapper;
import pl.ing.business.dto.onlinegame.Clan;
import pl.ing.business.dto.onlinegame.Players;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class OnlineGameGenerator {
    static private final Random random = new Random();
    public static void main(String[] args) {
        final int N = 20; //max 20000
        Players players = generatePlayers(N);
        try {
            File file = new File("/home/user/projects/og_%s.json".formatted(N));
            new ObjectMapper().writeValue(file, players);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static int randomGroupCount() {
        final int MIN_GROUP_COUNT = 1;
        final int MAX_GROUP_COUNT = 1000;
        return random.nextInt(MAX_GROUP_COUNT - MIN_GROUP_COUNT) + MIN_GROUP_COUNT;
    }

    private static Players generatePlayers(int clanSize) {
        int groupCount = randomGroupCount();
        return new Players(groupCount, generateClans(groupCount, clanSize));
    }

    private static List<Clan> generateClans(int groupCount, int clanSize) {
        return IntStream.rangeClosed(0,clanSize)
                .mapToObj((i) -> new Clan(randomGroupPlayers(groupCount), randomPoints()))
                .toList();
    }

    private static int randomPoints() {
        final int MIN_POINTS = 1;
        final int MAX_POINTS = 1000000;
        return random.nextInt(MAX_POINTS - MIN_POINTS) + MIN_POINTS;
    }

    private static int randomGroupPlayers(int groupCount) {
        final int MIN_GROUP_COUNT = 1;
        final int MAX_GROUP_COUNT = groupCount;
        return random.nextInt(MAX_GROUP_COUNT - MIN_GROUP_COUNT) + MIN_GROUP_COUNT;
    }


}
