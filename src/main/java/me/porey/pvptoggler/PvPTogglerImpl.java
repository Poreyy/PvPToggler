package me.porey.pvptoggler;

import me.porey.pvptoggler.pvp.FightManager;
import me.porey.pvptoggler.pvp.FightManagerImpl;
import me.porey.pvptoggler.rollback.RollbackManager;
import me.porey.pvptoggler.rollback.RollbackManagerImpl;
import me.porey.pvptoggler.util.CachedStringValues;
import me.porey.pvptoggler.util.CachedValues;

public class PvPTogglerImpl implements PvPToggler {

    private final FightManager fightManager;
    private final CachedValues<String> cachedStringValues;
    private final RollbackManager rollbackManager;

    public PvPTogglerImpl(PvPTogglerPlugin plugin) {
        this.fightManager = new FightManagerImpl(plugin);
        this.fightManager.register();
        this.cachedStringValues = new CachedStringValues(plugin);
        this.rollbackManager = new RollbackManagerImpl(plugin);
    }

    @Override
    public FightManager getFightManager() {
        return this.fightManager;
    }

    @Override
    public CachedValues<String> getCachedMessages() {
        return this.cachedStringValues;
    }

    @Override
    public RollbackManager getRollbackManager() {
        return this.rollbackManager;
    }
}