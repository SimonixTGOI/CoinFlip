package simo.coinflip;

import org.bukkit.plugin.java.JavaPlugin;
import simo.coinflip.commands.CoinFlipCommand;
import simo.coinflip.commands.LeaveQueueCommand;
import simo.coinflip.gui.ChoosingGUI;
import simo.coinflip.gui.CreateFlipGUI;
import simo.coinflip.listeners.ClickListener;
import simo.coinflip.managers.CoinFlipManager;
import simo.coinflip.managers.EconomyManager;
import simo.coinflip.managers.FlipQueue;

import java.util.Objects;

public final class Coinflip extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic

        getLogger().info("Plugin is currently loading...");

        EconomyManager economyManager = new EconomyManager(this);

        economyManager.setup();
        if(!economyManager.isEnabled()) {
            return;
        }

        CoinFlipManager coinFlipManager = new CoinFlipManager();
        FlipQueue flipQueue = new FlipQueue(economyManager);
        ChoosingGUI choosingGUI = new ChoosingGUI(flipQueue, this);
        CreateFlipGUI createFlipGUI = new CreateFlipGUI(coinFlipManager);

        ClickListener clickListener = new ClickListener(choosingGUI, createFlipGUI, coinFlipManager, flipQueue, economyManager, this);


        Objects.requireNonNull(getCommand("coinflip")).setExecutor(new CoinFlipCommand(choosingGUI));
        Objects.requireNonNull(getCommand("leavequeue")).setExecutor(new LeaveQueueCommand(flipQueue));

        getServer().getPluginManager().registerEvents(clickListener, this);
        getLogger().info("Plugin enabled!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("Plugin disabled!");
    }
}
