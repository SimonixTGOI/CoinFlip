package simo.coinflip.task;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class CloseTask extends BukkitRunnable {
    private final Player player;
    private final Player target;
    private final int wait;
    private int count = 0;

    public CloseTask(Player player, Player target, int wait) {
        this.player = player;
        this.target = target;
        this.wait = wait;
    }
    @Override
    public void run() {
        if(count >= wait) {
            player.closeInventory();
            target.closeInventory();
            this.cancel();
        }
        count++;
    }
}
