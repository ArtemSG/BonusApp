package org.example.bonus;

import io.quarkus.test.junit.QuarkusTest;
import org.example.bonus.models.PlayerBonus;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class BonusServiceTest {

    @Inject
    BonusService bonusService;

    @Test
    public void testBonusUpdate() {
        String playerId = "player123";
        bonusService.updateBonus(playerId);

        assertNotNull(PlayerBonus.findByPlayerId(playerId));
        assertEquals(10.0, PlayerBonus.findByPlayerId(playerId).totalBonus);
    }
}
