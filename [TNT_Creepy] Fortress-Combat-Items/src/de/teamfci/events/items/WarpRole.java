package de.teamfci.events.items;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import de.slikey.effectlib.effect.LineEffect;
import de.slikey.effectlib.util.DynamicLocation;
import de.slikey.effectlib.util.ParticleEffect;
import de.teamfci.main.main;

public class WarpRole implements Listener {
	static HashMap<Player, Location> pos = new HashMap<Player, Location>();
	public static HashMap<Player, Boolean> running = new HashMap<Player, Boolean>();
	public static HashMap<String, BukkitRunnable> hm = new HashMap<String, BukkitRunnable>();
	public static main pl;


	public WarpRole(main main) {
		WarpRole.pl = main;
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onJoin(PlayerJoinEvent ev){
		Player p = ev.getPlayer();
		running.put(p, false);
		ev.setJoinMessage(ChatColor.DARK_GRAY + "[" + ChatColor.DARK_GREEN + "+" + ChatColor.DARK_GRAY + "]" + " " + ChatColor.RESET + p.getDisplayName());
	}
	
	@EventHandler
	public void onKick(PlayerKickEvent ev){
		Player p = ev.getPlayer();
		running.remove(p);
		ev.setLeaveMessage(ChatColor.DARK_GRAY + "[" + ChatColor.DARK_RED + "Kicked" + ChatColor.DARK_GRAY + "]" + " " + p.getDisplayName());
	}
	
	@EventHandler
	public void onLeave(PlayerQuitEvent ev){
		Player p = ev.getPlayer();
		running.remove(p);
		ev.setQuitMessage(ChatColor.DARK_GRAY + "[" + ChatColor.DARK_RED + "-" + ChatColor.DARK_GRAY + "]" + " " + p.getDisplayName());
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent ev) {
		Action ac = ev.getAction();
		if (ac.equals(Action.RIGHT_CLICK_BLOCK) || ac.equals(Action.RIGHT_CLICK_AIR)) {
			final Player p = ev.getPlayer();
			String chcklore1 = "";
			try {
				chcklore1 = p.getItemInHand().getItemMeta().getLore().get(1);
			} catch (NullPointerException e) {
				
			}
			if (chcklore1.contains("Rechtsklick um zum Spawn zu gelangen")) {
				warp(p);
			}
		}
	}
	
	public static void sudo(Player p, String command) {
		p.performCommand(command);
	}
	
	public static void warp(final Player p){
		if (!running.get(p)) {
			running.put(p, true);
			hm.put(p.getName() + "role", new BukkitRunnable() {

				int high = 8;

				@Override
				public void run() {
					high--;
					if (!pos.containsKey(p)) {
						pos.put(p, p.getLocation());
						p.sendMessage("Nicht bewegen. Teleport in:");
					}
					if (p.getLocation().getX() != pos.get(p).getX()
							|| p.getLocation().getY() != pos.get(p).getY()
							|| p.getLocation().getZ() != pos.get(p).getZ()) {
						p.sendMessage("Teleportvorgang abgebrochen.");
						pos.remove(p);
						running.put(p, false);
						hm.get(p.getName() + "role").cancel();
					}
					for (int x = (-3); x < 3; x++) {
						for (int z = (-3); z < 3; z++) {
							LineEffect leff = new LineEffect(main.em);
							Location location = p.getLocation();
							location.setX(location.getX() + x);
							location.setZ(location.getZ() + z);
							DynamicLocation loc = new DynamicLocation(location);
							leff.setDynamicOrigin(loc);
							Location location2 = p.getLocation();
							location2.setY(location2.getY() + 50);
							DynamicLocation loc2 = new DynamicLocation(location2);
							leff.setDynamicTarget(loc2);
							leff.particle = ParticleEffect.SPELL_MOB;
							if (high == 7) {
								leff.color = Color.AQUA;
							}
							if (high == 6) {
								leff.color = Color.BLUE;
							}
							if (high == 5) {
								leff.color = Color.NAVY;
							}
							if (high == 4) {
								leff.color = Color.TEAL;
							}
							if (high == 3) {
								leff.color = Color.GREEN;
							}
							if (high == 2) {
								leff.color = Color.LIME;
							}
							if (high == 1) {
								leff.color = Color.WHITE;
							}
							if (high == 0) {
								leff.color = Color.RED;
							}
							leff.start();
						}
					}
					if (high == 0) {
//						Location loc = dataprovider.getSpawnLocation(dataprovider.getTeam(p), p);
//						p.teleport(loc);
						sudo(p, "spawn");
						pos.remove(p);
						running.put(p, false);
						ItemStack newroles = p.getItemInHand();
						newroles.setAmount(newroles.getAmount() - 1);
						p.setItemInHand(newroles);
						p.getWorld().playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 1F, 1F);
						p.playEffect(p.getLocation(), Effect.ENDER_SIGNAL, null);
						hm.get(p.getName() + "role").cancel();
					}
					p.sendMessage("..." + high);
				}
			});
			hm.get(p.getName() + "role").runTaskTimer(pl, 20L, 20L);
		} else {
			p.sendMessage("Warte ab bis du geportet wurdest.");
		}
	}
}
