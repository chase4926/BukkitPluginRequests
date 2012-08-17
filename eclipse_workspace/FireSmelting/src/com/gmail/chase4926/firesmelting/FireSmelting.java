package com.gmail.chase4926.firesmelting;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;


public class FireSmelting extends JavaPlugin {
	boolean enabled;
	
	public void onEnable() {
		this.saveDefaultConfig();
		
		enabled = getConfig().getBoolean("enabled");
		
		getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			public void run() {
				if (enabled == true) {
					for (World world : getServer().getWorlds()) {
						for (Entity entity : world.getEntities()) {
							if (entity.getType() == EntityType.DROPPED_ITEM) {
								ItemStack item = ((Item) entity).getItemStack();
								Location location = entity.getLocation();
								if (location.getBlock().getRelative(0, -1, 0).getType() == Material.IRON_BLOCK && location.getBlock().getRelative(0, -2, 0).getType() == Material.FIRE && (item.getType() == Material.IRON_ORE || item.getType() == Material.GOLD_ORE)) {
									int amount = item.getAmount();
									if (item.getType() == Material.IRON_ORE) {
										spawnItem(location, Material.IRON_INGOT, amount);
										entity.remove();
									} else if (item.getType() == Material.GOLD_ORE) {
										spawnItem(location, Material.GOLD_INGOT, amount);
										entity.remove();
									}
								}
							}
						}
					}
				}
			}
		}, 0L, 10L);
		
		getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			public void run() {
				if (enabled == true) {
					for (Player player :getServer().getOnlinePlayers()) {
						Location location = player.getLocation();
						if (location.getBlock().getRelative(0, -1, 0).getType() == Material.IRON_BLOCK && location.getBlock().getRelative(0, -2, 0).getType() == Material.FIRE) {
							player.damage(1);
							player.setFireTicks(20);
						}
					}
				}
			}
		}, 0L, 10L);
	}
	
	public void onDisable() {
		this.saveConfig();
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("firesmelting")) {
			if (enabled == true) {
				enabled = false;
				this.getConfig().set("enabled", false);
				sender.sendMessage(ChatColor.YELLOW+"FireSmelting turned off.");
			} else {
				enabled = true;
				this.getConfig().set("enabled", true);
				sender.sendMessage(ChatColor.YELLOW+"FireSmelting turned on.");
			}
			return true;
		}
		return false;
	}
	
	public void spawnItem(Location location, Material material, int amount) {
		ItemStack item = new ItemStack(material, amount);
		location.getWorld().dropItem(location, item);
	}
}
