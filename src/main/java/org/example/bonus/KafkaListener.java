package org.example.bonus;

import jakarta.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Incoming;

@ApplicationScoped
public class KafkaListener {

    @Inject
    BonusService bonusService;

    @Incoming("login-events")
    public void onLoginEvent(String playerId) {
        bonusService.updateBonus(playerId);
    }
}
