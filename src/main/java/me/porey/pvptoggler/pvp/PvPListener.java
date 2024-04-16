package me.porey.pvptoggler.pvp;

import me.porey.pvptoggler.PvPTogglerPlugin;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;

class PvPListener implements Listener {

    private final PvPTogglerPlugin plugin;

    PvPListener(PvPTogglerPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler(ignoreCancelled = true)
    public void onDamageByEntity(EntityDamageByEntityEvent e) {
        if (e.getEntityType() != EntityType.PLAYER)
            return;
        if (e.getDamager().getType() != EntityType.PLAYER)
            return;

        FightManager pvpManager = plugin.getPvPToggler().getFightManager();

        if (pvpManager.isFighter((Player) e.getEntity()) && pvpManager.isFighter((Player) e.getDamager())) {
            e.setCancelled(false);
            return;
        }

        e.setCancelled(true);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        plugin.getPvPToggler().getFightManager().removeFighter(e.getPlayer());
    }
}