package org.example.bonus.models;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;

@Entity
public class PlayerBonus extends PanacheEntity {
    public String playerId;
    public double totalBonus;

    public static PlayerBonus findByPlayerId(String playerId) {
        return find("playerId", playerId).firstResult();
    }
}