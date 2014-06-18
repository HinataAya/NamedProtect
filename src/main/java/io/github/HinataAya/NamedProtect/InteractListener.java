package io.github.HinataAya.NamedProtect;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public final class InteractListener implements Listener {
	
	private NamedProtect plugin;
	
	public InteractListener(NamedProtect _plugin){
		plugin = _plugin;
	}
	
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onName(PlayerInteractEntityEvent event){
		
		//Return when not NAME_TAG on hand.
		if(event.getPlayer().getItemInHand().getType() != Material.NAME_TAG){
			event.getPlayer().sendMessage("[NP] Not Name_Tag.");
			return;
		}
		
		//Return when entity not allow to protect.
		if(!isProtectList(event.getRightClicked().getType())){
			event.getPlayer().sendMessage("[NP] this entiyt is not allow to protect.");
			return;
		}
		
		//Cancel event when Entity is protected.
		String customName = ((LivingEntity)event.getRightClicked()).getCustomName();
		if(customName != null){
			if(!isOwner(customName, event.getPlayer().getName())){
				event.getPlayer().sendMessage("[NP] you can't rename entity owned by other.");
				event.setCancelled(true);
				return;
			}
		}
		
		//Cancel event when tag not own name.
		String displayName = event.getPlayer().getItemInHand().getItemMeta().getDisplayName();
		if(!isOwner(displayName, event.getPlayer().getName())){
			event.getPlayer().sendMessage("[NP] you can't rename entity with other's name.");
			event.setCancelled(true);
			return;
		}
	}
	
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onFeed(PlayerInteractEntityEvent event){

		//Return when entity not allow to protect.
		if(!isProtectList(event.getRightClicked().getType())){
			event.getPlayer().sendMessage("[NP] this entiyt is not allow to protect.");
			return;
		}
		
		//Cancel event when Entity is protected.
		String customName = ((LivingEntity)event.getRightClicked()).getCustomName();
		if(customName != null){
			if(!isOwner(customName, event.getPlayer().getName())){
				event.getPlayer().sendMessage("[NP] you can't rename entity owned by other.");
				event.setCancelled(true);
				return;
			}
		}
		
	}
	
	private Boolean isOwner(String displayName, String playerName){
		
		//Return false if no NP tag.
		if(!(displayName.matches("(.*\\["+plugin.publicKeyChar+".*\\])|(.*\\["+plugin.privateKeyChar+".*\\])"))){
			return true;
		}
		
		String[] split = displayName.split("(.*\\[)|(\\])");
		String targetName = split[1];
		
		targetName = targetName.replace(plugin.privateKeyChar, "");
		targetName = targetName.replace(plugin.publicKeyChar, "");
		
		plugin.getLogger().info("[test] targetName="+targetName+", playerName="+playerName);
		
		if(targetName.equalsIgnoreCase(playerName)){
			return true;
		}
		
		return false;
	}
	
	private Boolean isProtectList(EntityType target){
		for(EntityType m : plugin.publicList){
			if(target.equals(m)){
				return true;
			}
		}
		for(EntityType m : plugin.privateList){
			if(target.equals(m)){
				return true;
			}
		}
		return false;
	}
	
}
