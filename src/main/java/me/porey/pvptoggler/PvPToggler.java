package me.porey.pvptoggler;

import me.porey.pvptoggler.pvp.FightManager;
import me.porey.pvptoggler.rollback.RollbackManager;
import me.porey.pvptoggler.util.CachedValues;

public interface PvPToggler {

    /**
     * @return returns the FightManager.
     */
    FightManager getFightManager();

    /**
     *
     * @return Returns the CachedMessages.
     */
    CachedValues<String> getCachedMessages();

    /**
     *
     * @return Returns the RollbackManager.
     */
    RollbackManager getRollbackManager();
}