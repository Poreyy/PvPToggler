package me.porey.pvptoggler.extrafeatures;

import me.porey.pvptoggler.PvPTogglerPlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    private final PvPTogglerPlugin plugin;

    public JoinListener(PvPTogglerPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();

        if(p.hasPlayedBefore()) return;

        plugin.getPvPToggler().getFightManager().addFighter(e.getPlayer());
    }

    public void registerOptionally() {
        if(plugin.getConfig().getBoolean("pvp-enabled-by-default"))
            plugin.getServer().getPluginManager().registerEvents(this, plugin);
        else
            HandlerList.unregisterAll(this);
    }
}