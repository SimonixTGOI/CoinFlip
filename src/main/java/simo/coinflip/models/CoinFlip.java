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

    public void addValue(int amount) {
        this.value+=amount;
    }

    public void removeValue(int amount) {
        if(value == 0) {
            return;
        }
        if(this.value < amount) {
            return;
        }
        this.value-=amount;
    }

}
