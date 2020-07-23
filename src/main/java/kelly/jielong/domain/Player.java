package kelly.jielong.domain;

/**
 * Represents a player in the game.
 * @author kelly
 *
 */
public class Player {
    private int playerID;
    private String name;
    
    public Player(int playerID, String name) {
        this.playerID = playerID;
        this.name = name;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + playerID;
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
        Player other = (Player) obj;
        if (playerID != other.playerID)
            return false;
        return true;
    }
}
