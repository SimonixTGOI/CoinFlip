package simo.coinflip.managers;

import org.bukkit.entity.Player;
import simo.coinflip.models.CoinFlip;

import java.util.*;

public class CoinFlipManager {
    private final List<CoinFlip> creatingCoinFlips = new ArrayList<>();

    public void addCoinFlip(CoinFlip coinFlip) {
        creatingCoinFlips.add(coinFlip);
    }
    public void removeCoinFlip(CoinFlip coinFlip) {
        creatingCoinFlips.remove(coinFlip);
    }

    public List<CoinFlip> getCoinFlips() {
        return creatingCoinFlips;
    }

    public CoinFlip getCoinFlip(Player player) {
        for (CoinFlip coinFlip : creatingCoinFlips) {
            if (coinFlip.getPlayer().equals(player)) {
                return coinFlip;
            }
        }
        return null;
    }

    public void setCoinFlip(Player player, CoinFlip coinFlip) {
        creatingCoinFlips.set(creatingCoinFlips.indexOf(coinFlip), coinFlip);
    }

    public boolean existsCoinFlip(Player player) {
        for (CoinFlip coinFlip : creatingCoinFlips) {
            if (coinFlip.getPlayer().equals(player)) {
                return true;
            }
        }
        return false;
    }
}
