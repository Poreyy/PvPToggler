package me.porey.pvptoggler.extrafeatures;

import me.porey.pvptoggler.PvPTogglerPlugin;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class HealthListener implements Listener {

    private final PvPTogglerPlugin plugin;

    public HealthListener(PvPTogglerPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onDamage(EntityDamageByEntityEvent e) {
        if(e.getEntityType() != EntityType.PLAYER) return;

        if (!plugin.getPvPToggler().getFightManager().isFighter((Player) e.getEntity()))
            return;

        e.setDamage(0.0);
    }

    public void registerOptionally() {
        if(plugin.getConfig().getBoolean("stay-at-max-health"))
            plugin.getServer().getPluginManager().registerEvents(this, plugin);
        else
            HandlerList.unregisterAll(this);
    }
}