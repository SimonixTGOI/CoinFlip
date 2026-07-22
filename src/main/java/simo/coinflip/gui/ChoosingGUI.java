package simo.coinflip.gui;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import simo.coinflip.models.CoinFlip;
import simo.coinflip.managers.FlipQueue;

import java.util.ArrayList;
import java.util.List;

public class ChoosingGUI {
    private final FlipQueue flipQueue;
    private final Plugin plugin;
    public final NamespacedKey coinflipkey;
    private Inventory inv;

    public ChoosingGUI(FlipQueue flipQueue,  Plugin plugin) {
        this.flipQueue = flipQueue;
        this.plugin = plugin;
        coinflipkey = new NamespacedKey(plugin, "coinflip");
    }

    public void openGUI(Player player) {
        inv = Bukkit.createInventory(player, 27, Component.text("CoinFlip"));

        ItemStack nextPage = new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1);
        ItemStack previousPage = new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1);
        ItemStack createFlip = new ItemStack(Material.GREEN_STAINED_GLASS_PANE, 1);

        ItemMeta nextPageMeta = nextPage.getItemMeta();
        ItemMeta previousPageMeta = previousPage.getItemMeta();
        ItemMeta createFlipMeta = createFlip.getItemMeta();

        nextPageMeta.customName(Component.text("Next Page")
                .decoration(TextDecoration.ITALIC, false));

        previousPageMeta.customName(Component.text("Previous Page")
                .decoration(TextDecoration.ITALIC, false));

        createFlipMeta.customName(Component.text("Create Flip")
                .decoration(TextDecoration.ITALIC, false));

        nextPage.setItemMeta(nextPageMeta);
        previousPage.setItemMeta(previousPageMeta);
        createFlip.setItemMeta(createFlipMeta);

        for(CoinFlip coinFlip : flipQueue.getQueue().values()) {
            inv.addItem(createItem(coinFlip));

        }


        inv.setItem(26, nextPage);
        inv.setItem(24, previousPage);
        inv.setItem(18, createFlip);

        player.openInventory(inv);
    }

    public ItemStack createItem(CoinFlip coinFlip) {
        Player player = coinFlip.getPlayer();
        int value = coinFlip.getValue();
        ItemStack skull = new ItemStack(Material.PLAYER_HEAD, 1);
        SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
        skullMeta.setOwningPlayer(Bukkit.getOfflinePlayer(player.getUniqueId()));
        skullMeta.customName(Component.text(player.getName() + "'s CoinFlip")
                .decoration(TextDecoration.ITALIC, false));

        List<Component> lore = new ArrayList<>();
        lore.add(Component.text("Value: " + value)
                .decoration(TextDecoration.ITALIC, false));
        skullMeta.lore(lore);

        skullMeta.getPersistentDataContainer().set(coinflipkey, PersistentDataType.STRING, player.getName());

        skull.setItemMeta(skullMeta);
        return skull;
    }

    public ItemStack createSkull(Player player) {
        ItemStack skull = new ItemStack(Material.PLAYER_HEAD, 1);
        SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
        skullMeta.setOwningPlayer(Bukkit.getOfflinePlayer(player.getUniqueId()));
        skullMeta.customName(Component.text(player.getName())
                .decoration(TextDecoration.ITALIC, false));

        skull.setItemMeta(skullMeta);
        return skull;
    }


    public void updateChoosingGUI(CoinFlip coinFlip) {
        inv.addItem(createItem(coinFlip));
    }



}
