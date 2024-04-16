package me.porey.pvptoggler.util;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.porey.pvptoggler.PvPTogglerPlugin;
import me.porey.pvptoggler.pvp.FightManager;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PvPTogglerPlaceholderExpansion extends PlaceholderExpansion {

    private final CachedValues<String> cachedMessages;
    private final FightManager pvpManager;

    public PvPTogglerPlaceholderExpansion(PvPTogglerPlugin plugin) {
        this.cachedMessages = plugin.getPvPToggler().getCachedMessages();
        this.pvpManager = plugin.getPvPToggler().getFightManager();
    }

    @Override
    public @NotNull String getIdentifier() {
        return "pvptoggler";
    }

    @Override
    public @NotNull String getAuthor() {
        return "Porey";
    }

    @Override
    public @NotNull String getVersion() {
        return "0.0.1";
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public @Nullable String onPlaceholderRequest(Player p, @NotNull String params) {
        if (params.equalsIgnoreCase("pvp"))
            return pvpManager.isFighter(p) ?
                    cachedMessages.fromConfig("placeholders-output.true") :
                    cachedMessages.fromConfig("placeholders-output.false");
        return null;
    }
}