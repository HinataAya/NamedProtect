package io.github.HinataAya.NamedProtect;

import java.io.File;
import java.io.InputStream;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class ConfigLoader {
	
	private NamedProtect plugin;
	private String fileURL;
	
	private File configFile = null;
	private FileConfiguration fileConfiguration = null;
	
	public ConfigLoader(NamedProtect _plugin, String _fileURL){
		
		if(_plugin == null)
			throw new IllegalArgumentException("plugin is not exist.");
		
		plugin = _plugin;
		fileURL = _fileURL;
		File dataFolder = plugin.getDataFolder();
		if(dataFolder == null)
			throw new IllegalStateException();
		configFile = new File(plugin.getDataFolder(), fileURL);
	}
	
	public void reloadConfig(){
		fileConfiguration = YamlConfiguration.loadConfiguration(configFile);
		
		//load default config file.
		InputStream defConfigStream = plugin.getResource(fileURL);
		if(defConfigStream != null){
			YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
			fileConfiguration.setDefaults(defConfig);
		}
	}
	
	public FileConfiguration getConfig() {
        if (fileConfiguration == null) {
            this.reloadConfig();
        }
        return fileConfiguration;
    }
    
    public void saveDefaultConfig() {
        if (!configFile.exists()) {            
            this.plugin.saveResource(fileURL, false);
        }
    }
	
}
