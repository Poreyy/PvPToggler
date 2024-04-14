package me.porey.pvptoggler;

import com.jeff_media.updatechecker.UpdateCheckSource;
import com.jeff_media.updatechecker.UpdateChecker;
import me.porey.pvptoggler.extrafeatures.DeathListener;
import me.porey.pvptoggler.extrafeatures.JoinListener;
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

    private final static String SPIGOT_RESOURCE_ID = "116141";

    private CachedMessages cachedMessages;
    private PvPManager pvpManager;
    private RollbackManager rollbackManager;
    private DeathListener deathListener;
    private JoinListener joinListener;
    private HealthListener healthListener;

    @Override
    public void onEnable() {
        new UpdateChecker(this, UpdateCheckSource.SPIGOT, SPIGOT_RESOURCE_ID).checkEveryXHours(24).checkNow();
        initConfig();
        registerManagers();
        loadHooks();
        initializeListeners();
        registerCommands();
    }

    @Override
    public void onDisable() {
        this.pvpManager.onDisable();
    }

    private void initConfig() {
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
    }

    private void registerManagers() {
        this.cachedMessages = new CachedMessages(this);

        this.pvpManager = new PvPManager(this);
        this.pvpManager.register();

        this.rollbackManager = new RollbackManager(this);
    }

    private void loadHooks() {
        if (!getServer().getPluginManager().isPluginEnabled("PlaceholderAPI"))
            return;

        new PvPTogglerPlaceholderExpansion(pvpManager).register();
    }

    private void initializeListeners() {
        this.deathListener = new DeathListener(this);
        this.joinListener = new JoinListener(pvpManager);
        this.healthListener = new HealthListener(pvpManager);

        registerOptionalListeners();
    }

    public void registerOptionalListeners() {
        PluginManager pluginManager = getServer().getPluginManager();

        if(getConfig().getBoolean("format-death-message"))
            pluginManager.registerEvents(deathListener, this);
        else
            HandlerList.unregisterAll(deathListener);

        if(getConfig().getBoolean("pvp-enabled-by-default", false))
            pluginManager.registerEvents(joinListener, this);
        else
            HandlerList.unregisterAll(joinListener);

        if (getConfig().getBoolean("stay-at-max-health", true))
            pluginManager.registerEvents(healthListener, this);
        else
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