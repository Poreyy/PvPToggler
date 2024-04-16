package me.porey.pvptoggler.pvp;

import me.porey.pvptoggler.PvPTogglerPlugin;
import org.bukkit.Server;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class FightManagerImpl implements FightManager {

    private final PvPTogglerPlugin plugin;

    public FightManagerImpl(PvPTogglerPlugin plugin) {
        this.plugin = plugin;
    }

    private final Set<UUID> fighters = new HashSet<>();

    public void register() {
        plugin.getServer().getPluginManager().registerEvents(new PvPListener(plugin), plugin);
    }

    public void addFighter(Player p) {
        this.fighters.add(p.getUniqueId());

        plugin.getPvPToggler().getRollbackManager().savePlayer(p);

        Server server = p.getServer();

        plugin.getConfig().getStringList("on-pvp-enable-console-commands").forEach(command -> server.dispatchCommand(server.getConsoleSender(), command.replace("{player}", p.getName())));
    }

    public void removeFighter(Player p) {
        this.fighters.remove(p.getUniqueId());

        plugin.getPvPToggler().getRollbackManager().restorePlayer(p);

        Server server = p.getServer();

        plugin.getConfig().getStringList("on-pvp-disable-console-commands").forEach(command -> server.dispatchCommand(server.getConsoleSender(), command.replace("{player}", p.getName())));
    }

    public boolean isFighter(Player p) {
        return this.fighters.contains(p.getUniqueId());
    }

    public void onDisable() {
        plugin.getServer().getOnlinePlayers().forEach(player -> plugin.getPvPToggler().getRollbackManager().restorePlayer(player));
    }
}