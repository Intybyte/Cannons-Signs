package me.vaan.cannonsigns.utils;

import at.pavlov.cannons.cannon.Cannon;
import at.pavlov.cannons.cannon.CannonDesign;
import at.pavlov.cannons.projectile.Projectile;
import at.pavlov.cannons.sign.CannonSign;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;

import java.util.UUID;

public class SignGetUtils {

    /**
     * returns the strings for the sign
     *
     * @param index line on sign
     * @return line on the sign
     */
    public static String getSignString(int index, Cannon cannon) {
        String cannonName = cannon.getCannonName();
        UUID owner = cannon.getOwner();
        Projectile loadedProjectile = cannon.getLoadedProjectile();
        int loadedGunpowder = cannon.getLoadedGunpowder();

        return switch (index) {
            case 0 -> {
                // Cannon name in the first line
                if (cannonName == null) cannonName = "missing Name";
                yield cannonName;
            }
            case 1 -> {
                // Cannon owner in the second
                if (owner == null)
                    yield "missing Owner";
                OfflinePlayer bPlayer = Bukkit.getOfflinePlayer(owner);
                if (!bPlayer.hasPlayedBefore())
                    yield "not found";
                yield bPlayer.getName();
            }
            case 2 -> {
                // loaded Gunpowder/Projectile
                if (loadedProjectile != null)
                    yield loadedGunpowder + " | " + loadedProjectile.getMaterialInformation();
                else yield loadedGunpowder + " | " + "null";
            }
            // angles
            case 3 -> cannon.getHorizontalAngle() + "/" + cannon.getVerticalAngle();
            default -> "missing";
        };
    }

    private static String getLineOfCannonSigns(Cannon cannon, int line) {
        String lineStr = "";
        CannonDesign design = cannon.getCannonDesign();
        // goto the first cannon sign
        for (Location signLoc : design.getChestsAndSigns(cannon)) {
            lineStr = CannonSign.getLineOfThisSign(signLoc.getBlock(), line);
            // if something is found return it
            if (lineStr != null && !lineStr.isEmpty()) {
                return lineStr;
            }
        }

        return lineStr;
    }
}
