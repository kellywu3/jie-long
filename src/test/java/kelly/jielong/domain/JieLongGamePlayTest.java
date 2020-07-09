package kelly.jielong.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class JieLongGamePlayTest {
    private static final String[] playerNames = new String[] {"Maggie", "Kelly", "Daniel", "Jamie"};
    
    @Test
    public void testJieLongGamePlayConstructor() {
        Player[] players = new Player[playerNames.length];
        for(int i = 0; i < playerNames.length; i++) {
            players[i] = new Player(i, playerNames[i]);
        }
        JieLongGamePlay game = new JieLongGamePlay(players);
        int cardsPerPlayer = DeckManager.BASE_CARDS / playerNames.length;
        HashSet<Card> allCards = new HashSet<>();
        for(Player p : players) {
            ArrayList<Card> hand = game.getPlayerHand(p);
            Assertions.assertTrue(Collections.disjoint(allCards, hand));
            allCards.addAll(hand);
            int cards = hand.size();
            Assertions.assertEquals(cardsPerPlayer, cards);
        }
        Assertions.assertEquals(DeckManager.BASE_CARDS, allCards.size());
    }
}
