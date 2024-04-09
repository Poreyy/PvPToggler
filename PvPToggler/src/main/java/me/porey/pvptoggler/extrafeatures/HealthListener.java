package me.porey.pvptoggler.extrafeatures;

import me.porey.pvptoggler.pvp.PvPManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class HealthListener implements Listener {

    private final PvPManager pvpManager;

    public HealthListener(PvPManager pvpManager) {
        this.pvpManager = pvpManager;
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
        if (!pvpManager.isFighter(e.getEntity().getUniqueId()))
            return;

        e.setDamage(0.0);
    }
}