package simo.coinflip;

import org.bukkit.plugin.java.JavaPlugin;
import simo.coinflip.commands.CoinFlipCommand;
import simo.coinflip.commands.LeaveQueueCommand;
import simo.coinflip.gui.ChoosingGUI;
import simo.coinflip.gui.CreateFlipGUI;
import simo.coinflip.listeners.ClickListener;
import simo.coinflip.managers.*;

import java.util.Objects;

public final class Coinflip extends JavaPlugin {
    private final EconomyManager economyManager = new EconomyManager(this);
    private final CreatingQueueManager creatingQueueManager = new CreatingQueueManager();
    private final FlipQueue flipQueue = new FlipQueue(economyManager);
    private final ChoosingGUI choosingGUI = new ChoosingGUI(flipQueue, this);
    private final CreateFlipGUI createFlipGUI = new CreateFlipGUI(creatingQueueManager);
    private final OccupiedListManager occupiedListManager = new OccupiedListManager();
    private final ClickListener clickListener = new ClickListener(choosingGUI, createFlipGUI, creatingQueueManager, flipQueue, economyManager, this, occupiedListManager);

    @Override
    public void onEnable() {
        // Plugin startup logic

        getLogger().info("Plugin is currently loading...");



        economyManager.setup();
        if(!economyManager.isEnabled()) {
            return;
        }


        Objects.requireNonNull(getCommand("coinflip")).setExecutor(new CoinFlipCommand(choosingGUI));
        Objects.requireNonNull(getCommand("leavequeue")).setExecutor(new LeaveQueueCommand(flipQueue));

        getServer().getPluginManager().registerEvents(clickListener, this);
        getLogger().info("Plugin enabled!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("Plugin disabled!");
        DisablingManager disablingManager = new DisablingManager(economyManager, occupiedListManager, flipQueue, this);
        disablingManager.refundOccupied();
        disablingManager.refundQueue();

    }
}
