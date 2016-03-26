package de.teamfci.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class WarpRoleListener implements Listener {

	@EventHandler
	public void onInteract(PlayerInteractEvent ev) {
		Action ac = ev.getAction();
		if (ac.equals(Action.RIGHT_CLICK_AIR)) {
			Player p = ev.getPlayer();
			if (p.getItemInHand().getItemMeta().getLore().get(1).contains("Rechtsklick um zum Spawn zu gelangen")) {
				p.performCommand("spawn");
				ItemStack newroles = p.getItemInHand();
				newroles.setAmount(newroles.getAmount() - 1);
				p.setItemInHand(newroles);
			}
		}
//		if (ac.equals(Action.RIGHT_CLICK_BLOCK)) {
//			Player p = ev.getPlayer();
//			if (p.getItemInHand().getItemMeta().getLore().get(1).contains("Rechtsklick um zum Spawn zu gelangen")) {
//				p.performCommand("spawn");
//				ItemStack newroles = p.getItemInHand();
//				newroles.setAmount(newroles.getAmount() - 1);
//				p.setItemInHand(newroles);
//			}
//		}
	}
}
