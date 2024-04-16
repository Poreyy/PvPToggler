package me.porey.pvptoggler.extrafeatures;

import me.porey.pvptoggler.PvPTogglerPlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.text.DecimalFormat;

public class DeathListener implements Listener {

    private final PvPTogglerPlugin plugin;

    public DeathListener(PvPTogglerPlugin plugin) {
        this.plugin = plugin;
    }

    private static final DecimalFormat PLAYER_HEALTH_FORMAT = new DecimalFormat("#.##");

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDeath(PlayerDeathEvent e) {
        Player victim = e.getEntity();
        Player killer = victim.getKiller();

        if (killer == null) return;

        e.setDeathMessage(plugin.getPvPToggler().getCachedMessages().fromConfig("death-message")
                .replace("{victim}", victim.getName())
                .replace("{killer}", killer.getName())
                .replace("{health}", getHealth(killer))
        );
    }

    private String getHealth(Player p) {
        return PLAYER_HEALTH_FORMAT.format(p.getHealth() / 2);
    }

    public void registerOptionally() {
        if(plugin.getConfig().getBoolean("format-death-message"))
            plugin.getServer().getPluginManager().registerEvents(this, plugin);
        else
            HandlerList.unregisterAll(this);
    }
}