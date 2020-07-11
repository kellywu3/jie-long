package kelly.jielong.domain;

public class Card {
    private Suit suit;
    private int rank;
    private String cardName;
    
    public Card(String cardName) {
        this.cardName = cardName;
        this.suit = Suit.asSuit(cardName.charAt(0));
        this.rank = DeckManager.charAsRank(cardName.charAt(1));
    }

    public String getCardName() {
        return cardName;
    }

    public Suit getSuit() {
        return suit;
    }

    public int getRank() {
        return rank;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + rank;
        result = prime * result + ((suit == null) ? 0 : suit.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Card other = (Card) obj;
        if (rank != other.rank)
            return false;
        if (suit != other.suit)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Card [suit=" + suit + ", rank=" + rank + "]";
    }
}
