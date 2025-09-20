package me.oceangrad.randomplayerexplosion;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class EventListener implements Listener {
    @EventHandler
    public static void playerJoin(PlayerJoinEvent event) {
        Util.runExplosionTimer(event.getPlayer());
    }

    @EventHandler
    public static void playerDisconnect(PlayerQuitEvent event) {
        Util.cancelExplosionTimer(event.getPlayer());
    }
}
