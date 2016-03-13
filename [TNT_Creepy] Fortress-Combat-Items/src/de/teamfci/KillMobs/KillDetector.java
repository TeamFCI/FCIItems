package de.teamfci.KillMobs;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import de.teamfci.Items.Generators.CreateRandomItem;

public class KillDetector implements Listener {
	
	@EventHandler
	public void onKill(EntityDeathEvent e) {
		if(e.getEntity().getKiller() instanceof Player) {
			Player p = (Player) e.getEntity().getKiller();
			Random r = new Random();
			int randy = r.nextInt(101);
			ArrayList<Integer> list = new ArrayList<Integer>();
			list.add(20);
			list.add(60);
			list.add(100);
			if(!list.contains(randy)) {
				return;
			}
			if(e.getEntity().getType() == EntityType.CREEPER) {
//				DropItemCreeper.creeperKilled(p, e.getEntity());
				Location loc = e.getEntity().getLocation();
				ItemStack item = CreateRandomItem.generateItem(p);
				loc.setY(loc.getY() + 1);
				loc.getWorld().dropItemNaturally(loc, item);
			}
			if(e.getEntity().getType() == EntityType.SKELETON) {
//				DropItemCreeper.creeperKilled(p, e.getEntity());
				Location loc = e.getEntity().getLocation();
				ItemStack item = CreateRandomItem.generateItem(p);
				loc.setY(loc.getY() + 1);
				loc.getWorld().dropItemNaturally(loc, item);
			}
			if(e.getEntity().getType() == EntityType.WITCH) {
//				DropItemCreeper.creeperKilled(p, e.getEntity());
				Location loc = e.getEntity().getLocation();
				ItemStack item = CreateRandomItem.generateItem(p);
				loc.setY(loc.getY() + 1);
				loc.getWorld().dropItemNaturally(loc, item);
			}
			if(e.getEntity().getType() == EntityType.SPIDER) {
//				DropItemCreeper.creeperKilled(p, e.getEntity());
				Location loc = e.getEntity().getLocation();
				ItemStack item = CreateRandomItem.generateItem(p);
				loc.setY(loc.getY() + 1);
				loc.getWorld().dropItemNaturally(loc, item);
			}
			if(e.getEntity().getType() == EntityType.ENDERMAN) {
//				DropItemCreeper.creeperKilled(p, e.getEntity());
				Location loc = e.getEntity().getLocation();
				ItemStack item = CreateRandomItem.generateItem(p);
				loc.setY(loc.getY() + 1);
				loc.getWorld().dropItemNaturally(loc, item);
			}
			if(e.getEntity().getType() == EntityType.ZOMBIE) {
//				DropItemCreeper.creeperKilled(p, e.getEntity());
				Location loc = e.getEntity().getLocation();
				ItemStack item = CreateRandomItem.generateItem(p);
				loc.setY(loc.getY() + 1);
				loc.getWorld().dropItemNaturally(loc, item);
			}
		}
		
	}
	
}
