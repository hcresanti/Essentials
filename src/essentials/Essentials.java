package essentials;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import commands.Commands;
import listeners.EventListener;

public class Essentials extends JavaPlugin {
	
	@Override
	public void onEnable() {
		
		getLogger().info("Muffin's Essentials pack has been enabled!");
		Bukkit.broadcastMessage(ChatColor.GOLD + "Muffin's Essentials Pack has been enabled!");
		
		//Homes
		this.getCommand("home").setExecutor(new Commands());
		this.getCommand("sethome").setExecutor(new Commands());
		this.getCommand("delhome").setExecutor(new Commands());
		this.getCommand("homes").setExecutor(new Commands());
		
		//General
		this.getCommand("position").setExecutor(new Commands());
		this.getCommand("tpr").setExecutor(new Commands());
		this.getCommand("tpa").setExecutor(new Commands());
		
		//Listeners
		this.getServer().getPluginManager().registerEvents(new EventListener(), this);
		
		loadConfig();
		
	}
	
	@Override
	public void onDisable() {
		getLogger().info("Muffin's Essentials pack has been disabled!");
	}
	
	public void loadConfig() {
		getConfig().options().copyDefaults(true);
		saveConfig();
	}

}
