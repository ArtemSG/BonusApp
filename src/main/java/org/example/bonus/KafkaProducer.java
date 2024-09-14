package org.example.bonus;

import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import org.example.bonus.models.BonusEvent;
import org.example.bonus.models.PlayerBonus;

import javax.inject.Inject;

@ApplicationScoped
public class KafkaProducer {

    @Inject
    BonusService bonusService;

    @Outgoing("bonus-updates")
    public BonusEvent produceBonusEvent(String playerId) {
        return bonusService.createBonusEvent(playerId);
    }

    @Outgoing("bonus-updates")
    public String produceBonusUpdate(String playerId) {
        PlayerBonus playerBonus = PlayerBonus.findByPlayerId(playerId);
        return "Player: " + playerId + " Bonus: " + playerBonus.totalBonus;
    }
}
