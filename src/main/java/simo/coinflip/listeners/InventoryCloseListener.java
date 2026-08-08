package simo.coinflip.listeners;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import simo.coinflip.managers.CreatingQueueManager;

public class InventoryCloseListener implements Listener {
    private final CreatingQueueManager creatingQueueManager;

    public InventoryCloseListener(CreatingQueueManager creatingQueueManager) {
        this.creatingQueueManager = creatingQueueManager;
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        Component titleComponent = event.getView().title();
        String title = PlainTextComponentSerializer.plainText().serialize(titleComponent);
        Player player =  (Player) event.getPlayer();

        if(title.equals("Create Flip")) {
            if(creatingQueueManager.existsCoinFlip(player)) {
                creatingQueueManager.removeCoinFlip(creatingQueueManager.getCoinFlip(player));
            }
        }
    }
}
