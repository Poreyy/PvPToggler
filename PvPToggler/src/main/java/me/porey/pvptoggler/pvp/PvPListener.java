package me.porey.pvptoggler.pvp;

import me.porey.pvptoggler.PvPTogglerPlugin;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
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

        Player attacked = (Player) e.getEntity();
        Player attacker = (Player) e.getDamager();

        PvPManager pvpManager = plugin.getPvPManager();

        if (pvpManager.isFighter(attacker.getUniqueId()) && pvpManager.isFighter(attacked.getUniqueId())) {
            return;
        }

        e.setCancelled(true);
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        Player victim = e.getEntity();
        Player killer = victim.getKiller();

        if (killer == null) return;

        e.setDeathMessage(plugin.getCachedMessages().fromConfig("death-message")
                .replace("{victim}", victim.getName())
                .replace("{killer}", killer.getName())
                .replace("{health}", String.format("%.2f", killer.getHealth()))
        );
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        plugin.getPvPManager().setFighter(e.getPlayer(), false);
    }
}