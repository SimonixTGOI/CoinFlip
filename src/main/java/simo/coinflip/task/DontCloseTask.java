package simo.coinflip.task;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;

public class DontCloseTask extends BukkitRunnable {
    private final Inventory inventory;
    private final Player player;
    private final int duration;
    private int count = 0;

    public DontCloseTask(Player player, Inventory inventory, int duration_in_seconds) {
        this.inventory = inventory;
        this.player = player;
        this.duration = duration_in_seconds*20;
    }

    @Override
    public void run() {
        if(count >= duration) {
            cancel();
            return;
        }
        if(!player.getOpenInventory().getTopInventory().equals(inventory)) {
            player.openInventory(inventory);
        }
        count++;
    }
}
