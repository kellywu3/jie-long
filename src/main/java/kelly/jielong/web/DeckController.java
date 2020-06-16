package kelly.jielong.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kelly.jielong.domain.Card;
import kelly.jielong.domain.DeckManager;

@Controller
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
}
