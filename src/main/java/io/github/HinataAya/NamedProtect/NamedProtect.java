package io.github.HinataAya.NamedProtect;

import org.bukkit.plugin.java.JavaPlugin;

public final class NamedProtect extends JavaPlugin {
	
	@Override
	public void onEnable(){
		
		getLogger().info("[NP] NamedProtect is Enable.");
	}
	
	@Override
	public void onDisable(){
		getLogger().info("[NP] NamedProtect is Disable.");
	}

}