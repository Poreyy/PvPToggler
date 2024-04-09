package me.porey.pvptoggler.rollback;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
class PreviousPlayerData {

    private final ItemStack[] contents;
    private final ItemStack[] armorContents;
    private final Location location;

    PreviousPlayerData(ItemStack[] contents, ItemStack[] armorContents, Location location) {
        this.contents = contents;
        this.armorContents = armorContents;
        this.location = location;
    }
    void restoreContents(Player p) {
       PlayerInventory inventory = p.getInventory();

        inventory.setContents(this.contents);
        inventory.setArmorContents(this.armorContents);
    }

    void restoreLocation(Player p) {
        p.teleport(location);
    }
}