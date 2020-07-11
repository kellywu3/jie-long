package kelly.jielong.web;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kelly.jielong.domain.Card;
import kelly.jielong.domain.DeckManager;
import kelly.jielong.domain.JieLongGamePlay;
import kelly.jielong.domain.Player;

@Controller
@RequestMapping("/api/card")
public class DeckController {
    private static final Logger logger = LoggerFactory.getLogger(DeckController.class);
    @GetMapping("/randomcard")
    @ResponseBody
	public Card generateRandomCard() {
        logger.info("generate random card");
	    return DeckManager.obtainRandomCard(0);
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
    
    @GetMapping("/playerhands")
    @ResponseBody
    public LinkedHashMap<Player, ArrayList<Card>> generatePlayerHands() {
        Player[] players = new Player[4];
        for(int i = 0; i < players.length; i++) {
            players[i] = new Player(i, Integer.toString(i));
        }
        JieLongGamePlay game = new JieLongGamePlay(players);
        logger.info("generate player hands");
        return game.getPlayerHands();
    }
}
