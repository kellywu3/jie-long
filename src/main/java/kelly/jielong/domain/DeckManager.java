package kelly.jielong.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Random;

public class DeckManager {
    public static final int BASE_CARDS = 52;
    private static final int CARDS_PER_SUIT = 13;
    private static final Random random = new Random();
    
    public static Card obtainRandomCard(int jokers) {
        int randomNum = random.nextInt(BASE_CARDS + jokers);
        return numToCard(randomNum);
    }
    
    public static Card[] generateRandomDeck(Integer jokers) {
        int numCards = BASE_CARDS + (jokers == null ? 0 : jokers);
        HashSet<Integer> initCardList = new HashSet<>();
        ArrayList<Card> numDeck = new ArrayList<>();
        for(int i = 0; i < numCards; i++) {
            initCardList.add(i);
        }
        for(int j = 0; j < numCards; j++) {
            int h = removeRandom(initCardList);
            Card card = numToCard(h);
            numDeck.add(card);
        }
        Card[] deck = numDeck.toArray(new Card[numDeck.size()]);
        return deck;
    }
    
    public static <T> T removeRandom(Collection<T> collection) {
        int r = random.nextInt(collection.size());
        T t = itemAt(collection, r);
        collection.remove(t);
        return t;
    }
    
    public static <T> T itemAt(Collection<T> collection, int idx) {
        for(T t : collection) {
            if(idx-- > 0) {
                continue;
            }
            return t;
        }
        return null;
    }
    
    public static String toShortHand(Card card) {
        StringBuilder sb = new StringBuilder();
        sb.append(card.getSuit().asChar());
        sb.append(rankAsChar(card.getRank()));  
        return sb.toString();
    }
    
    public static Card fromShortHand(String card) {
        char s = card.charAt(0);
        char r = card.charAt(1);
        StringBuilder sb = new StringBuilder()
            .append(s)
            .append(r)
            ;
        return new Card(sb.toString());
    }
    
    public static Card numToCard(int num) {
        Suit s = Suit.asSuit(num / CARDS_PER_SUIT);
        int r = (num % CARDS_PER_SUIT);
        return new Card(cardNameFrom(s, r));
    }
    
    public static String cardNameFrom(Suit suit, int rank) {
        char s = suit.asChar();
        char r = DeckManager.rankAsChar(rank);
        StringBuilder sb = new StringBuilder()
                .append(s)
                .append(r)
                ;
        return sb.toString();
    }
    
    public static int cardToNum(Card card) {
        int suitNum = card.getSuit().ordinal() * CARDS_PER_SUIT;
        int rank = card.getRank();
        return suitNum + rank;
    }
    
    /**
     * Converts a rank number to a char that represents the rank.
     * * A = ace
     * * T = ten
     * * J = jack
     * * Q = queen
     * * K = king
     * @param rank
     * @return
     */
    public static char rankAsChar(int rank) {
        switch(rank) {
        case 13 :
            return 'Z';
        case 0:
            return 'A';
        case 9:
            return 'T';
        case 10:
            return 'J';
        case 11:
            return 'Q';
        case 12:
            return 'K';
        default:
            return (char) ('1' + rank);
        }
    }
    
    public static int charAsRank(char c) {
        switch(c) {
        case 'Z':
            return 13;
        case 'A':
            return 0;
        case 'T':
            return 9;
        case 'J':
            return 10;
        case 'Q':
            return 11;
        case 'K':
            return 12;
        default:
            return (int) (c - '1');
        }
    }
}
