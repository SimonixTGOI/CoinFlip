package simo.coinflip.task;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import simo.coinflip.gui.ChoosingGUI;
import simo.coinflip.gui.Flipping;

import java.util.Objects;

public class FlipAnimation extends BukkitRunnable {
    private final Player player;
    private final Player target;
    private final Inventory inventory;
    private final Flipping flipping;
    private int status;
    private int count = 0;

    public FlipAnimation(Player player, Player target, Inventory inventory, Flipping flipping) {
        this.player = player;
        this.target = target;
        this.inventory = inventory;
        this.flipping = flipping;
    }


    @Override
    public void run() {

        if(count > 5) {
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
