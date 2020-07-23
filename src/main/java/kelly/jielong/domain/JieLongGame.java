package kelly.jielong.domain;

import java.util.Collection;
import java.util.HashMap;

import org.springframework.stereotype.Service;

/**
 * Manages the logistics of the game.
 * @author kelly
 *
 */
@Service
public class JieLongGame {
    private static HashMap<Integer, JieLongGamePlay> games = new HashMap<>();
    
    public JieLongGamePlay findGameById(int id) {
        JieLongGamePlay result = games.get(id);
        if(result == null) {
            result = new JieLongGamePlay(generateNewPlayers(4), id);
            games.put(id, result);
        }
        return result;
    }
    
    private Player[] generateNewPlayers(int num) {
       Player[] result = new Player[num];
       for(int i = 0; i < num; i++) {
           Player p = new Player(i, Integer.toString(i));
           result[i] = p;
       }
       return result;
    }
    
    public Collection<Card> getPlayerCards(int id, int playerid) {
        JieLongGamePlay g = findGameById(id);
        return g.getCardsByIdx(playerid);
    }
}
