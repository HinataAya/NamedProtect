package io.github.HinataAya.NamedProtect;

import java.util.ArrayList;

import org.bukkit.entity.EntityType;
import org.bukkit.plugin.java.JavaPlugin;

public final class NamedProtect extends JavaPlugin {
	
	public static ConfigLoader config;
	public static ConfigLoader localization;
	public static ArrayList<EntityType> publicList = null;
	public static ArrayList<EntityType> privateList = null;
	public static String publicKeyChar = null;
	public static String privateKeyChar = null;
	
	
	@Override
	public void onEnable(){
		
		configLoad();
		
		this.getServer().getPluginManager().registerEvents(new InteractListener(this), this);
		
		getLogger().info("[NP] NamedProtect is Enable.");
	}
	
	@Override
	public void onDisable(){
		getLogger().info("[NP] NamedProtect is Disable.");
	}
	
	public void configLoad(){
		config = new ConfigLoader(this, "config.yml");
		config.saveDefaultConfig();
		
		publicList = new ArrayList<EntityType>();
		privateList = new ArrayList<EntityType>();
		
		for(String s : config.getConfig().getStringList("Public.Entities")){
				publicList.add(EntityType.valueOf(s));
		}
		for(String s : config.getConfig().getStringList("Private.Entities")){
				privateList.add(EntityType.valueOf(s));
		}
		
		publicKeyChar = config.getConfig().getString("Public.KeyChar");
		privateKeyChar = config.getConfig().getString("Private.KeyChar");
		
		localization = new ConfigLoader(this, config.getConfig().getString("Localization")+".yml");
		localization.saveDefaultConfig();
	}

}
