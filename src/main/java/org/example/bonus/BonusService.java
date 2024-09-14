package org.example.bonus;

import io.smallrye.mutiny.Multi;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import org.example.bonus.models.BonusEvent;
import org.example.bonus.models.PlayerBonus;

@ApplicationScoped
public class BonusService {

    // Listen to the player-login-events Kafka topic
    @Incoming("player-login-events")
    public void processLoginEvent(String loginEvent) {
        System.out.println("Received login event: " + loginEvent);
        // Process login event and calculate bonus
    }

    // Produce events to the player-bonus-updates Kafka topic
    @Outgoing("player-bonus-updates")
    public Multi<String> sendBonusUpdates() {
        return Multi.createFrom().items("Bonus Update 1", "Bonus Update 2");
    }

    @Transactional
    public void updateBonus(String playerId) {
        PlayerBonus playerBonus = PlayerBonus.findByPlayerId(playerId);
        if (playerBonus == null) {
            playerBonus = new PlayerBonus();
            playerBonus.playerId = playerId;
            playerBonus.totalBonus = calculateBonus();
            playerBonus.persist();
        } else {
            playerBonus.totalBonus += calculateBonus();
            playerBonus.persist();
        }
    }

    private double calculateBonus() {
        return 10.0; // Fixed bonus for simplicity
    }

    public BonusEvent createBonusEvent(String playerId) {
        PlayerBonus playerBonus = PlayerBonus.findByPlayerId(playerId);
        return new BonusEvent(playerId, playerBonus.totalBonus);
    }
}
