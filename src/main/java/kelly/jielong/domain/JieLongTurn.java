package kelly.jielong.domain;

public class JieLongTurn {
    private int seat;
    private Card card;

    public JieLongTurn(int seat, Card card) {
        this.seat = seat;
        this.card = card;
    }

    public int getSeat() {
        return seat;
    }

    public Card getCard() {
        return card;
    }
}
