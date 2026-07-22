package simo.coinflip.managers;

import org.bukkit.entity.Player;
import simo.coinflip.models.CoinFlip;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class FlipQueue {
    private final Map<UUID, CoinFlip> map = new HashMap<>();
    private final EconomyManager economyManager;
    public FlipQueue(EconomyManager economyManager) {
        this.economyManager = economyManager;
    }

    public boolean joinQueue(CoinFlip coinFlip) {
        Player player = coinFlip.getPlayer();
        if(!economyManager.withdrawMoney(player, coinFlip.getValue())) {
            return false;
        }
        map.put(player.getUniqueId(), coinFlip);
        return true;
    }

    public boolean leaveQueue(Player player) {
        if(!map.containsKey(player.getUniqueId())) {
            return false;
        }
        CoinFlip coinFlip = map.get(player.getUniqueId());
        if(!economyManager.depositPlayer(player, coinFlip.getValue())) {
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

    public Map<UUID, CoinFlip> getQueue() {
        return map;
    }

    public CoinFlip getCoinFlip(Player player) {
        return map.get(player.getUniqueId());
    }
}
