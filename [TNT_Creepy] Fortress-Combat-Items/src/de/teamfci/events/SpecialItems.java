package de.teamfci.events;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Bat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Ghast;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.entity.Slime;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitRunnable;

import de.slikey.effectlib.effect.LineEffect;
import de.slikey.effectlib.util.DynamicLocation;
import de.slikey.effectlib.util.ParticleEffect;
import de.teamfci.main.main;

public class SpecialItems implements Listener {
	HashMap<String, BukkitRunnable> hm = new HashMap<String, BukkitRunnable>();
	HashMap<Player, Location> pos = new HashMap<Player, Location>();
	public static main pl;
	public SpecialItems(main main) {
		SpecialItems.pl = main;
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent ev) {
		Action ac = ev.getAction();
		if (ac.equals(Action.RIGHT_CLICK_BLOCK) || ac.equals(Action.RIGHT_CLICK_AIR)) {
			final Player p = ev.getPlayer();
			if (p.getItemInHand().getItemMeta().getLore().get(1).contains("Rechtsklick um zum Spawn zu gelangen")) {
				
				hm.put(p.getName()+"role", new BukkitRunnable() {
					int high = 7;
					@Override
					public void run() {
						high--;
						if(!(high==0)){
							if(!pos.containsKey(p)){
								pos.put(p, p.getLocation());
								p.sendMessage("Nicht bewegen. Teleport in:");
							}
							if(!p.getLocation().equals(pos.get(p))){
								p.sendMessage("Teleportvorgand abgebrochen. Rolle wird nicht zurückerstattet.");
							}
							for (int x = (-3); x < 3; x++) {
								for (int z = (-3); z < 3; z++) {
									
									LineEffect leff = new LineEffect(pl.em);
									Location location = p.getLocation();
									location.setX(location.getX() + x);
									location.setZ(location.getZ() + z);
									DynamicLocation loc = new DynamicLocation(location);
									leff.setDynamicOrigin(loc);
									Location location2 = p.getLocation();
									location2.setY(location2.getY() + 20);
									DynamicLocation loc2 = new DynamicLocation(location2);
									leff.setDynamicTarget(loc2);
									leff.particle = ParticleEffect.FIREWORKS_SPARK;
									if(high == 7){
										leff.color = Color.AQUA;
									}
									if(high == 6){
										leff.color = Color.BLUE;
									}
									if(high == 5){
										leff.color = Color.NAVY;
									}
									if(high == 4){
										leff.color = Color.TEAL;
									}
									if(high == 3){
										leff.color = Color.GREEN;
									}
									if(high == 2){
										leff.color = Color.LIME;
									}
									if(high == 1){
										leff.color = Color.WHITE;
									}
									leff.start();
								}
							}
							p.sendMessage("..." + high);
							
						} else {
							sudo(p, "spawn");
							pos.remove(p);
							return;
						}
						
						
						
						
					}
				});
				hm.get(p.getName()+"role").runTaskTimer(pl, 20L,20L);
				

				
				
				
//				Location loc = dataprovider.getSpawnLocation(dataprovider.getTeam(p), p);
//				p.teleport(loc);
				ItemStack newroles = p.getItemInHand();
				newroles.setAmount(newroles.getAmount() - 1);
				p.setItemInHand(newroles);
			}
			if(p.getItemInHand().getItemMeta().getLore().get(2).equalsIgnoreCase("§8morsch,zerbrechlich")){
				if(hm.containsKey(p.getName()+"timer")) {
					p.sendMessage("§cWarte bis dein MagicArmor jemanden getroffen hat!");
					return;
				}
				Location loc = p.getLocation();
				final ArmorStand a = loc.getWorld().spawn(loc, ArmorStand.class);
				a.setVisible(false);
				a.setSmall(true);
				for(Player target : Bukkit.getOnlinePlayers()) {
					target.playSound(p.getLocation(), Sound.WITHER_SHOOT, 1F, 1F);
				}
				ItemStack is = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
				SkullMeta meta = (SkullMeta) is.getItemMeta();
				meta.setOwner(p.getName());
				is.setItemMeta(meta);
				a.setHelmet(is);
				
				a.getLocation().setYaw(p.getLocation().getYaw());
				a.getLocation().setPitch(p.getLocation().getPitch());
				hm.put(p.getName()+"timer", new BukkitRunnable() {

					@Override
					public void run() {
						a.setVelocity(a.getLocation().getDirection().multiply(1.6D));
						for(Player target : Bukkit.getOnlinePlayers()) {
							target.getWorld().playEffect(a.getLocation(), Effect.FLYING_GLYPH, 10);
							target.getWorld().playEffect(a.getLocation(), Effect.FLYING_GLYPH, 10);
						}
						for (Entity ent : a.getNearbyEntities(1, 1, 1)) {
							if(ent instanceof Monster || ent instanceof Ghast || ent instanceof Slime || ent instanceof Bat || ent instanceof Player && ent != p) {
								ent.setFireTicks(100);
								ent.setVelocity(a.getLocation().getDirection().multiply(0.3D).setY(0.3D));
								p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1F, 1F);
								//STOP! WEITER OBEN!
								a.remove();
								hm.get(p.getName()+"timer").cancel();
								hm.remove(p.getName()+"timer");
								hm.get(p.getName()+"later").cancel();
								hm.remove(p.getName()+"later");
							}
						}
					}
					
				});
				hm.get(p.getName()+"timer").runTaskTimer(pl, 0L, 0L);
				
				hm.put(p.getName()+"later", new BukkitRunnable() {

					@Override
					public void run() {
						a.remove();
						hm.get(p.getName()+"timer").cancel();
						hm.remove(p.getName()+"timer");
						hm.get(p.getName()+"later").cancel();
						hm.remove(p.getName()+"later");
					}
					
				});
				hm.get(p.getName()+"later").runTaskLater(pl, 3*20L);
			}
		}
	}
	
	public static void sudo(Player p, String command) {
		p.performCommand(command);
	}
}
