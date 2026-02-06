package com.example.fly;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class FlyPlugin extends JavaPlugin {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!command.getName().equalsIgnoreCase("fly")) {
            return false;
        }

        if (!(sender instanceof Player player)) {
            sender.sendMessage(ChatColor.RED + "Only players can use this command.");
            return true;
        }

        boolean enableFlight = !player.getAllowFlight();
        player.setAllowFlight(enableFlight);
        if (!enableFlight) {
            player.setFlying(false);
        }

        String message = enableFlight ? "Flight enabled." : "Flight disabled.";
        player.sendMessage(ChatColor.GREEN + message);
        return true;
    }
}
