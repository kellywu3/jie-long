package kelly.jielong.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.HashSet;

@Controller
@RequestMapping("/api/message")
public class MessageController {
    private Logger logger = LoggerFactory.getLogger(MessageController.class);
    private HashSet<SseEmitter> emitters = new HashSet<>();
    private HashSet <SseEmitter> toBeRemoved = new HashSet<>();

    private synchronized SseEmitter createEmitter() {
        SseEmitter emitter = new SseEmitter();
        emitters.add(emitter);
        emitter.onCompletion(() -> {
            logger.info("On completion");
            toBeRemoved.add(emitter);
        });
        return emitter;
    }

    public synchronized void publishEmitterEvent(SseEmitter.SseEventBuilder event) {
        emitters.removeAll(toBeRemoved);
        for(SseEmitter emitter: emitters) {
            try {
                emitter.send(event);
            } catch(IOException e) {
                logger.info("Emitter send event error");
                toBeRemoved.add(emitter);
            }
        }
    }

    @GetMapping("/jie-long/emitter")
    private SseEmitter getEmitter() {
        return createEmitter();
    }

    @GetMapping("/jie-long/emitter/test/{var}")
    @ResponseBody
    private String emitterTest(@PathVariable int var) {
        SseEmitter.SseEventBuilder event = SseEmitter.event().data(var);
        publishEmitterEvent(event);
        return "OK " + var;
    }
}
