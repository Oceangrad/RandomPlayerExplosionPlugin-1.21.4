package me.oceangrad.randomplayerexplosion;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

public class Util {
    public static final NamespacedKey EXPLOSION_TIMER_KEY = NamespacedKey.fromString("exp-timer");
    public static final NamespacedKey TIMEOUT_ID_KEY = NamespacedKey.fromString("timeout-id");
    public static final BukkitScheduler SCHEDULER = Bukkit.getScheduler();

    public static int setNewTimer(Player player) {
        int timer = getRandomInt(30, 180);
        player.getPersistentDataContainer().set(EXPLOSION_TIMER_KEY, PersistentDataType.INTEGER, timer);

        return timer;
    }

    public static int getPlayerTimer(Player player) {
        PersistentDataContainer container = player.getPersistentDataContainer();

        int timer;
        if (!container.has(EXPLOSION_TIMER_KEY)) {
            timer = getRandomInt(30, 180);
            container.set(Util.EXPLOSION_TIMER_KEY, PersistentDataType.INTEGER, timer);
            return timer;
        }

        timer = container.get(EXPLOSION_TIMER_KEY, PersistentDataType.INTEGER);
        return timer;
    }

    public static int getRandomInt(int start, int end) {
        int num = (int) ((Math.random()*(end-start))+start);
        return num;
    }

    public static void runExplosionTimer(Player player) {
        ExplosionTimerTask task = new ExplosionTimerTask(player);
        task.runTaskTimer(JavaPlugin.getPlugin(RandomPlayerExplosionPlugin.class), 20L, 20L);
    }

    public static void cancelExplosionTimer(Player player) {
        PersistentDataContainer container = player.getPersistentDataContainer();

        int taskId = container.get(Util.TIMEOUT_ID_KEY, PersistentDataType.INTEGER);
        Util.SCHEDULER.cancelTask(taskId);
    }
}
