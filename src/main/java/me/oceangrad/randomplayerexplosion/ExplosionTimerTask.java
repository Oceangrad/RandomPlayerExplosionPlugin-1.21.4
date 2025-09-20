package me.oceangrad.randomplayerexplosion;

import me.oceangrad.randomplayerexplosion.locale.LocaleManager;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.concurrent.atomic.AtomicInteger;

public class ExplosionTimerTask extends BukkitRunnable {
    private Player player;
    private PersistentDataContainer container;
    private AtomicInteger timer;

    public ExplosionTimerTask(Player player) {
        super();
        this.player = player;
        this.container = player.getPersistentDataContainer();
        this.timer = new AtomicInteger(Util.getPlayerTimer(player));
    }
    @Override
    public void run() {
        if (container.getOrDefault(Util.TIMEOUT_ID_KEY, PersistentDataType.INTEGER, -1) != getTaskId()) {
            container.set(Util.TIMEOUT_ID_KEY, PersistentDataType.INTEGER, getTaskId());
        }
        container.set(Util.EXPLOSION_TIMER_KEY, PersistentDataType.INTEGER, timer.get());

        String message = LocaleManager.getLocale(player.getLocale())
                .getExplosionTimerMessage()
                .replace("$timer$", String.valueOf(timer.get()));

        TextComponent actionBarMessage = new TextComponent(message);
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, actionBarMessage);

        if (timer.get() == 0) {
            player.getWorld().createExplosion(player.getLocation(), 15);
            timer.set(Util.setNewTimer(player));
        }
        timer.decrementAndGet();
    }
}
