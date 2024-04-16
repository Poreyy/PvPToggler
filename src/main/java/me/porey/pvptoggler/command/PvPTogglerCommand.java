package me.porey.pvptoggler.command;

import me.porey.pvptoggler.PvPTogglerPlugin;
import me.porey.pvptoggler.util.CachedValues;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import javax.annotation.Nonnull;

public class PvPTogglerCommand implements CommandExecutor {

    private final PvPTogglerPlugin plugin;

    public PvPTogglerCommand(PvPTogglerPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, @Nonnull Command command, @Nonnull String label, @Nonnull String[] args) {
        long currentTime = System.currentTimeMillis();

        plugin.reloadConfig();

        CachedValues<String> cachedMessages = plugin.getPvPToggler().getCachedMessages();
        cachedMessages.clearCaches();

        plugin.registerOptionalListeners();

        sender.sendMessage(cachedMessages.fromConfig("general-messages.reload-message").replace("{time}", String.valueOf(System.currentTimeMillis() - currentTime)));
        return false;
    }
}