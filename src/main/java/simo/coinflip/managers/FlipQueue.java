package simo.coinflip.managers;

import org.bukkit.entity.Player;
import simo.coinflip.contructors.CoinFlip;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class FlipQueue {
    private final Map<UUID, CoinFlip> map = new HashMap<>();

    public void joinQueue(CoinFlip coinFlip) {
        Player player = coinFlip.getPlayer();
        map.put(player.getUniqueId(), coinFlip);
    }

    public boolean leaveQueue(Player player) {
        if(!map.containsKey(player.getUniqueId())) {
            return false;
        }
        map.remove(player.getUniqueId());
        return true;
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
