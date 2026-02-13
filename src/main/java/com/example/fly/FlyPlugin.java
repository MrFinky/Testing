package com.example.fly;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class FlyPlugin extends JavaPlugin implements CommandExecutor {
    private static final String FLY_PERMISSION = "flyplugin.fly";
    private static final String SPEED_CONFIG_PATH = "default-flight-speed";

    @Override
    public void onEnable() {
        saveDefaultConfig();

        if (getCommand("fly") != null) {
            getCommand("fly").setExecutor(this);
        } else {
            getLogger().severe("Failed to register /fly command. Check plugin descriptors.");
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!command.getName().equalsIgnoreCase("fly")) {
            return false;
        }

        if (!(sender instanceof Player player)) {
            sender.sendMessage(ChatColor.RED + "Only players can use this command.");
            return true;
        }

        if (!player.hasPermission(FLY_PERMISSION)) {
            player.sendMessage(ChatColor.RED + "You do not have permission to use /fly.");
            return true;
        }

        boolean enableFlight = !player.getAllowFlight();
        player.setAllowFlight(enableFlight);

        if (enableFlight) {
            player.setFlySpeed(getConfiguredFlightSpeed());
            player.setFlying(true);
            player.sendMessage(ChatColor.GREEN + "Flight enabled.");
        } else {
            player.setFlying(false);
            player.sendMessage(ChatColor.GREEN + "Flight disabled.");
        }

        return true;
    }

    private float getConfiguredFlightSpeed() {
        double configuredSpeed = getConfig().getDouble(SPEED_CONFIG_PATH, 0.1D);
        double clampedSpeed = Math.max(-1.0D, Math.min(1.0D, configuredSpeed));
        return (float) clampedSpeed;
    }
}
