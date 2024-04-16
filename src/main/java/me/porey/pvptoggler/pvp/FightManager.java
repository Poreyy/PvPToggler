package me.porey.pvptoggler.pvp;

import org.bukkit.entity.Player;

public interface FightManager {

    void register();

    /**
     * Add a player to fighters.
     * @param player Player to add to fighters.
     */
    void addFighter(Player player);

    /**
     * Remove a player from fighters.
     * @param player Player to remove from fighters.
     */
    void removeFighter(Player player);

    /**
     *
     * @param player Player to check the fighting status of.
     * @return returns whether the player is a fighter or not.
     */
    boolean isFighter(Player player);

    void onDisable();
}