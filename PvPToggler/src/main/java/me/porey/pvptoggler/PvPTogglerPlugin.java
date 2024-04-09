package me.porey.pvptoggler;

import me.porey.pvptoggler.pvp.PvPManager;
import me.porey.pvptoggler.rollback.RollbackManager;
import me.porey.pvptoggler.command.PvPCommand;
import me.porey.pvptoggler.command.PvPTogglerCommand;
import me.porey.pvptoggler.extrafeatures.HealthListener;
import me.porey.pvptoggler.util.CachedMessages;
import me.porey.pvptoggler.util.PvPTogglerPlaceholderExpansion;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class PvPTogglerPlugin extends JavaPlugin {

    private CachedMessages cachedMessages;
    private PvPManager pvpManager;
    private RollbackManager rollbackManager;
    private HealthListener healthListener;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        registerManagers();
        initializeListeners();
        registerCommands();
    }

    private void registerManagers() {
        this.cachedMessages = new CachedMessages(this);

        this.pvpManager = new PvPManager(this);
        this.pvpManager.register();

        this.rollbackManager = new RollbackManager(this);

        if(!getServer().getPluginManager().isPluginEnabled("PlaceholderAPI"))
            return;

        new PvPTogglerPlaceholderExpansion(pvpManager).register();
    }

    private void initializeListeners() {
        this.healthListener = new HealthListener(pvpManager);

        registerListeners();
    }

    public void registerListeners() {
        PluginManager pluginManager = getServer().getPluginManager();

        if (getConfig().getBoolean("stay-at-max-health", true)) {
            pluginManager.registerEvents(healthListener, this);
            return;
        }
        HandlerList.unregisterAll(healthListener);
    }

    private void registerCommands() {
        Objects.requireNonNull(getCommand("pvp")).setExecutor(new PvPCommand(this));
        Objects.requireNonNull(getCommand("pvptoggler")).setExecutor(new PvPTogglerCommand(this));
    }

    public CachedMessages getCachedMessages() {
        return this.cachedMessages;
    }

    public PvPManager getPvPManager() {
        return pvpManager;
    }

    public RollbackManager getRollbackManager() {
        return rollbackManager;
    }
}