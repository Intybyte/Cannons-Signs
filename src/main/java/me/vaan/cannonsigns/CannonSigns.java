package me.vaan.cannonsigns;

import at.pavlov.cannons.cannon.CannonManager;
import me.vaan.cannonsigns.listeners.SignListener;
import me.vaan.cannonsigns.utils.SignUpdateUtils;
import org.bukkit.plugin.java.JavaPlugin;

public final class CannonSigns extends JavaPlugin {

    private static CannonSigns instance;

    @Override
    public void onEnable() {
        instance = this;
        this.getServer().getPluginManager().registerEvents(new SignListener(), this);

        getServer().getScheduler().runTaskTimer(this, () -> {
            var cannonList = CannonManager.getCannonList();
            for (var cannon : cannonList.values()) {
                SignUpdateUtils.updateCannonSigns(cannon);
            }
        }, 0, 10);
    }

    public static CannonSigns getInstance() {
        return instance;
    }
}
