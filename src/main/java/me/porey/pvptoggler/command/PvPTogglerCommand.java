package me.porey.pvptoggler.command;

import me.porey.pvptoggler.PvPTogglerPlugin;
import me.porey.pvptoggler.util.CachedMessages;
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

        CachedMessages cachedMessages = plugin.getCachedMessages();
        cachedMessages.clearCaches();

        plugin.registerOptionalListeners();

        sender.sendMessage(cachedMessages.fromConfig(
                        "general-messages.reload-message")
                .replace("{time}", System.currentTimeMillis() - currentTime + "")
        );
        return false;
    }
}