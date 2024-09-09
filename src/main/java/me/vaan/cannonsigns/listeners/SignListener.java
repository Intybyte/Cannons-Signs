package me.vaan.cannonsigns.listeners;

import at.pavlov.cannons.Cannons;
import at.pavlov.cannons.cannon.Cannon;
import at.pavlov.cannons.cannon.CannonManager;
import me.vaan.cannonsigns.utils.SignUpdateUtils;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.WallSign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.SignChangeEvent;

public class SignListener implements Listener {

    private final CannonManager cannonManager;

    public SignListener() {
        this.cannonManager = Cannons.getPlugin().getCannonManager();
    }

    @EventHandler
    public void signChange(SignChangeEvent event) {

        Block block = event.getBlock();
        if (!(block.getBlockData() instanceof WallSign wallSign)) {
            return;
        }

        //get block which is the sign attached to
        BlockFace signFace = wallSign.getFacing();
        Block cannonBlock = block.getRelative(signFace.getOppositeFace());

        //get cannon from location and creates a cannon if not existing
        Cannon cannon = cannonManager.getCannon(cannonBlock.getLocation(), event.getPlayer().getUniqueId());

        //get cannon from the sign
        Cannon cannonFromSign = CannonManager.getCannon(event.getLine(0));

        //if the sign is placed against a cannon - no problem
        //if the sign has the name of other cannon - change it
        if (cannon == null) {
            if (cannonFromSign != null) {
                //this sign is in conflict with cannons
                event.getPlayer().sendMessage(ChatColor.RED + "This sign is in conflict with cannons");
                event.setLine(0, "[Cannons]");
                event.setLine(1, "Player");
            }
        } else if (cannon.isCannonSign(block.getLocation())) {
            //if there is a cannon and the sign is mounted on the sign interface
            event.setLine(0, cannon.getSignString(0));
            event.setLine(1, cannon.getSignString(1));
            event.setLine(2, cannon.getSignString(2));
            event.setLine(3, cannon.getSignString(3));
        }
    }

    @EventHandler
    public void signPlace(BlockPlaceEvent event) {

        Block block = event.getBlockPlaced();
        Location blockLoc = block.getLocation();

        // setup a new cannon
        cannonManager.getCannon(blockLoc, event.getPlayer().getUniqueId());

        // Place wallsign
        if (block.getBlockData() instanceof WallSign wallSign) {
            // check cannon
            Location loc = event.getBlock().getRelative(wallSign.getFacing().getOppositeFace()).getLocation();
            Cannon cannon = cannonManager.getCannon(loc, event.getPlayer().getUniqueId(), true);

            if (cannon != null) {
                SignUpdateUtils.updateCannonSigns(cannon);
            }
        }
    }
}
