package me.porey.pvptoggler.util;

import me.porey.pvptoggler.PvPTogglerPlugin;
import org.bukkit.ChatColor;

import java.util.HashMap;
import java.util.Map;

public class CachedStringValues implements CachedValues<String> {

    private final PvPTogglerPlugin plugin;

    public CachedStringValues(PvPTogglerPlugin plugin) {
        this.plugin = plugin;
    }

    private final Map<String, String> cachedMessages = new HashMap<>();

    public String fromConfig(String key) {
        String configValue = plugin.getConfig().getString(key, key);
        return colorize(configValue);
    }

    public String colorize(String text) {
        return cachedMessages.computeIfAbsent(text, (string) -> ChatColor.translateAlternateColorCodes('&', string));
    }

    public void clearCaches() {
        this.cachedMessages.clear();
    }
}