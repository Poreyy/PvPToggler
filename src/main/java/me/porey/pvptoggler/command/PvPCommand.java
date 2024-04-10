package me.porey.pvptoggler.command;

import me.porey.pvptoggler.PvPTogglerPlugin;
import me.porey.pvptoggler.pvp.PvPManager;
import me.porey.pvptoggler.util.CachedMessages;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.util.UUID;

public class PvPCommand implements CommandExecutor {

    private final CachedMessages cachedMessages;
    private final PvPManager pvpManager;

    public PvPCommand(PvPTogglerPlugin plugin) {
        this.cachedMessages = plugin.getCachedMessages();
        this.pvpManager = plugin.getPvPManager();
    }

    @Override
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label, @Nonnull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(cachedMessages.fromConfig("general-messages.only-players"));
            return true;
        }
        Player p = (Player) sender;
        if (args.length == 0) {
            togglePvP(p, p, true);
            return true;
        }
        if(args[0].equalsIgnoreCase("-info")) {
            sendInfoMenu(p);
            return true;
        }
        if (!p.hasPermission("pvptoggler.toggle.others")) {
            togglePvP(p, p, true);
            return true;
        }
        Player target = p.getServer().getPlayerExact(args[0]);
        if(target == null) {
            p.sendMessage(cachedMessages.fromConfig("general-messages.player-not-found"));
            return false;
        }
        togglePvP(p, target, false);
        return true;
    }

    private void sendInfoMenu(Player p) {
        p.sendMessage(cachedMessages.colorize("&aPvPToggler plugin made by&e Porey&a!"));
        p.sendMessage(cachedMessages.colorize("&aMinecraft user:&e Poreyy"));
        p.sendMessage(cachedMessages.colorize("&aDiscord user:&e porey"));
    }

    private void togglePvP(CommandSender sender, Player target, boolean self) {
        UUID targetUUID = target.getUniqueId();

        boolean isFighter = !pvpManager.isFighter(targetUUID);
        pvpManager.setFighter(target, isFighter);

        if(self)
            sender.sendMessage(cachedMessages.fromConfig(isFighter ? "pvp-enabled" : "pvp-disabled"));
        else
            sender.sendMessage(cachedMessages.fromConfig(isFighter ? "pvp-enabled-other" : "pvp-disabled-other").replace("{target}", target.getName()));
    }
}