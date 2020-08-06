package kelly.jielong.web;

import kelly.jielong.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;

@Controller
@RequestMapping("/api/card")
public class DeckController {
    private static final Logger logger = LoggerFactory.getLogger(DeckController.class);
    private HashSet<SseEmitter> emitters = new HashSet<>();
    private HashSet<SseEmitter> toBeRemoved = new HashSet<>();
    
    @Autowired
    private JieLongGame jieLongGame;

    private synchronized void publishEmitterEvent(SseEmitter.SseEventBuilder event) {
        emitters.removeAll(toBeRemoved);
        for(SseEmitter emitter : emitters) {
            try {
                emitter.send(event);
            } catch(IOException e) {
                logger.info("Publish event error:", e);
                toBeRemoved.add(emitter);
            }
        }
    }

    private synchronized SseEmitter createEmitter(){
        SseEmitter emitter = new SseEmitter();
//        emitter.onError((err) -> {
//            logger.warn("Emitter error:", err);
//            toBeRemoved.add(emitter);
//        });

//        emitter.onTimeout(() -> {
//            logger.warn("Emitter Timed out");
//            toBeRemoved.add(emitter);
//        });

        emitter.onCompletion(() -> {
            logger.warn("Emitter Completed");
            toBeRemoved.add(emitter);
        });

        emitters.add(emitter);
        return emitter;
    }

//    private synchronized void removeEmitter(SseEmitter emitter) {
//        emitters.remove(emitter);
//    }
    
    @GetMapping("/randomcard")
    @ResponseBody
	public Card generateRandomCard() {
        logger.info("generate random card");
	    return DeckManager.obtainRandomCard(0);
	}
    
    @GetMapping("/jie-long/{gameId}/players")
    @ResponseBody
    public Player[] getPlayers(@PathVariable int gameId) {
        JieLongGamePlay game = jieLongGame.findGameById(gameId);
        return game.getPlayers();
    }
    
    @GetMapping("/randomdeck")
    @ResponseBody
    public Card[] generateRandomDeck(@RequestParam(name = "j", required = false) Integer jokers) {
        logger.info("generate random deck - {}", jokers);
        return DeckManager.generateRandomDeck(jokers);
    }
    
    @GetMapping("/randomdeck2/{jokers}")
    @ResponseBody
    public Card[] generateRandomDeck2(@PathVariable Integer jokers) {
        logger.info("generate random deck 2 - {}", jokers);
        return DeckManager.generateRandomDeck(jokers);
    }
    
    @GetMapping("/jie-long/{gameId}/{playerId}")
    @ResponseBody
    public Collection<Card> playerCards(
        @PathVariable Integer gameId,
        @PathVariable Integer playerId
    ) {
        logger.info("generatePlayerHands: {}, {}", gameId, playerId);
        logger.info("generate player hands");
        return jieLongGame.getPlayerCards(gameId, playerId);
    }
    
    @PostMapping("/jie-long/{gameId}/{playerId}/{card}")
    @ResponseBody
    public boolean playCard(
            @PathVariable int gameId
            ,@PathVariable int playerId
            ,@PathVariable Card card) 
    {
        JieLongGamePlay gp = jieLongGame.findGameById(gameId);
        Player p = gp.findPlayerByIdx(playerId);
        boolean result = gp.playCard(p, card);
        return result;
    }

    @PostMapping("/jie-long/games")
    @ResponseBody
    public Map<Integer, JieLongGamePlay> listGames() {
        return JieLongGame.getGames();
    }

//    @PostMapping("/jie-long/joingame/{gameId}/{playerId}/{name}")
//    @ResponseBody
//    public int joinGame(int gameId, int playerId, String name) {
//        int seatNum;
//    }

    @GetMapping("/jie-long/emitter")
    public SseEmitter getEmitters() {
        return createEmitter();
    }

    private static int id = 0;
    @GetMapping("/jie-long/emitter/test")
    @ResponseBody
    public String testEmitter() {
        SseEmitter.SseEventBuilder seb = SseEmitter.event()
            .data("hello-" + id)
            .id(Integer.toString(id))
            ;
        publishEmitterEvent(seb);
        return "OK" + (id++);
    }
}
