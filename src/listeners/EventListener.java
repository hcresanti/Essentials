package listeners;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.EnchantingInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class EventListener implements Listener {
	
	private final String sig = ChatColor.WHITE + "[" + ChatColor.GOLD + "MEP" + ChatColor.WHITE + "] ";
//	private static ItemStack stack = new ItemStack(Material.LAPIS_LAZULI, 20);
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		event.getPlayer().sendMessage(sig + "Muffin's Essentials Pack is online!");
	}
	
//	@EventHandler
//	public void onEnchantTableOpen(InventoryOpenEvent event) {
//		
//		if(event.getInventory() instanceof EnchantingInventory) {
//			Inventory inv = (EnchantingInventory) event.getInventory();
//			inv.setItem(1, stack);
//		}
//		
//	}
//	
//	@EventHandler
//	public void onEnchantTableClose(InventoryOpenEvent event) {
//		
//		if(event.getInventory() instanceof EnchantingInventory) {
//			stack.setAmount(0);
//		}
//		
//	}

}
