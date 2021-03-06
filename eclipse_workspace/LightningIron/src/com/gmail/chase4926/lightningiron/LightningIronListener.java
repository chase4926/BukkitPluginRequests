package com.gmail.chase4926.lightningiron;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockRedstoneEvent;

public class LightningIronListener implements Listener {
	LightningIron plugin = null;
	
	public LightningIronListener(LightningIron p) {
		plugin = p;
	}
	
	@EventHandler
	public void strikeLightingOnPower(BlockRedstoneEvent event) {
		if (event.getOldCurrent() == 0) {
			Block block = event.getBlock();
			for (int z = 0; z < 3; z++) {
				for (int x = 0; x < 3; x++) {
					if (x != 1 || z != 1) {
						Block relative_block = block.getRelative(x - 1, 0, z - 1);
						if (relative_block.getType() == Material.IRON_BLOCK) {
							Location location = relative_block.getLocation();
							location = new Location(location.getWorld(), location.getX(), location.getY() + 1, location.getBlockZ());
							location.getWorld().strikeLightning(location);
						}
					}
				}
			}
		}
	}
}
