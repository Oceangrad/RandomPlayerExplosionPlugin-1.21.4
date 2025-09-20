package me.oceangrad.randomplayerexplosion;

import me.oceangrad.randomplayerexplosion.locale.LocaleManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class RandomPlayerExplosionPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        LocaleManager.loadLocalization();

        getServer().getPluginManager().registerEvents(new EventListener(), this);
        getServer().getOnlinePlayers().forEach(Util::runExplosionTimer);
    }

    @Override
    public void onDisable() {
        getServer().getOnlinePlayers().forEach(Util::cancelExplosionTimer);
    }
}
