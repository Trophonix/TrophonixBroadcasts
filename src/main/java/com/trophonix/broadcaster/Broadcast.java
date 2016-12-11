package com.trophonix.broadcaster;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

/**
 * Created by Lucas on 12/11/2016.
 */
public class Broadcast {

    private Broadcaster plugin;

    private BukkitTask task;
    private String broadcast;
    private int delay;

    public Broadcast(Broadcaster plugin, String broadcast, int delay) {
        this.plugin = plugin;
        this.broadcast = ChatColor.translateAlternateColorCodes('&', broadcast);
        setDelay(delay);
    }

    public void cancel() {
        if (task != null)
            task.cancel();
    }

    public void setBroadcast(String broadcast) { this.broadcast = broadcast; }

    public String getBroadcast() { return this.broadcast; }

    public void setDelay(int delay) {
        this.delay = delay;
        if (task != null)
            task.cancel();
        task = plugin.getServer().getScheduler().runTaskTimer(plugin, () -> {
            plugin.getServer().broadcastMessage(this.broadcast);
        }, delay*20, delay*20);
    }

    public int getDelay() { return this.delay; }

}