package simo.coinflip.models;

import org.bukkit.entity.Player;
import simo.coinflip.task.DontCloseTask;
import simo.coinflip.task.FlipAnimation;

public class CoinFlipModel {
    private int value;
    private final Player player;
    private Player target;
    private DontCloseTask task1;
    private DontCloseTask task2;
    private FlipAnimation flipAnimation;

    public CoinFlipModel(Player player) {
        this.player = player;
    }

    public void setTask1(DontCloseTask task1) {
        this.task1 = task1;
    }

    public void setTask2(DontCloseTask task2) {
        this.task2 = task2;
    }

    public void setFlipAnimation(FlipAnimation flipAnimation) {
        this.flipAnimation = flipAnimation;
    }

    public DontCloseTask getTask1() {
        return task1;
    }

    public DontCloseTask getTask2() {
        return task2;
    }

    public FlipAnimation getFlipAnimation() {
        return flipAnimation;
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
