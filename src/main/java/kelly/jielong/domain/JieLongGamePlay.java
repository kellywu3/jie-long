package kelly.jielong.domain;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class JieLongGamePlay {
    private LinkedHashMap<Player, ArrayList<Card>> playerHands;
    
    public JieLongGamePlay(Player[] players) {
        playerHands = new LinkedHashMap<>();
        dealCards(players);

    }
    
    private void dealCards(Player[] players) {
        int numbOfPlayers = players.length;
        Card[] deck = DeckManager.generateRandomDeck(0);
        ArrayList<Card>[] hands =  new ArrayList[numbOfPlayers];
        for(int i = 0; i < numbOfPlayers; i++) {
            hands[i] = new ArrayList<>();
            playerHands.put(players[i], hands[i]);
        }
        for(int i = 0; i < DeckManager.BASE_CARDS; i++) {
            hands[i % numbOfPlayers].add(deck[i]);
        }
    }
    
    public boolean playCard(Player player, Card card) {
        return false;
    }
    
    public LinkedHashMap<Player, ArrayList<Card>> getPlayerHands() {
        return playerHands;
    }

    public ArrayList<Card> getPlayerHand(Player player) {
        return playerHands.get(player);
    }

    @Override
    public String toString() {
        return "JieLongGamePlay [playerHands=" + playerHands + "]";
    }
}
