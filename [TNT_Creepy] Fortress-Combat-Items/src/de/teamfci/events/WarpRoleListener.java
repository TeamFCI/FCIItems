package de.teamfci.events;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import de.teamfci.main.main;

public class WarpRoleListener implements Listener {

	HashMap<String, BukkitRunnable> hm = new HashMap<String, BukkitRunnable>();
	public static main pl;
	public WarpRoleListener(main main) {
		this.pl = main;
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent ev) {
		Entity e;
		Action ac = ev.getAction();
		if (ac.equals(Action.RIGHT_CLICK_AIR)) {
			Player p = ev.getPlayer();
			if (p.getItemInHand().getItemMeta().getLore().get(1).contains("Rechtsklick um zum Spawn zu gelangen")) {
				p.performCommand("spawn");
				ItemStack newroles = p.getItemInHand();
				newroles.setAmount(newroles.getAmount() - 1);
				p.setItemInHand(newroles);
			}
		} else {
			if (ac.equals(Action.RIGHT_CLICK_BLOCK)) {
				Player p = ev.getPlayer();
				if (p.getItemInHand().getItemMeta().getLore().get(1).contains("Rechtsklick um zum Spawn zu gelangen")) {
					p.performCommand("spawn");
					ItemStack newroles = p.getItemInHand();
					newroles.setAmount(newroles.getAmount() - 1);
					p.setItemInHand(newroles);
				}
				if(p.getItemInHand().getItemMeta().getLore().get(2).equalsIgnoreCase("morsch,zerbrechlich")){
					Location loc = p.getLocation();
					final ArmorStand a = loc.getWorld().spawn(loc, ArmorStand.class);
					a.getLocation().setYaw(p.getLocation().getYaw());
					a.getLocation().setPitch(p.getLocation().getPitch());
					Bukkit.getScheduler().scheduleSyncRepeatingTask(pl, new Runnable() {
						
						@Override
						public void run() {
							
							for (Entity ent : a.getNearbyEntities(0.1, 0.1, 0.1)) {
								if(ent instanceof Player){
									Player p = (Player) ent;
									p.setFireTicks(100);
									//STOP! WEITER OBEN!
									break;
								}
							}
							a.getLocation().getDirection().multiply(0.1);
						}
					}, 0, 1);
				}
			}
		}
	}
}
