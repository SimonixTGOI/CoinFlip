package simo.coinflip.listeners;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import simo.coinflip.gui.ChoosingGUI;
import simo.coinflip.gui.CreateFlipGUI;
import simo.coinflip.gui.Flipping;
import simo.coinflip.managers.CreatingQueueManager;
import simo.coinflip.managers.EconomyManager;
import simo.coinflip.managers.FlipQueue;
import simo.coinflip.models.CoinFlip;

public class ClickListener implements Listener {
    private final ChoosingGUI choosingGUI;
    private final CreateFlipGUI createFlipGUI;
    private final CreatingQueueManager creatingQueueManager;
    private final FlipQueue flipQueue;
    private final EconomyManager economyManager;
    private final Plugin plugin;

    public ClickListener(ChoosingGUI choosingGUI, CreateFlipGUI createFlipGUI, CreatingQueueManager creatingQueueManager, FlipQueue flipQueue, EconomyManager economyManager, Plugin plugin) {
        this.choosingGUI = choosingGUI;
        this.createFlipGUI = createFlipGUI;
        this.flipQueue = flipQueue;
        this.creatingQueueManager = creatingQueueManager;
        this.economyManager = economyManager;
        this.plugin = plugin;
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Component titleComponent = event.getView().title();
        String title = PlainTextComponentSerializer.plainText().serialize(titleComponent);

        ItemStack item = event.getCurrentItem();
        Component itemNameComponent;
        String itemName;



        switch (title) {
            case "CoinFlip":
                event.setCancelled(true);

                if(item == null || item.getType() == Material.AIR) return;
                itemNameComponent = item.getItemMeta().customName();
                if(itemNameComponent == null) return;
                itemName = PlainTextComponentSerializer.plainText().serialize(itemNameComponent);

                if(itemName.equalsIgnoreCase("Create Flip")) {
                    if(flipQueue.isInQueue(player)) {
                        player.sendMessage("You already have a CoinFlip!");
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
                            if(target.getUniqueId().equals(player.getUniqueId())) {
                                player.sendMessage("You can't accept your own CoinFlip!");
                                return;
                            }
                            int value = flipQueue.getCoinFlip(target).getValue();
                            if(!economyManager.hasEnoughMoney(player, value)) {
                                player.sendMessage("You do not have enough money!");
                                return;
                            }
                            if(!economyManager.withdrawMoney(player, value)) {
                                player.sendMessage("An error occurred while accepting the CoinFlip.");
                                plugin.getLogger().warning("Failed to withdraw " + value + " from " + player.getName());
                                return;
                            }
                            if(flipQueue.isInQueue(player)) {
                                player.sendMessage("To accept a CoinFlip you need to exit the queue.");
                                return;
                            }

                            Flipping flipping = new Flipping(choosingGUI, plugin,economyManager, flipQueue);
                            flipping.openingFlipping(player, target, flipQueue.getCoinFlip(target));
                        }
                    }

                }


                return;

            case "Create Flip":
                event.setCancelled(true);

                if(item == null || item.getType() == Material.AIR) return;
                itemNameComponent = item.getItemMeta().customName();
                if(itemNameComponent == null) return;
                itemName = PlainTextComponentSerializer.plainText().serialize(itemNameComponent);

                if(!creatingQueueManager.existsCoinFlip(player)) {
                    CoinFlip coinFlip = new CoinFlip(player);
                    creatingQueueManager.addCoinFlip(coinFlip);
                }

                if(itemName.equalsIgnoreCase("Create Flip")) {
                    CoinFlip coinFlip = creatingQueueManager.getCoinFlip(player);
                    if(coinFlip.getValue() == 0) {
                        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
                        player.sendMessage("The CoinFlip value must be higher than zero!");
                        return;
                    }
                    if(!economyManager.hasEnoughMoney(player, coinFlip.getValue())) {
                        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
                        player.sendMessage("Not enough money!");
                        return;
                    }


                    if(!flipQueue.joinQueue(coinFlip)) {
                        player.sendMessage("An error occurred while creating the CoinFlip.");
                        plugin.getLogger().warning("Failed to withdraw " + coinFlip.getValue() + " from " + player.getName());
                        return;
                    }

                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 1f, 1f);

                    creatingQueueManager.removeCoinFlip(coinFlip);

                    player.sendMessage("CoinFlip created!");

                    player.closeInventory();
                }
                if(itemName.equalsIgnoreCase("Add 100")) {
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1f, 1.2f);
                    CoinFlip coinFlip = creatingQueueManager.getCoinFlip(player);
                    coinFlip.addValue(100);
                    createFlipGUI.updateGUI(event.getInventory(), player);
                }
                if(itemName.equalsIgnoreCase("Remove 100")) {
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1f, 0.7f);
                    CoinFlip coinFlip = creatingQueueManager.getCoinFlip(player);
                    coinFlip.removeValue(100);
                    createFlipGUI.updateGUI(event.getInventory(), player);
                }
                if(itemName.equalsIgnoreCase("Back")) {
                    CoinFlip coinFlip = creatingQueueManager.getCoinFlip(player);
                    creatingQueueManager.removeCoinFlip(coinFlip);

                    choosingGUI.openGUI(player);

                }
                return;

            case "You Lost", "You Won", "CoinFlipping...":
                event.setCancelled(true);

        }

    }
}
