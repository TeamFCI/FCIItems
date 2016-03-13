package de.teamfci.UnknownArmor;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Armor implements Listener {
	ArrayList<ItemStack> unknownArmor = new ArrayList<ItemStack>();
	
	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		//Komplette Set == +4Herzen
		//Komplette Set == /
		if(unknownArmor.isEmpty()) {
			ItemStack helmet = new ItemStack(Material.DIAMOND_HELMET);
			
			ItemMeta meta = helmet.getItemMeta();
			unknownArmor.add(helmet);
		}
	}
	
}
