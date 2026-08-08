package simo.coinflip.managers;

import org.bukkit.entity.Player;
import simo.coinflip.models.CoinFlipModel;

import java.util.*;

public class CreatingQueueManager {
    private final Map<UUID, CoinFlipModel> creatingCoinFlips = new HashMap<>();

    public void addCoinFlip(CoinFlipModel coinFlipModel) {
        creatingCoinFlips.put(coinFlipModel.getPlayer().getUniqueId(), coinFlipModel);
    }
    public void removeCoinFlip(CoinFlipModel coinFlipModel) {
        creatingCoinFlips.remove(coinFlipModel.getPlayer().getUniqueId());
    }

    public Map<UUID, CoinFlipModel> getCoinFlips() {
        return creatingCoinFlips;
    }

    public CoinFlipModel getCoinFlip(Player player) {
        if(creatingCoinFlips.containsKey(player.getUniqueId())) {
            return creatingCoinFlips.get(player.getUniqueId());
        }
        return null;
    }

    public boolean existsCoinFlip(Player player) {
        return creatingCoinFlips.containsKey(player.getUniqueId());
    }
}
