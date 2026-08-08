package simo.coinflip.task;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;

public class CloseTask extends BukkitRunnable {
    private final Player player;
    private final Player target;
    private final int duration;
    private Inventory inventory1 = null;
    private Inventory inventory2 = null;
    private int count = 0;

    public CloseTask(Player player, Player target, int duration) {
        this.player = player;
        this.target = target;
        this.duration = duration;
    }
    @Override
    public void run() {
        if(inventory1 == null) {
            inventory1 = player.getOpenInventory().getTopInventory();
        }
        if(inventory2 == null) {
            inventory2 = target.getOpenInventory().getTopInventory();
        }
        if(count >= duration) {
            if (player.getOpenInventory().getTopInventory().equals(inventory1)) {
                player.closeInventory();
            }
            if (target.getOpenInventory().getTopInventory().equals(inventory2)) {
                target.closeInventory();
            }
            this.cancel();
        }
        count++;
    }
}
