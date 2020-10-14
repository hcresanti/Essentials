package commands;

import java.util.HashMap;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import essentials.Essentials;

public class Commands implements CommandExecutor {
	
	private Essentials plugin = Essentials.getPlugin(Essentials.class);
	private static HashMap<Player, Player> requests = new HashMap<Player, Player>();
	private final String sig = ChatColor.WHITE + "[" + ChatColor.GOLD + "MEP" + ChatColor.WHITE + "] ";
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String commandRaw, String[] args) {
		
		if (sender instanceof Player) {
			Player player = (Player) sender;
			
			//HOME
			if (command.getName().equalsIgnoreCase("home")) {
				home(player, args);
			
			//SETHOME
			} else if (command.getName().equalsIgnoreCase("sethome")) {
				setHome(player, args);
				
			//DELHOME
			} else if (command.getName().equalsIgnoreCase("delhome")) {
				delHome(player, args);
				
			//HOMES
			} else if (command.getName().equalsIgnoreCase("homes")) {
				homes(player);
			
			//POSITION
			} else if (command.getName().equalsIgnoreCase("position")) {
				position(player, args);
			
			//TPR
			} else if (command.getName().equalsIgnoreCase("tpr")) {
				tpr(player, args);
		
			//TPA
			} else if (command.getName().equalsIgnoreCase("tpa")) {
				tpa(player);
			}
			
			return true;
		
		} else {
			sender.sendMessage(sig + ChatColor.RED + "Must be a player to use this!");
			return false;
		}
	}


	//Teleports player to valid home
	private void home(Player player, String[] args) {
		
		if (plugin.getConfig().contains("Users." + player.getUniqueId() + ".Homes." + args[0].toLowerCase())) {
			String home = args[0].toLowerCase();
			
			String world = plugin.getConfig().getString("Users." + player.getUniqueId() + ".Homes." + home + ".World");
			int xCoord = plugin.getConfig().getInt("Users." + player.getUniqueId() + ".Homes." + home + ".X");
			int yCoord = plugin.getConfig().getInt("Users." + player.getUniqueId() + ".Homes." + home + ".Y");
			int zCoord = plugin.getConfig().getInt("Users." + player.getUniqueId() + ".Homes." + home + ".Z");
			plugin.saveConfig();
			
			Location tp = new Location(Bukkit.getWorld(world), xCoord + 0.5, yCoord, zCoord + 0.5);
			player.teleport(tp);
			player.sendMessage(sig + ChatColor.GREEN + "Whoosh!");
		
		} else {
			player.sendMessage(sig + ChatColor.RED + "No such home found!");
		}				
		
	}
	
	//Adds home to player's home list
	private void setHome(Player player, String[] args) {
		if (args.length > 0) {
			String home = args[0].toLowerCase();
			
			plugin.getConfig().set("Users." + player.getUniqueId() + ".Homes." + home + ".World", player.getLocation().getWorld().getName());
			plugin.getConfig().set("Users." + player.getUniqueId() + ".Homes." + home + ".X", player.getLocation().getBlockX());
			plugin.getConfig().set("Users." + player.getUniqueId() + ".Homes." + home + ".Y", player.getLocation().getBlockY());
			plugin.getConfig().set("Users." + player.getUniqueId() + ".Homes." + home + ".Z", player.getLocation().getBlockZ());
			plugin.saveConfig();
			
			player.sendMessage(sig + ChatColor.WHITE + args[0] + ChatColor.GREEN + " has been added to homes list!");
			
		} else {
			player.sendMessage(sig + ChatColor.RED + "Please enter a valid home name!");
		}
	}
	
	//Deletes a valid home
	private void delHome(Player player, String[] args) {
		if (args.length > 0) {
			String home = args[0].toLowerCase();
			
			plugin.getConfig().set("Users." + player.getUniqueId() + ".Homes." + home, null);
			plugin.saveConfig();
			
			player.sendMessage(sig + ChatColor.WHITE + args[0] + ChatColor.GREEN + " has been removed!");
		}
	}
	
	//Lists every home a player has
	private void homes(Player player) {
		Set<String> homes = plugin.getConfig().getConfigurationSection("Users." + player.getUniqueId() + ".Homes")
				.getKeys(false);
		plugin.saveConfig();

		player.sendMessage(sig + ChatColor.WHITE + "Homes: " + homes.toString());
	}
	
	//Handles position requests
	@SuppressWarnings("deprecation")
	private void position(Player player, String[] args) {
		if(args.length > 0) {
			Bukkit.getPlayer(args[0]).sendMessage(sig + ChatColor.GREEN + player.getDisplayName() + "'s Position: " + ChatColor.WHITE + "(X/Z)  " + 
			player.getLocation().getBlockX() + " / " + player.getLocation().getBlockZ());
			
		} else {			
			Bukkit.broadcastMessage(sig + ChatColor.GREEN + player.getDisplayName() + "'s Position: " + ChatColor.WHITE + "(X/Z)  " + 
			player.getLocation().getBlockX() + " / " + player.getLocation().getBlockZ());
		}
	}
	
	//Sends a teleport request to specified player
	@SuppressWarnings("deprecation")
	private void tpr(Player sender, String[] args) {
		
		//If the target player is online
		if (Bukkit.getPlayer(args[0]) != null && Bukkit.getPlayer(args[0]).isOnline()) {
			Player target= Bukkit.getPlayer(args[0]);
			
			//Puts tpr in requests Map
			requests.put(target, sender);
			target.sendMessage(sig + ChatColor.GREEN + sender.getDisplayName() + " is requesting to teleport to you! /tpa to accept");
			sender.sendMessage(sig + ChatColor.GREEN + "Teleport request send to " + target.getDisplayName() + "!");
			
		} else { 
			sender.sendMessage(sig + ChatColor.RED + "Player not found!");
		}
		
	}
	
	//Accepts last teleport request sent
	private void tpa(Player target) {
		if (requests.containsKey(target)) {
			Player sender = requests.get(target);
			
			sender.teleport(target.getLocation());
			target.sendMessage(sig + ChatColor.GREEN + "Accepted!");
			sender.sendMessage(sig + ChatColor.GREEN + "Whoosh!");
			
			requests.remove(target);
		} else {
			target.sendMessage(sig + ChatColor.RED + "You don't have any pending teleport requests!");
		}
	}
	
}
