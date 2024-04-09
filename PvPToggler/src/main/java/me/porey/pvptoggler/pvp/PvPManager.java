package me.porey.pvptoggler.pvp;

import me.porey.pvptoggler.PvPTogglerPlugin;
import org.bukkit.Server;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class PvPManager {

    private final PvPTogglerPlugin plugin;

    public PvPManager(PvPTogglerPlugin plugin) {
        this.plugin = plugin;
    }

    private final Set<UUID> fighters = new HashSet<>();

    public void register() {
        plugin.getServer().getPluginManager().registerEvents(new PvPListener(plugin), plugin);
    }

    public void setFighter(Player p, boolean fighter) {
        Server server = p.getServer();

        if (fighter) {
            this.fighters.add(p.getUniqueId());
            plugin.getRollbackManager().savePlayer(p);

            plugin.getConfig().getStringList("on-pvp-enable-console-commands").forEach(command -> server.dispatchCommand(server.getConsoleSender(), command.replace("{player}", p.getName())));
            return;
        }
        this.fighters.remove(p.getUniqueId());
        plugin.getRollbackManager().restorePlayer(p);

        plugin.getConfig().getStringList("on-pvp-disable-console-commands").forEach(command -> server.dispatchCommand(server.getConsoleSender(), command.replace("{player}", p.getName())));
    }

    public boolean isFighter(UUID playerUUID) {
        return this.fighters.contains(playerUUID);
    }
}