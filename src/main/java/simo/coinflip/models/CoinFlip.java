package simo.coinflip.models;

import org.bukkit.entity.Player;

public class CoinFlip {
    private int value;
    private final Player player;

    public CoinFlip(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public int getValue() {
        return this.value;
    }

    public void addValue() {
        this.value+=100;
    }

    public void removeValue() {
        if(value == 0) {
            return;
        }
        this.value-=100;
    }

}
