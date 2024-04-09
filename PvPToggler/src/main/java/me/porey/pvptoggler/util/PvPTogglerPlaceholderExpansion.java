package me.porey.pvptoggler.util;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.porey.pvptoggler.pvp.PvPManager;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PvPTogglerPlaceholderExpansion extends PlaceholderExpansion {

    private final PvPManager pvpManager;

    public PvPTogglerPlaceholderExpansion(PvPManager pvpManager) {
        this.pvpManager = pvpManager;
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
        if(params.equalsIgnoreCase("pvp"))
            return pvpManager.isFighter(p.getUniqueId()) ? "true" : "false";
        if(params.equalsIgnoreCase("pvp_formatted"))
            return pvpManager.isFighter(p.getUniqueId()) ? "On" : "Off";
        return null;
    }
}