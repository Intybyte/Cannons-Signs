package me.vaan.cannonsigns.utils;

import at.pavlov.cannons.cannon.Cannon;
import at.pavlov.cannons.projectile.Projectile;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.block.data.type.WallSign;

import java.util.UUID;

public class SignGetUtils {

    /**
     * returns line written on the sign sign
     * @return
     */
    public static String getLineOfThisSign(Block block, int line) {
        if (block == null) return null;
        if (!(block.getBlockData() instanceof WallSign)) return null;

        Sign sign = (Sign) block.getState();

        return sign.getLine(line);
    }

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

        switch (index) {

            case 0:
                // Cannon name in the first line
                if (cannonName == null) cannonName = "missing Name";
                return cannonName;
            case 1:
                // Cannon owner in the second
                if (owner == null)
                    return "missing Owner";
                OfflinePlayer bPlayer = Bukkit.getOfflinePlayer(owner);
                if (!bPlayer.hasPlayedBefore())
                    return "not found";
                return bPlayer.getName();
            case 2:
                // loaded Gunpowder/Projectile
                if (loadedProjectile != null)
                    return loadedGunpowder + " | " + loadedProjectile.getMaterialInformation();
                else return loadedGunpowder + " | " + "null";
            case 3:
                // angles
                return cannon.getHorizontalAngle() + "/" + cannon.getVerticalAngle();
        }

        return "missing";
    }

    /**
     * returns the amount of gunpowder that is written on a cannon sign
     * @return
     */
	/*
	public int getGunpowderFromSign() {
		String str[] = getLineOfCannonSigns(2).split(" ");
		// g: 2 c: 123:1
		if (str.length >= 4 )
		{
			return Integer.parseInt(str[1]);
		}
		return 0;
	}

	/**
	 * returns the projectileID that is written on a cannon sign
	 * @return
	 */
	/*
	public int getProjectileIDFromSign() {
		// g: 2 c: 123:1
		String str[] = getLineOfCannonSigns(2).split(" ");
		if (str.length >= 4 )
		{
			//123:1
			String str2[] = str[3].split(":");
			if (str.length >= 2)
			{
				return Integer.parseInt(str2[0]);
			}
		}
		return 0;
	}

	/**
	 * returns the projectileData that is written on a cannon sign
	 * @return
	 */
	/*
	public int getProjectileDataFromSign() {
		// g: 2 c: 123:1
		String str[] = getLineOfCannonSigns(2).split(" ");
		if (str.length >= 4 )
		{
			//123:1
			String str2[] = str[3].split(":");
			if (str.length >= 2)
			{
				return Integer.parseInt(str2[1]);
			}
		}
		return 0;
	}

	/**
	 * returns the horizontal angle that is written on a cannon sign
	 * @return
	 */
	/*
	public double getHorizontalAngleFromSign() {
		// 12/-2
		String str[] = getLineOfCannonSigns(2).split("/");
		if (str.length >= 2 )
		{
			return Double.parseDouble(str[0]);
		}
		return 0;
	}

	/**
	 * returns the vertical angle that is written on a cannon sign
	 * @return
	 */
	/*
	public double getVerticalAngleFromSign() {
		// 12/-2
		String str[] = getLineOfCannonSigns(2).split("/");
		if (str.length >= 2 )
		{
			return Double.parseDouble(str[1]);
		}
		return 0;
	}*/
}
