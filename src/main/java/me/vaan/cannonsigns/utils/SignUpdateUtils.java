package me.vaan.cannonsigns.utils;

import at.pavlov.cannons.cannon.Cannon;
import at.pavlov.cannons.cannon.CannonBlocks;
import at.pavlov.cannons.container.SimpleBlock;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.block.data.type.WallSign;

public class SignUpdateUtils {
    public static void updateSign(Block block, Cannon cannon) {
        Sign sign = (Sign) block.getState();
        if (cannon.isValid()) {
            // Cannon name in the first line
            sign.setLine(0, SignGetUtils.getSignString(0, cannon));
            // Cannon owner in the second
            sign.setLine(1, SignGetUtils.getSignString(1, cannon));
            // loaded Gunpowder/Projectile
            sign.setLine(2, SignGetUtils.getSignString(2, cannon));
            // angles
            sign.setLine(3, SignGetUtils.getSignString(3, cannon));
        } else {
            // Cannon name in the first line
            sign.setLine(0, "this cannon is");
            // Cannon owner in the second
            sign.setLine(1, "damaged");
            // loaded Gunpowder/Projectile
            sign.setLine(2, "");
            // angles
            sign.setLine(3, "");
        }
        sign.setEditable(false);
        sign.update(true);
    }

    /**
     * updates all signs that are attached to a cannon
     */
    public static void updateCannonSigns(Cannon cannon) {
        // update all possible sign locations
        for (Location signLoc : cannon.getCannonDesign().getChestsAndSigns(cannon)) {
            //check blocktype and orientation before updating sign.
            if (isCannonSign(signLoc, cannon))
                updateSign(signLoc.getBlock(), cannon);
        }
    }

    public static boolean isCannonSign(Location loc, Cannon cannon) {
        if (!(loc.getBlock().getBlockData() instanceof WallSign)) {
            return false;
        }

        CannonBlocks cannonBlocks = cannon.getCannonDesign().getCannonBlockMap().get(cannon.getCannonDirection());

        if (cannonBlocks == null) {
            return false;
        }

        for (SimpleBlock cannonblock : cannonBlocks.getChestsAndSigns()) {
            // compare location
            if (cannonblock.toLocation(cannon.getWorldBukkit(), cannon.getOffset()).equals(loc)) {
                //Block block = loc.getBlock();
                //compare and data
                //only the two lower bits of the bytes are important for the direction (delays are not interessting here)
                //if (cannonblock.getData() == block.getData() || block.getData() == -1 || cannonblock.getData() == -1 )
                return true;
            }
        }
        return false;
    }
}
