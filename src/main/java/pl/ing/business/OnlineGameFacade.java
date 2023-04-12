package pl.ing.business;

import pl.ing.business.dto.onlinegame.Clan;
import pl.ing.business.dto.onlinegame.Order;
import pl.ing.business.dto.onlinegame.Players;

import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class OnlineGameFacade {
    private static final ClansComparator CLANS_COMPARATOR = new ClansComparator();
    public List<List<Order>> calculate(Players players) {
        List<Clan> clans = makeValidClansInOrder(players);
        List<List<Order>> orders = new LinkedList<>();
        while (!clans.isEmpty()) {
            List<Clan> matchingClans = findMatchingClans(clans, players.getGroupCount());
            List<Order> orderGroup = matchingClans.stream()
                    .map(c -> new Order(c.getNumberOfPlayers(), c.getPoints()))
                    .toList();
            orders.add(orderGroup);
            matchingClans.forEach(clans::remove);
        }
        return orders;
    }

    private List<Clan> findMatchingClans(List<Clan> clans, int groupCount) {
        List<Clan> matchingClans = new LinkedList<>();
        int currentGroupCount = 0;
        for (Clan clan : clans) {
            if (currentGroupCount + clan.getNumberOfPlayers() <= groupCount) {
                matchingClans.add(clan);
                currentGroupCount += clan.getNumberOfPlayers();
            }
            if (currentGroupCount == groupCount)
                break;
        }
        return matchingClans;
    }

    private List<Clan> makeValidClansInOrder(Players players) {
        return players.getClans().stream()
                .filter(clan -> clan.getNumberOfPlayers() <= players.getGroupCount())
                .sorted(CLANS_COMPARATOR)
                .collect(Collectors.toList());
    }

    private static class ClansComparator implements Comparator<Clan> {
        @Override
        public int compare(Clan o1, Clan o2) {
            if (o1.getPoints() == o2.getPoints()) {
                return o1.getNumberOfPlayers() - o2.getNumberOfPlayers();
            }
            else return -1 * (o1.getPoints() - o2.getPoints());
        }
    }
}
