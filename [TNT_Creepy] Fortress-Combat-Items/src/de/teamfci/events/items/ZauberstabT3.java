package de.teamfci.events.items;

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

public class ZauberstabT3 implements Listener {
	public static HashMap<String, BukkitRunnable> hm = new HashMap<String, BukkitRunnable>();
	private main pl;

	public ZauberstabT3(main pl) {

		this.pl = pl;

	}

	@EventHandler
	public void onInteract(PlayerInteractEvent ev) {

		final Player p = ev.getPlayer();
		Action ac = ev.getAction();
		if (p.getItemInHand().getItemMeta().hasLore()) {
				if (p.getItemInHand().getItemMeta().getLore().get(2).equalsIgnoreCase("§8Federnd,dick und elastisch")) {
					if (ac.equals(Action.RIGHT_CLICK_AIR) || ac.equals(Action.RIGHT_CLICK_BLOCK)) {
						if (!p.isSneaking()) {
							Right(p);
						}
						if (p.isSneaking()) {
							ShiftRight(p);
						}
					}
					if (ac.equals(Action.LEFT_CLICK_AIR) || ac.equals(Action.LEFT_CLICK_BLOCK)) {
						if (!p.isSneaking()) {
							Left(p);
						}
						if (p.isSneaking()) {
							ShiftLeft(p);
						}
					}
				}
		}
	}

	private void Left(Player p) {
		// ARI: Schutzschild
		p.sendMessage("Comming Sohn");
	}

	private void Right(final Player p) {

		if (p.getItemInHand().getItemMeta().hasLore()) {
					if (hm.containsKey(p.getName() + "timer")) {
						p.sendMessage("§cWarte bis dein MagicArmor jemanden getroffen hat!");
						return;
					}
					Location loc = p.getLocation();
					final ArmorStand a = loc.getWorld().spawn(loc, ArmorStand.class);
					a.setVisible(false);
					a.setSmall(true);
					for (Player target : Bukkit.getOnlinePlayers()) {
						target.playSound(p.getLocation(), Sound.WITHER_SHOOT, 1F, 1F);
					}
					ItemStack is = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
					SkullMeta meta = (SkullMeta) is.getItemMeta();
					meta.setOwner(p.getName());
					is.setItemMeta(meta);
					a.setHelmet(is);

					a.getLocation().setYaw(p.getLocation().getYaw());
					a.getLocation().setPitch(p.getLocation().getPitch());
					hm.put(p.getName() + "timer", new BukkitRunnable() {

						@Override
						public void run() {
							a.setVelocity(a.getLocation().getDirection().multiply(1.6D));
							for (Player target : Bukkit.getOnlinePlayers()) {
								target.getWorld().playEffect(a.getLocation(), Effect.FLYING_GLYPH, 10);
								target.getWorld().playEffect(a.getLocation(), Effect.FLYING_GLYPH, 10);
							}
							for (Entity ent : a.getNearbyEntities(1, 1, 1)) {
								if (ent instanceof Monster || ent instanceof Ghast || ent instanceof Slime
										|| ent instanceof Bat || ent instanceof Player && ent != p) {
									ent.setFireTicks(100);
									ent.setVelocity(a.getLocation().getDirection().multiply(0.3D).setY(0.3D));
									p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1F, 1F);
									// STOP! WEITER OBEN!
									a.remove();
									hm.get(p.getName() + "timer").cancel();
									hm.remove(p.getName() + "timer");
									hm.get(p.getName() + "later").cancel();
									hm.remove(p.getName() + "later");
								}
							}
						}

					});
					hm.get(p.getName() + "timer").runTaskTimer(pl, 0L, 0L);

					hm.put(p.getName() + "later", new BukkitRunnable() {

						@Override
						public void run() {
							a.remove();
							hm.get(p.getName() + "timer").cancel();
							hm.remove(p.getName() + "timer");
							hm.get(p.getName() + "later").cancel();
							hm.remove(p.getName() + "later");
						}

					});
					hm.get(p.getName() + "later").runTaskLater(pl, 3 * 20L);
			
		}

	}

	private void ShiftLeft(Player p) {
		// TODO Auto-generated method stub

	}

	private void ShiftRight(final Player p) {
			if (hm.containsKey(p.getName() + "timer")) {
				p.sendMessage("§cWarte bis dein MagicArmor jemanden getroffen hat!");
				return;
			}
			Location loc = p.getLocation();
			final ArmorStand a = loc.getWorld().spawn(loc, ArmorStand.class);
			a.setVisible(false);
			a.setSmall(true);
			for (Player target : Bukkit.getOnlinePlayers()) {
				target.playSound(p.getLocation(), Sound.WITHER_SHOOT, 1F, 1F);
			}
			a.getLocation().setYaw(p.getLocation().getYaw());
			a.getLocation().setPitch(p.getLocation().getPitch());
			hm.put(p.getName() + "timer", new BukkitRunnable() {
				Location lastlocation;
				@Override
				public void run() {
					a.setVelocity(a.getLocation().getDirection().multiply(1.6D));
					LineEffect leff = new LineEffect(pl.em);
					try {
						leff.setDynamicOrigin(new DynamicLocation(lastlocation) );
					} catch (NullPointerException e) {
						leff.setDynamicOrigin(new DynamicLocation(p));
					}
					lastlocation = a.getLocation();
					leff.setDynamicTarget(new DynamicLocation(a));
					leff.particle = ParticleEffect.SPELL_MOB;
					leff.color = Color.RED;
					leff.particleCount = leff.particleCount/2;
					leff.start();
					for (Player target : Bukkit.getOnlinePlayers()) {
						target.getWorld().playEffect(a.getLocation(), Effect.FLYING_GLYPH, 10);
						target.getWorld().playEffect(a.getLocation(), Effect.FLYING_GLYPH, 10);
					}
					for (Entity ent : a.getNearbyEntities(1, 1, 1)) {
						if (ent instanceof Player && ent != p) {
							Player pHit = (Player) ent;
							ItemStack isInHand = pHit.getItemInHand();
							pHit.getWorld().dropItem(pHit.getLocation(), isInHand);
							pHit.setItemInHand(new ItemStack(Material.AIR));
							ent.setVelocity(a.getLocation().getDirection().multiply(0.3D).setY(0.3D));
							p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1F, 1F);
							// STOP! WEITER OBEN!
							a.remove();
							hm.get(p.getName() + "timer").cancel();
							hm.remove(p.getName() + "timer");
							hm.get(p.getName() + "later").cancel();
							hm.remove(p.getName() + "later");
						}
					}
				}

			});
			hm.get(p.getName() + "timer").runTaskTimer(pl, 0L, 0L);

			hm.put(p.getName() + "later", new BukkitRunnable() {

				@Override
				public void run() {
					a.remove();
					hm.get(p.getName() + "timer").cancel();
					hm.remove(p.getName() + "timer");
					hm.get(p.getName() + "later").cancel();
					hm.remove(p.getName() + "later");
				}

			});
			hm.get(p.getName() + "later").runTaskLater(pl, 3 * 20L);

		
	}
}