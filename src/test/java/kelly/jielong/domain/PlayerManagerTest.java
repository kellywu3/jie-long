package kelly.jielong.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
public class PlayerManagerTest {
    @Autowired
    private PlayerManager playerManager;

    @Test
    public void testSanity() throws IOException {
        Assertions.assertNotNull(playerManager);
        Player p = new Player(1, "person");
        playerManager.savePlayerToFile(p);
    }
}
