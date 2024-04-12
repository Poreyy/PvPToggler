package me.porey.pvptoggler.extrafeatures;

import me.porey.pvptoggler.pvp.PvPManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    private final PvPManager pvpManager;

    public JoinListener(PvPManager pvpManager) {
        this.pvpManager = pvpManager;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        pvpManager.setFighter(e.getPlayer(), true);
    }
}