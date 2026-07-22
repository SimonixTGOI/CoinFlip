package simo.coinflip.managers;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import simo.coinflip.Coinflip;

public class EconomyManager {
    private Economy economy;
    private final Coinflip plugin;
    private Boolean enabled;

    public EconomyManager(Coinflip plugin) {
        this.plugin = plugin;
    }


    public void setup() {
        Plugin vaultPlugin = Bukkit.getPluginManager().getPlugin("Vault");
        if(vaultPlugin == null) {
            plugin.getLogger().warning("[Coinflip] Vault not found!");
            enabled = false;
            return;
        }

        RegisteredServiceProvider<Economy> economyProvider = vaultPlugin.getServer().getServicesManager().getRegistration(Economy.class);
        if(economyProvider == null) {
            plugin.getLogger().warning("[Coinflip] Economy not found!");
            enabled = false;
            return;
        }

        plugin.getLogger().info("[Coinflip] EconomyManager has been initialized!");
        economy = economyProvider.getProvider();
        enabled = true;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public boolean hasEnoughMoney(Player player, double amount) {
        return economy.has(player, amount);
    }

    public boolean withdrawMoney(Player player, double amount) {
        return economy.withdrawPlayer(player, amount).transactionSuccess();
    }

    public double getBalance(Player player) {
        return economy.getBalance(player);
    }

    public boolean depositPlayer(Player player, double amount) {
        return economy.depositPlayer(player, amount).transactionSuccess();
    }
}
