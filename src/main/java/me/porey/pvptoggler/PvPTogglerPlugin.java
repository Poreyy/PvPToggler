package me.porey.pvptoggler;

import com.jeff_media.updatechecker.UpdateCheckSource;
import com.jeff_media.updatechecker.UpdateChecker;
import me.porey.pvptoggler.command.PvPCommand;
import me.porey.pvptoggler.command.PvPTogglerCommand;
import me.porey.pvptoggler.extrafeatures.DeathListener;
import me.porey.pvptoggler.extrafeatures.HealthListener;
import me.porey.pvptoggler.extrafeatures.JoinListener;
import me.porey.pvptoggler.util.PvPTogglerPlaceholderExpansion;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class PvPTogglerPlugin extends JavaPlugin {

    private final static String SPIGOT_RESOURCE_ID = "116141";

    private PvPToggler pvpToggler;

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
        this.pvpToggler.getFightManager().onDisable();
    }

    private void initConfig() {
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
    }

    private void registerManagers() {
        this.pvpToggler = new PvPTogglerImpl(this);
    }

    private void loadHooks() {
        if (getServer().getPluginManager().isPluginEnabled("PlaceholderAPI"))
            new PvPTogglerPlaceholderExpansion(this).register();
    }

    private void initializeListeners() {
        this.deathListener = new DeathListener(this);
        this.joinListener = new JoinListener(this);
        this.healthListener = new HealthListener(this);

        registerOptionalListeners();
    }

    public void registerOptionalListeners() {
        deathListener.registerOptionally();
        healthListener.registerOptionally();
        joinListener.registerOptionally();
    }

    private void registerCommands() {
        Objects.requireNonNull(getCommand("pvp")).setExecutor(new PvPCommand(this.pvpToggler));
        Objects.requireNonNull(getCommand("pvptoggler")).setExecutor(new PvPTogglerCommand(this));
    }

    public PvPToggler getPvPToggler() {
        return pvpToggler;
    }
}