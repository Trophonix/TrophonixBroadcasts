package com.trophonix.broadcaster;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lucas on 12/11/2016.
 */
public class Broadcaster extends JavaPlugin {

    private File broadcastsFile = new File("plugins" + File.separator + this.getDescription().getName() + File.separator + "broadcasts.yml");
    private FileConfiguration broadcastsConfig;

    private List<Broadcast> broadcasts = new ArrayList<>();

    @Override
    public void onEnable() {
        if (!broadcastsFile.exists()) {
            broadcastsFile.getParentFile().mkdirs();
            try {
                broadcastsFile.createNewFile();
            } catch (IOException ex) {
                ex.printStackTrace();
                getServer().getPluginManager().disablePlugin(this);
                return;
            }
        }
        broadcastsConfig = YamlConfiguration.loadConfiguration(broadcastsFile);
        if (broadcastsConfig.contains("broadcasts")) {
            broadcastsConfig.getConfigurationSection("broadcasts").getKeys(false).forEach((key) -> {
                String broadcast = broadcastsConfig.getString("broadcasts." + key + ".broadcast", "&4NULL");
                int delay = broadcastsConfig.getInt("broadcasts." + key + ".delay", 60);
                broadcasts.add(new Broadcast(this, broadcast, delay));
            });
            System.out.println("[Trophonix Broadcaster] Successfully loaded broadcasts from config!");
        }
        getServer().getPluginCommand("broadcasts").setExecutor(new BroadcastsCommand(this));
    }

    public List<Broadcast> getBroadcasts() { return broadcasts; }

}
