package simo.coinflip.models;

import org.bukkit.entity.Player;

public class CoinFlip {
    private int value;
    private final Player player;
    private Player target;

    public CoinFlip(Player player) {
        this.player = player;
    }

    public void setTarget(Player target) {
        this.target = target;
    }

    public Player getTarget() {
        return target;
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
