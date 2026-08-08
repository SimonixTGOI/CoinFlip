package simo.coinflip.task;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import simo.coinflip.gui.Flipping;

import java.util.Objects;

public class FlipAnimation extends BukkitRunnable {
    private final Player player;
    private final Player target;
    private final Inventory inventory;
    private final Flipping flipping;
    private int status;
    private int count = 0;
    private final int duration;

    public FlipAnimation(Player player, Player target, Inventory inventory, Flipping flipping, int duration_in_seconds) {
        this.player = player;
        this.target = target;
        this.inventory = inventory;
        this.flipping = flipping;
        this.duration = duration_in_seconds*2;

    }


    @Override
    public void run() {

        if(count >= duration) {
            flipping.flipResult();
            this.cancel();
        } else {
            setInventory();
            count++;
        }

    }

    public void redInventory() {
        for(int i = 0; i < inventory.getSize(); i++) {
            if(inventory.getItem(i) == null|| Objects.requireNonNull(inventory.getItem(i)).getType() == Material.AIR || Objects.requireNonNull(inventory.getItem(i)).getType() == Material.GREEN_STAINED_GLASS_PANE) {
                inventory.setItem(i, new ItemStack(Material.RED_STAINED_GLASS_PANE));
            }
        }

    }

    public void greenInventory() {
        for(int i = 0; i < inventory.getSize(); i++) {
            if(inventory.getItem(i) == null|| Objects.requireNonNull(inventory.getItem(i)).getType() == Material.AIR || Objects.requireNonNull(inventory.getItem(i)).getType() == Material.RED_STAINED_GLASS_PANE) {
                inventory.setItem(i, new ItemStack(Material.GREEN_STAINED_GLASS_PANE));
            }
        }
    }

    public void setInventory() {
        if(status == 0) {
            greenInventory();
            status = 1;
            player.playSound(player, Sound.BLOCK_NOTE_BLOCK_BELL, 1.0f, 1.0f);
            target.playSound(target, Sound.BLOCK_NOTE_BLOCK_BELL, 1.0f, 1.0f);
        } else {
            redInventory();
            status = 0;
            player.playSound(player, Sound.BLOCK_NOTE_BLOCK_BIT, 1.0f, 1.0f);
            target.playSound(target, Sound.BLOCK_NOTE_BLOCK_BIT, 1.0f, 1.0f);
        }
    }
}
