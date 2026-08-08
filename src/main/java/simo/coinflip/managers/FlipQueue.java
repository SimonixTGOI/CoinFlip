package simo.coinflip.managers;

import org.bukkit.entity.Player;
import simo.coinflip.models.CoinFlipModel;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class FlipQueue {
    private final Map<UUID, CoinFlipModel> map = new HashMap<>();
    private final EconomyManager economyManager;
    public FlipQueue(EconomyManager economyManager) {
        this.economyManager = economyManager;
    }

    public boolean joinQueue(CoinFlipModel coinFlipModel) {
        Player player = coinFlipModel.getPlayer();
        if(!economyManager.withdrawMoney(player, coinFlipModel.getValue())) {
            return false;
        }
        map.put(player.getUniqueId(), coinFlipModel);
        return true;
    }

    public boolean leaveQueue(Player player) {
        if(!map.containsKey(player.getUniqueId())) {
            return false;
        }
        CoinFlipModel coinFlipModel = map.get(player.getUniqueId());
        if(!economyManager.depositPlayer(player, coinFlipModel.getValue())) {
            return false;
        }
        map.remove(player.getUniqueId());
        return true;
    }

    public void finishQueue(Player player) {
        map.remove(player.getUniqueId());
    }

    public boolean isInQueue(Player player) {
        return map.containsKey(player.getUniqueId());
    }

    public Map<UUID, CoinFlipModel> getQueue() {
        return map;
    }

    public CoinFlipModel getCoinFlip(Player player) {
        return map.get(player.getUniqueId());
    }
}
