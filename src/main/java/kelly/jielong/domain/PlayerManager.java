package kelly.jielong.domain;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

@Service
public class PlayerManager {
    private static final Logger logger = LoggerFactory.getLogger(PlayerManager.class);

    @Autowired
    private JieLongGame game;

    @Value("${data.save.dir}")
    private String dataRootDir;

    public Integer createPlayer(Integer playerId, String name) {
        Player p = new Player(playerId, name);

        return playerId;
    }

    public void savePlayerToFile(Player p) throws IOException {
        logger.info("Player: {} - {}", p, dataRootDir);
        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());

        File parent = Paths.get(dataRootDir).toFile();
        parent.mkdirs();


        File dest = new File(parent, "player-" + p.getPlayerId());
        logger.info("File name: {}", dest);
        writer.writeValue(dest, p);
    }
}
