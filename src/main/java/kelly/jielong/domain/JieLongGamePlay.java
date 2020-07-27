package kelly.jielong.domain;

import java.util.ArrayList;
import java.util.Collection;

public class JieLongGamePlay {
    private static final int CARDS_ON_TABLE_IDX = -1;
    private static final int CARDS_IN_DECK_IDX = -2;
    // Keeps track of each card's ownership and the cards on the table
    // -1 = on table
    // 0-5 = player hand
    // -2 = in deck
    private int[] cards;
    private Player[] players;
    private int gameId;
    
    public JieLongGamePlay(Player[] players, int gameId) {
        cards = new int[DeckManager.BASE_CARDS];
        this.players = players;
        this.gameId = gameId;
        dealCards();

    }
    
    private void dealCards() {
        int numbOfPlayers = players.length;
        Card[] deck = DeckManager.generateRandomDeck(0);
//        ArrayList<Card>[] hands =  new ArrayList[numbOfPlayers];
        
        int playeridx = 0;
        for(int cardidx = 0; cardidx < deck.length; cardidx++) {
            int cardnum = DeckManager.cardToNum(deck[cardidx]);
            cards[cardnum] = playeridx;
            playeridx++;
            playeridx = playeridx % numbOfPlayers;
        }
    }
    
    private int findPlayerIdx(Player p) {
        for(int i = 0; i < players.length; i++) {
            if(players[i].equals(p)) {
                return i;
            }
        }
        return Integer.MIN_VALUE;
    }
    
    public Player findPlayerByIdx(int idx) {
        for(int i = 0; i < players.length; i++) {
            if(players[i].getPlayerId() == idx) {
                return players[i];
            }
        }
        return null;
    }
    
    public Collection<Card> getCardsByIdx(int idx) {
        ArrayList<Card> result = new ArrayList<>();
        for(int i = 0; i < cards.length; i++) {
            if(cards[i] == idx) {
                result.add(DeckManager.numToCard(i));
            }
        }
        return result;        
    }
    
    public Collection<Card> getCardsOnTable() {
        return getCardsByIdx(CARDS_ON_TABLE_IDX);
    }
    
    public Collection<Card> getPlayerDeck(Player p) {
        int idx = findPlayerIdx(p);
        return getCardsByIdx(idx);
    }
    
    private boolean validatePlay(Card card) {
        int cardNum = DeckManager.cardToNum(card);
        int cardRank = card.getRank();
        if(cardRank < 6) {
            if(cards[cardNum + 1] == CARDS_ON_TABLE_IDX) {
                return true;
            }
        } else if(cardRank > 6) {
            if(cards[cardNum - 1] == CARDS_ON_TABLE_IDX) {
                return true;
            }
        } else {
            return true;
        }
        return false;
    }
    
    public boolean playCard(Player player, Card card) {
        // check if player has card
        int idx = findPlayerIdx(player);
        int cardNum = DeckManager.cardToNum(card);
        if(idx != cards[cardNum]) {
            return false;
        }
        // check if card can be played
        if(validatePlay(card) == false) {
            return false;
        }
        // update card ownership
        cards[cardNum] = CARDS_ON_TABLE_IDX;
        return true;
    }

    public Player[] getPlayers() {
        return players;
    }

    public int getGameId() {
        return gameId;
    }
}
