package kelly.jielong.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a player in the game.
 * @author kelly
 *
 */
public class Player {
    @JsonProperty("playerId")
    private int playerId;
    private String name;
    
    public Player(int playerId, String name) {
        this.playerId = playerId;
        this.name = name;
    }
    
    public int getPlayerId() {
        return playerId;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + playerId;
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
        if (playerId != other.playerId)
            return false;
        return true;
    }
}
