package kelly.jielong;

import java.util.ArrayList;
import java.util.HashSet;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import kelly.jielong.domain.Card;
import kelly.jielong.domain.DeckManager;
import kelly.jielong.domain.Suit;

public class DeckManagerTest {
    @Test
    public void testRankAsChar() {
        int c = 0;
        Assertions.assertEquals('A',DeckManager.rankAsChar(c++));
        Assertions.assertEquals('2',DeckManager.rankAsChar(c++));
        Assertions.assertEquals('3',DeckManager.rankAsChar(c++));
        Assertions.assertEquals('4',DeckManager.rankAsChar(c++));
        Assertions.assertEquals('5',DeckManager.rankAsChar(c++));
        Assertions.assertEquals('6',DeckManager.rankAsChar(c++));
        Assertions.assertEquals('7',DeckManager.rankAsChar(c++));
        Assertions.assertEquals('8',DeckManager.rankAsChar(c++));
        Assertions.assertEquals('9',DeckManager.rankAsChar(c++));
        Assertions.assertEquals('T',DeckManager.rankAsChar(c++));
        Assertions.assertEquals('J',DeckManager.rankAsChar(c++));
        Assertions.assertEquals('Q',DeckManager.rankAsChar(c++));
        Assertions.assertEquals('K',DeckManager.rankAsChar(c++));
        Assertions.assertEquals('Z',DeckManager.rankAsChar(c++));
    }
    
    @Test
    public void testCharAsRank() {
        int c = 0;
        Assertions.assertEquals(c++,DeckManager.charAsRank('A'));
        Assertions.assertEquals(c++,DeckManager.charAsRank('2'));
        Assertions.assertEquals(c++,DeckManager.charAsRank('3'));
        Assertions.assertEquals(c++,DeckManager.charAsRank('4'));
        Assertions.assertEquals(c++,DeckManager.charAsRank('5'));
        Assertions.assertEquals(c++,DeckManager.charAsRank('6'));
        Assertions.assertEquals(c++,DeckManager.charAsRank('7'));
        Assertions.assertEquals(c++,DeckManager.charAsRank('8'));
        Assertions.assertEquals(c++,DeckManager.charAsRank('9'));
        Assertions.assertEquals(c++,DeckManager.charAsRank('T'));
        Assertions.assertEquals(c++,DeckManager.charAsRank('J'));
        Assertions.assertEquals(c++,DeckManager.charAsRank('Q'));
        Assertions.assertEquals(c++,DeckManager.charAsRank('K'));
        Assertions.assertEquals(c++,DeckManager.charAsRank('Z'));
    }
    
    @Test
    public void testCardToNum() {
        Assertions.assertEquals(new Card(DeckManager.cardNameFrom(Suit.HEART, 0)), DeckManager.numToCard(0));
        Assertions.assertEquals(52, DeckManager.cardToNum(new Card(DeckManager.cardNameFrom(Suit.JOKER, 0))));
        Assertions.assertEquals(53, DeckManager.cardToNum(new Card(DeckManager.cardNameFrom(Suit.JOKER, 1))));
    }
    
    @Test
    public void testNumToCard() {
        Assertions.assertEquals(new Card(DeckManager.cardNameFrom(Suit.JOKER, 0)), DeckManager.numToCard(52));
        Assertions.assertEquals(new Card(DeckManager.cardNameFrom(Suit.JOKER, 1)), DeckManager.numToCard(53));
    }
    
    @Test
    public void testItemAt() {
        ArrayList<Integer> col = new ArrayList<>();
        col.add(3);
        col.add(5);
        col.add(7);
        col.add(203);
        col.add(115);
        
        int i = 0;
        Assertions.assertEquals(3, DeckManager.itemAt(col, i++));
        Assertions.assertEquals(5, DeckManager.itemAt(col, i++));
        Assertions.assertEquals(7, DeckManager.itemAt(col, i++));
        Assertions.assertEquals(203, DeckManager.itemAt(col, i++));
        Assertions.assertEquals(115, DeckManager.itemAt(col, i++));
    }
    
    @Test
    public void testRemoveRandom() {
        HashSet<Integer> col = new HashSet<>();
        col.add(3);
        col.add(5);
        col.add(7);
        col.add(203);
        col.add(115);
        for(int i = 0; i < 5; i++) {
            Integer j = DeckManager.removeRandom(col);
            Assertions.assertNotNull(j);
            Assertions.assertEquals(4 - i, col.size());
        }
        Assertions.assertEquals(0, col.size());
    }
    
    @Test
    public void testGenerateRandomDeck() {
        Card[] deck = DeckManager.generateRandomDeck(0);
        int deckSize = deck.length;
        Assertions.assertEquals(52, deckSize);
        for(int i = 0; i < deckSize - 1; i++) {
            for(int j = i + 1; j < deckSize; j++) {
                Assertions.assertFalse(deck[i].equals(deck[j]));
            }
        }
    }
}
