package simo.coinflip.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;
import simo.coinflip.managers.EconomyManager;
import simo.coinflip.managers.FlipQueue;
import simo.coinflip.managers.OccupiedListManager;
import simo.coinflip.models.CoinFlipModel;

public class QuitListener implements Listener {
    private final OccupiedListManager occupiedListManager;
    private final FlipQueue flipQueue;
    private final Plugin plugin;
    private final EconomyManager economyManager;

    public QuitListener(OccupiedListManager occupiedListManager, FlipQueue flipQueue, Plugin plugin, EconomyManager economyManager) {
        this.occupiedListManager = occupiedListManager;
        this.flipQueue = flipQueue;
        this.plugin = plugin;
        this.economyManager = economyManager;
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if(flipQueue.isInQueue(player)) {
            if(!flipQueue.leaveQueue(player)) {
                plugin.getLogger().warning("An error occurred while trying to leave the queue. (" + player.getName() + ")");
            }
            return;
        }
        if(occupiedListManager.isOccupied(player)) {
            CoinFlipModel coinFlipModel = occupiedListManager.getCoinFlipFromPlayer(player);
            Player target;
            if(coinFlipModel.getTarget() == player) {
                target = coinFlipModel.getPlayer();
            } else {
                target = coinFlipModel.getTarget();
            }
            if(!economyManager.depositPlayer(player, coinFlipModel.getValue())) {
                plugin.getLogger().warning("An error occurred while cancelling the coin flip. (" + player.getName() + ")");
            }
            if(!economyManager.depositPlayer(target, coinFlipModel.getValue())) {
                plugin.getLogger().warning("An error occurred while cancelling the coin flip. (" + target.getName() + ")");
            }
            coinFlipModel.getTask1().cancel();
            coinFlipModel.getTask2().cancel();
            coinFlipModel.getFlipAnimation().cancel();
            occupiedListManager.removeOccupied(player);
            occupiedListManager.removeOccupied(target);
            return;
        }
    }
}
