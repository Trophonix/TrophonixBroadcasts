package com.trophonix.broadcaster;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * Created by Lucas on 12/11/2016.
 */
public class BroadcastsCommand implements CommandExecutor {

    private Broadcaster plugin;

    public BroadcastsCommand(Broadcaster plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1 && args[0].equalsIgnoreCase("list")) {
            sender.sendMessage(ChatColor.YELLOW + "[Trophonix Broadcaster] Broadcasts:");
            for (int index = 0; index < plugin.getBroadcasts().size(); index++) {
                Broadcast broadcast = plugin.getBroadcasts().get(index);
                sender.sendMessage(ChatColor.YELLOW + Integer.toString(index + 1) + ". Every " + broadcast.getDelay() + " seconds:");
                sender.sendMessage("  " + broadcast.getBroadcast());
            }
            sender.sendMessage(" ");
            return true;
        } else if (args.length == 2 && args[0].equalsIgnoreCase("remove")) {
            int index;
            try {
                index = Integer.parseInt(args[1]);
            } catch (NumberFormatException ex) {
                sender.sendMessage(ChatColor.DARK_RED + "Error: " + ChatColor.RED + "On input string: \"" + args[1] + "\"");
                return true;
            }
            if (index < 1 || index > plugin.getBroadcasts().size()) {
                sender.sendMessage(ChatColor.DARK_RED + "Error: " + ChatColor.RED + "ID must be within bounds of the list!");
                return true;
            }
            Broadcast broadcast = plugin.getBroadcasts().get(index - 1);
            broadcast.cancel();
            plugin.getBroadcasts().remove(broadcast);
            sender.sendMessage(ChatColor.YELLOW + "Broadcast successfully removed!");
            return true;
        } else if (args.length >= 3 && (args[0].equalsIgnoreCase("add") || args[0].equalsIgnoreCase("schedule"))) {
            int delay;
            try {
                delay = Integer.parseInt(args[1]);
            } catch (NumberFormatException ex) {
                sender.sendMessage(ChatColor.DARK_RED + "Error: " + ChatColor.RED + "On input string: \"" + args[1] + "\"");
                return true;
            }
            String broadcast = "";
            for (int i = 2; i < args.length; i++) {
                broadcast = broadcast + args[i] + " ";
            }
            plugin.getBroadcasts().add(new Broadcast(plugin, broadcast, delay));
            sender.sendMessage(ChatColor.YELLOW + "Broadcast successfully scheduled!");
            return true;
        }
        sender.sendMessage(new String[]{
                " ",
                ChatColor.YELLOW + "[Trophonix Broadcaster] Help:",
                ChatColor.YELLOW + "/broadcasts list " + ChatColor.WHITE + "List current broadcasts",
                ChatColor.YELLOW + "/broadcasts add <delay in seconds> <broadcast text> " + ChatColor.WHITE + "Schedule a broadcast",
                ChatColor.YELLOW + "/broadcasts remove <id> " + ChatColor.WHITE + "Remove a broadcast",
                " "
        });
        return true;
    }

}
