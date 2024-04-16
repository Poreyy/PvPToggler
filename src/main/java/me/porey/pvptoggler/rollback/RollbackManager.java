package me.porey.pvptoggler.rollback;

import org.bukkit.entity.Player;

public interface RollbackManager {

    /**
     *
     * @param player The player to save.
     */
    void savePlayer(Player player);

    /**
     *
     * @param player The player to roll back.
     */
    void restorePlayer(Player player);
}