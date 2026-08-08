package simo.coinflip.managers;

import org.bukkit.entity.Player;
import simo.coinflip.models.CoinFlip;

import java.util.*;

public class CreatingQueueManager {
    private final Map<UUID, CoinFlip> creatingCoinFlips = new HashMap<>();

    public void addCoinFlip(CoinFlip coinFlip) {
        creatingCoinFlips.put(coinFlip.getPlayer().getUniqueId(), coinFlip);
    }
    public void removeCoinFlip(CoinFlip coinFlip) {
        creatingCoinFlips.remove(coinFlip.getPlayer().getUniqueId());
    }

    public Map<UUID, CoinFlip> getCoinFlips() {
        return creatingCoinFlips;
    }

    public CoinFlip getCoinFlip(Player player) {
        if(creatingCoinFlips.containsKey(player.getUniqueId())) {
            return creatingCoinFlips.get(player.getUniqueId());
        }
        return null;
    }

    public boolean existsCoinFlip(Player player) {
        return creatingCoinFlips.containsKey(player.getUniqueId());
    }
}
