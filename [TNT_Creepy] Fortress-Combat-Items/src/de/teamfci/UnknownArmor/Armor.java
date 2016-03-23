package de.teamfci.UnknownArmor;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Armor implements Listener {
	ArrayList<Player> players = new ArrayList<Player>();
	
	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		//Komplette Set == +4Herzen
		//Komplette Set == /
		Player p = e.getPlayer();
		ItemStack helmet = p.getInventory().getHelmet();
		ItemStack chestplate = p.getInventory().getChestplate();
		ItemStack leggings = p.getInventory().getLeggings();
		ItemStack boots = p.getInventory().getBoots();
		
		ItemMeta meta = helmet.getItemMeta();
		String name = meta.getLore().get(3);
		if(name.equals("&5&l???")) {
			p.sendMessage("§a1");
		} else {
			p.sendMessage("1");
			p.sendMessage(name);
			if(players.contains(p)) {
				players.remove(p);
				p.sendMessage("1.1");
			}
		}
		meta = chestplate.getItemMeta();
		name = meta.getLore().get(3);
		if(name.equals("&5&l???")) {
			p.sendMessage("§a2");
		} else {
			p.sendMessage("2");
			if(players.contains(p)) {
				players.remove(p);
				p.sendMessage("2.1");
			}
		}
		meta = leggings.getItemMeta();
		name = meta.getLore().get(3);
		if(name.equals("&5&l???")) {
			p.sendMessage("§a3");
		} else {
			p.sendMessage("3");
			if(players.contains(p)) {
				players.remove(p);
				p.sendMessage("3.1");
			}
		}
		meta = boots.getItemMeta();
		name = meta.getLore().get(3);
		if(name.equals("&5&l???")) {
			p.sendMessage("5");
			if(!players.contains(p)) {
				players.add(p);
				p.setMaxHealth(p.getMaxHealth() + 8.0);
				p.sendMessage("5.1");
			}
		} else {
			p.sendMessage("4");
			if(players.contains(p)) {
				players.remove(p);
				p.sendMessage("4.1");
			}
		}
		
	}
	
}
