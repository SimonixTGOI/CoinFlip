package simo.coinflip.managers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import simo.coinflip.models.CoinFlipModel;

import java.util.Map;
import java.util.UUID;

public class DisablingManager {
    private final EconomyManager economyManager;
    private final OccupiedListManager occupiedListManager;
    private final FlipQueue flipQueue;
    private final Plugin plugin;


    public DisablingManager(EconomyManager economyManager, OccupiedListManager occupiedListManager, FlipQueue flipQueue, Plugin plugin) {
        this.economyManager = economyManager;
        this.occupiedListManager = occupiedListManager;
        this.flipQueue = flipQueue;
        this.plugin = plugin;
    }

    public void refundOccupied() {
        Map<UUID, CoinFlipModel> map = occupiedListManager.getMap();
        for (Map.Entry<UUID, CoinFlipModel> entry : map.entrySet()) {
            UUID uuid = entry.getKey();
            Player player = Bukkit.getPlayer(uuid);
            CoinFlipModel coinFlipModel = entry.getValue();
            int value = coinFlipModel.getValue();

            if(!economyManager.depositPlayer(player, value)) {
                plugin.getLogger().warning("An error occurred while refunding " + player + " with " + value + " money (occupied)");
            }

        }
    }

    public void refundQueue() {
        Map<UUID, CoinFlipModel> map = flipQueue.getQueue();
        for (Map.Entry<UUID, CoinFlipModel> entry : map.entrySet()) {
            UUID uuid = entry.getKey();
            Player player = Bukkit.getPlayer(uuid);
            CoinFlipModel coinFlipModel = entry.getValue();
            int value = coinFlipModel.getValue();
            if(!economyManager.depositPlayer(player, value)) {
                plugin.getLogger().warning("An error occurred while refunding " + player + " with " + value + " money (queue)");
            }
        }
    }
}
