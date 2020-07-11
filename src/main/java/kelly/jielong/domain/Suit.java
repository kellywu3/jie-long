package kelly.jielong.domain;

public enum Suit {
    HEART
    , SPADE
    , DIAMOND
    , CLUB
    , JOKER;
    
    public char asChar() {
        return name().charAt(0);
    }
    
    public static Suit asSuit(char c) {
        for(Suit s : values()) {
            if(s.name().charAt(0) == c) {
                return s;
            }
        }
        return null;
    }
    
    public static Suit asSuit(int i) {
        if(i < 0 || i > 4) {
            return null;
        } else {
            return Suit.values()[i];
        }
    }
}
