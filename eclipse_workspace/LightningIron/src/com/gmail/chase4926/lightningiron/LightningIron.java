package com.gmail.chase4926.lightningiron;

import org.bukkit.plugin.java.JavaPlugin;

public class LightningIron extends JavaPlugin {
	public void onEnable() {
		getServer().getPluginManager().registerEvents(new LightningIronListener(this), this);
	}
}