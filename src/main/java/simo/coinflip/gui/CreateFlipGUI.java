package simo.coinflip.gui;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import simo.coinflip.managers.CreatingQueueManager;

import java.util.ArrayList;
import java.util.List;

public class CreateFlipGUI {
    private final CreatingQueueManager creatingQueueManager;

    public CreateFlipGUI(CreatingQueueManager creatingQueueManager) {
        this.creatingQueueManager = creatingQueueManager;
    }


    public void openGUI(Player player) {
        Inventory inv = Bukkit.createInventory(player, 9, Component.text("Create Flip"));
        ItemStack create = new ItemStack(Material.GREEN_STAINED_GLASS);
        ItemStack add = new ItemStack(Material.GREEN_CONCRETE);
        ItemStack remove = new ItemStack(Material.RED_CONCRETE);
        ItemStack close = new ItemStack(Material.BARRIER);

        ItemMeta createMeta = create.getItemMeta();
        ItemMeta addMeta = add.getItemMeta();
        ItemMeta removeMeta = remove.getItemMeta();
        ItemMeta closeMeta = close.getItemMeta();

        createMeta.customName(Component.text("Create Flip")
                .decoration(TextDecoration.ITALIC, false));

        int value = 0;
        if(!(creatingQueueManager.getCoinFlip(player) == null)) {
            value = creatingQueueManager.getCoinFlip(player).getValue();
        }
        List<Component> lore =  new ArrayList<>();
        lore.add(Component.text("Value:" + value)
                .decoration(TextDecoration.ITALIC, false));
        createMeta.lore(lore);



        addMeta.customName(Component.text("Add 100")
                .decoration(TextDecoration.ITALIC, false));

        removeMeta.customName(Component.text("Remove 100")
                .decoration(TextDecoration.ITALIC, false));

        closeMeta.customName(Component.text("Back")
                .decoration(TextDecoration.ITALIC, false));

        create.setItemMeta(createMeta);
        add.setItemMeta(addMeta);
        remove.setItemMeta(removeMeta);
        close.setItemMeta(closeMeta);

        inv.setItem(6, add);
        inv.setItem(4, create);
        inv.setItem(2, remove);
        inv.setItem(0, close);

        player.openInventory(inv);

    }

    public void updateGUI(Inventory inventory, Player player) {
        ItemStack create = new ItemStack(Material.GREEN_STAINED_GLASS);
        ItemMeta createMeta = create.getItemMeta();

        createMeta.customName(Component.text("Create Flip")
                .decoration(TextDecoration.ITALIC, false));

        int value = 0;
        if(!(creatingQueueManager.getCoinFlip(player) == null)) {
            value = creatingQueueManager.getCoinFlip(player).getValue();
        }
        List<Component> lore =  new ArrayList<>();
        lore.add(Component.text("Value:" + value)
                .decoration(TextDecoration.ITALIC, false));
        createMeta.lore(lore);

        create.setItemMeta(createMeta);
        inventory.setItem(4, create);
    }
}
