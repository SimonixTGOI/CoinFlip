package simo.coinflip.task;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;
import simo.coinflip.gui.ChoosingGUI;

public class UpdateChoosingGUI extends  BukkitRunnable {
    private final Player player;
    private final ChoosingGUI choosingGUI;
    private final Inventory inv;

    public UpdateChoosingGUI(Player player, ChoosingGUI choosingGUI, Inventory inv) {
        this.player = player;
        this.choosingGUI = choosingGUI;
        this.inv = inv;
    }

    @Override
    public void run() {
        if(!player.getOpenInventory().getTopInventory().equals(inv)) {
            cancel();
            return;
        }
        choosingGUI.updateChoosingGUI(inv);
    }
}
