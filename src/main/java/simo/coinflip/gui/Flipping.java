package simo.coinflip.gui;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import simo.coinflip.managers.EconomyManager;
import simo.coinflip.managers.FlipQueue;
import simo.coinflip.models.CoinFlip;
import simo.coinflip.task.CloseTask;
import simo.coinflip.task.FlipAnimation;

import java.util.Objects;
import java.util.Random;

public class Flipping {
    private final ChoosingGUI choosingGUI;
    private final Plugin plugin;
    private final EconomyManager economyManager;
    private final FlipQueue flipQueue;
    private Player player;
    private Player target;
    private CoinFlip coinFlip;


    public Flipping(ChoosingGUI choosingGUI, Plugin plugin, EconomyManager economyManager, FlipQueue flipQueue) {
        this.choosingGUI = choosingGUI;
        this.plugin = plugin;
        this.economyManager = economyManager;
        this.flipQueue = flipQueue;
    }


    public void openingFlipping(Player player, Player target, CoinFlip coinFlip) {
        this.player = player;
        this.target = target;
        this.coinFlip = coinFlip;
        flipQueue.finishQueue(target);
        Inventory flipping = Bukkit.createInventory(player, 45, Component.text("CoinFlipping..."));
        flipping.setItem(21, choosingGUI.createSkull(player));
        flipping.setItem(23, choosingGUI.createSkull(target));
        flipping.setItem(22, new ItemStack(Material.RED_STAINED_GLASS));
        player.openInventory(flipping);
        target.openInventory(flipping);
        new FlipAnimation(player, target, flipping, this)
                .runTaskTimer(plugin, 0L, 10L);
    }


    public void flipResult() {

        int value = coinFlip.getValue();
        Random random = new Random();
        if(random.nextBoolean()) {

            player.sendMessage("You Lost!");
            player.openInventory(redInventory(target, player));
            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_DEATH, 0.7f, 0.8f);
            economyManager.depositPlayer(target, value*2);
            target.sendMessage("You Won!");
            target.openInventory(greenInventory(target));
            target.playSound(target.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f);
        } else {
            economyManager.depositPlayer(player, value*2);
            player.sendMessage("You Won!");
            player.openInventory(greenInventory(player));
            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f);
            target.sendMessage("You Lost!");
            target.openInventory(redInventory(player, target));
            target.playSound(target.getLocation(), Sound.ENTITY_PLAYER_DEATH, 0.7f, 0.8f);

        }
        new CloseTask(player, target, 2)
                .runTaskTimer(plugin, 0L, 20L);
    }

    public Inventory redInventory(Player winner, Player loser) {
        Inventory inv =  Bukkit.createInventory(loser, 45, Component.text("You Lost"));
        inv.setItem(22, choosingGUI.createSkull(winner));
        for(int i = 0; i < inv.getSize(); i++) {
            if(inv.getItem(i) == null|| Objects.requireNonNull(inv.getItem(i)).getType() == Material.AIR) {
                inv.setItem(i, new ItemStack(Material.RED_STAINED_GLASS_PANE));
            }
        }
        return inv;
    }

    public Inventory greenInventory(Player winner) {
        Inventory inv =  Bukkit.createInventory(winner, 45, Component.text("You Won"));
        inv.setItem(22, choosingGUI.createSkull(winner));
        for(int i = 0; i < inv.getSize(); i++) {
            if(inv.getItem(i) == null|| Objects.requireNonNull(inv.getItem(i)).getType() == Material.AIR) {
                inv.setItem(i, new ItemStack(Material.GREEN_STAINED_GLASS_PANE));
            }
        }
        return inv;
    }

}
