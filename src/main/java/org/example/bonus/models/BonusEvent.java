package org.example.bonus.models;

public class BonusEvent {
    public String playerId;
    public double bonusAmount;

    public BonusEvent() {}

    public BonusEvent(String playerId, double bonusAmount) {
        this.playerId = playerId;
        this.bonusAmount = bonusAmount;
    }
}
