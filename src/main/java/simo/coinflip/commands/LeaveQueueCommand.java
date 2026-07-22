package simo.coinflip.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import simo.coinflip.managers.FlipQueue;

public class LeaveQueueCommand implements CommandExecutor {
    private final FlipQueue flipQueue;

    public LeaveQueueCommand(FlipQueue flipQueue) {
        this.flipQueue = flipQueue;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if(!(sender instanceof Player player)) {
            sender.sendMessage("Only players may execute this command.");
            return true;
        }

        if(flipQueue.leaveQueue(player)) {
            player.sendMessage("You have left the queue.");
        } else {
            player.sendMessage("You are not in a queue.");
        }


        return true;
    }
}
