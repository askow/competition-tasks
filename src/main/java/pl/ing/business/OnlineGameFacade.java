package pl.ing.business;

import pl.ing.business.dto.onlinegame.Clan;
import pl.ing.business.dto.onlinegame.Order;
import pl.ing.business.dto.onlinegame.Players;

import javax.enterprise.context.ApplicationScoped;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

@ApplicationScoped
public class OnlineGameFacade {
    public List<List<Order>> calculate(Players players) {
        //filter groups that will not match
        int capacity = players.getGroupCount();
        List<Clan> validClans = players.getClans().stream()
                .filter(clan -> clan.getNumberOfPlayers() <= capacity)
                .sorted(new ClansComparator())
                .toList();

//        List<List<Order>> orders = new LinkedList<>();
        Stack<List<Order>> stack = new Stack<>();
        while (!validClans.isEmpty()) {
            if (stack.empty()) {
                Clan clan = validClans.get(0);
                stack.push(List.of(new Order(clan.getNumberOfPlayers(), clan.getPoints())));
                validClans = validClans.stream().filter(c -> c != clan).toList();
            }
            else {
                List<Order> orders = stack.pop();
                int currentGroupSize = orders.stream().map(Order::getNumberOfPlayers).reduce(0, Integer::sum);
                AtomicInteger tmp = new AtomicInteger(currentGroupSize);
                List<Clan> matchingClans = validClans.stream()
                        .filter(c -> {
                            boolean a = tmp.get() + c.getNumberOfPlayers() <= capacity;
                            if (a)
                                tmp.addAndGet(c.getNumberOfPlayers());
                            return a;
                        })
                        .toList();
                if (matchingClans.isEmpty()) {
                    stack.push(orders);
                    stack.push(new LinkedList<>());
                }
                else {
                    Stream<Order> s2 = matchingClans.stream().map(c -> new Order(c.getNumberOfPlayers(), c.getPoints()));
                    stack.push(Stream.concat(orders.stream(), s2).toList());
                }
                validClans = validClans.stream().filter(c -> !matchingClans.contains(c)).toList();
            }
        }

        return stack.stream().toList();
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
