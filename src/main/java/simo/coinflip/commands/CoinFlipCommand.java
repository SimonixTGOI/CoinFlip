package simo.coinflip.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import simo.coinflip.gui.ChoosingGUI;

public class CoinFlipCommand implements CommandExecutor {
    private final ChoosingGUI choosingGUI;
    public CoinFlipCommand(ChoosingGUI choosingGUI) {
        this.choosingGUI = choosingGUI;
    }


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if(!(sender instanceof Player player)) {
            sender.sendMessage("Only player may execute this command");
            return true;
        }

        if(!player.hasPermission("coinflip.command")) {
            player.sendMessage("No permission");
            return true;
        }

        choosingGUI.openGUI(player);

        return true;
    }
}
