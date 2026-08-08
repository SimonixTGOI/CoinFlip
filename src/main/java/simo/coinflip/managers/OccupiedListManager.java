package simo.coinflip.managers;

import org.bukkit.entity.Player;
import simo.coinflip.models.CoinFlipModel;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class OccupiedListManager {
    private final Map<UUID, CoinFlipModel> map = new HashMap<>();

    public void setOccupied(Player player, CoinFlipModel coinFlipModel) {
        map.put(player.getUniqueId(), coinFlipModel);
    }

    public void removeOccupied(Player player) {
        map.remove(player.getUniqueId());
    }

    public CoinFlipModel getCoinFlipFromPlayer(Player player) {
        return map.get(player.getUniqueId());
    }

    public boolean isOccupied(Player player) {
        return map.containsKey(player.getUniqueId());
    }

    public Map<UUID, CoinFlipModel> getMap() {
        return map;
    }

}
