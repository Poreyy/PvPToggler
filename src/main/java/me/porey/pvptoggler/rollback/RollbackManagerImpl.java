package me.porey.pvptoggler.rollback;

import me.porey.pvptoggler.PvPTogglerPlugin;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RollbackManagerImpl implements RollbackManager {

    private final PvPTogglerPlugin plugin;

    public RollbackManagerImpl(PvPTogglerPlugin plugin) {
        this.plugin = plugin;
    }

    private final Map<UUID, PreviousPlayerData> previousPlayerDataMap = new HashMap<>();

    public void savePlayer(Player p) {
        PlayerInventory inventory = p.getInventory();

        PreviousPlayerData previousPlayerData = new PreviousPlayerData(inventory.getContents(), inventory.getArmorContents(), p.getLocation());

        this.previousPlayerDataMap.put(p.getUniqueId(), previousPlayerData);
    }

    public void restorePlayer(Player p) {
        UUID uuid = p.getUniqueId();

        PreviousPlayerData previousPlayerData = this.previousPlayerDataMap.remove(uuid);

        if(previousPlayerData == null) return;

        restore(previousPlayerData, p);
    }

    private void restore(PreviousPlayerData previousPlayerData, Player p) {
        FileConfiguration config = plugin.getConfig();

        if(config.getBoolean("cached-inventories"))
            previousPlayerData.restoreContents(p);

        if(config.getBoolean("cached-locations"))
            previousPlayerData.restoreLocation(p);
    }
}