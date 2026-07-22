package simo.coinflip.listeners;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import simo.coinflip.contructors.CoinFlip;
import simo.coinflip.gui.ChoosingGUI;
import simo.coinflip.gui.CreateFlipGUI;
import simo.coinflip.managers.CoinFlipManager;
import simo.coinflip.managers.EconomyManager;
import simo.coinflip.managers.FlipQueue;
import simo.coinflip.task.FlipAnimation;

import java.util.Random;

public class ClickListener implements Listener {
    private final ChoosingGUI choosingGUI;
    private final CreateFlipGUI createFlipGUI;
    private final CoinFlipManager coinFlipManager;
    private final FlipQueue flipQueue;
    private final Plugin plugin;
    private final EconomyManager economyManager;

    public ClickListener(ChoosingGUI choosingGUI, CreateFlipGUI createFlipGUI, CoinFlipManager coinFlipManager, FlipQueue flipQueue, Plugin plugin, EconomyManager economyManager) {
        this.choosingGUI = choosingGUI;
        this.createFlipGUI = createFlipGUI;
        this.flipQueue = flipQueue;
        this.coinFlipManager = coinFlipManager;
        this.plugin = plugin;
        this.economyManager = economyManager;
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Component titleComponent = event.getView().title();
        String title = PlainTextComponentSerializer.plainText().serialize(titleComponent);
        ItemStack item = event.getCurrentItem();
        if(item == null || item.getType() == Material.AIR) return;
        Component itemNameComponent = item.getItemMeta().customName();
        if(itemNameComponent == null) return;
        String itemName = PlainTextComponentSerializer.plainText().serialize(itemNameComponent);


        switch (title) {
            case "CoinFlip":
                event.setCancelled(true);
                if(itemName.equalsIgnoreCase("Create Flip")) {
                    if(flipQueue.isInQueue(player)) {
                        player.closeInventory();
                        player.sendMessage("You already have a coin flip!");
                        return;
                    }
                    createFlipGUI.openGUI(player);

                }
                if(itemName.equalsIgnoreCase("Next Page")) {
                    player.sendMessage("Next Page");
                }
                if(itemName.equalsIgnoreCase("Previous Page")) {
                    player.sendMessage("Previous Page");
                }

                String playerName = item.getItemMeta().getPersistentDataContainer()
                        .get(choosingGUI.coinflipkey, PersistentDataType.STRING);
                if(playerName != null) {
                    Player target = Bukkit.getPlayer(playerName);
                    if(target != null) {
                        if(flipQueue.getQueue().containsKey(target.getUniqueId())) {
                            player.sendMessage("CoinFlip started!");
                            target.sendMessage("CoinFlip started!");

                            Inventory flipping = Bukkit.createInventory(player, 45, Component.text("CoinFlipping..."));

                            player.openInventory(flipping);

                            new FlipAnimation(choosingGUI, player, flipping)
                                    .runTaskTimer(plugin, 10L, 10L);

                            new FlipAnimation(choosingGUI, target, flipping)
                                    .runTaskTimer(plugin, 10L, 10L);

                            Random random = new Random();
                            if(random.nextBoolean()) {
                                economyManager.depositPlayer(target, flipQueue.getCoinFlip(target).getValue());
                                player.sendMessage("You Lost!");
                                target.sendMessage("You Won!");
                            } else {
                                economyManager.depositPlayer(player, flipQueue.getCoinFlip(target).getValue());
                                player.sendMessage("You Won!");
                                target.sendMessage("You Lost!");
                            }
                            flipQueue.leaveQueue(target);
                        }
                    }

                }


                return;

            case "Create Flip":
                event.setCancelled(true);
                if(!coinFlipManager.existsCoinFlip(player)) {
                    CoinFlip coinFlip = new CoinFlip(player);
                    coinFlipManager.addCoinFlip(coinFlip);
                }

                if(itemName.equalsIgnoreCase("Create Flip")) {
                    CoinFlip coinFlip = coinFlipManager.getCoinFlip(player);
                    if(!economyManager.hasEnoughMoney(player, coinFlip.getValue())) {
                        player.sendMessage("Not enough money!");
                        return;
                    }
                    flipQueue.joinQueue(coinFlip);
                    coinFlipManager.removeCoinFlip(coinFlip);

                    player.closeInventory();
                }
                if(itemName.equalsIgnoreCase("Add 100")) {
                    CoinFlip coinFlip = coinFlipManager.getCoinFlip(player);
                    coinFlip.addValue();
                    createFlipGUI.updateGUI(event.getInventory(), player);
                }
                if(itemName.equalsIgnoreCase("Remove 100")) {
                    CoinFlip coinFlip = coinFlipManager.getCoinFlip(player);
                    coinFlip.removeValue();
                    createFlipGUI.updateGUI(event.getInventory(), player);
                }
                if(itemName.equalsIgnoreCase("Back")) {
                    CoinFlip coinFlip = coinFlipManager.getCoinFlip(player);
                    coinFlipManager.removeCoinFlip(coinFlip);

                    choosingGUI.openGUI(player);

                }
                return;
            default:
                player.sendMessage("An error as occurred.");
                break;
        }

    }
}
