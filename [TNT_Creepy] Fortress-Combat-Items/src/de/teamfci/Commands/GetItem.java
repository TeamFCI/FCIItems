package de.teamfci.Commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import de.teamfci.Items.Generators.CreateRandomItem;
import de.teamfci.main.main;

public class GetItem implements CommandExecutor {
	
	public boolean onCommand(CommandSender sender, Command cmd, String cmdlabel, String[] args) {
		
		if(sender instanceof Player) {
			Player p = (Player) sender;
			
			
			if(args.length == 0) {
				if(p.hasPermission("specialitems.getitem")) {
					p.sendMessage("§aRandom-Item-Drop by Mobs:");
					p.sendMessage("§7/getitem random");
					p.sendMessage("§7/getitem refresh");
				} else {
					p.sendMessage("§cDu hast keine Permission!");
					p.sendMessage("§cPermission: §aspecialitems.getitem");
				}
			}
			
			if(args.length == 1) {
				if(args[0].equalsIgnoreCase("random")){
					if(p.hasPermission("specialitems.getitem.random")) {
						ItemStack item = CreateRandomItem.generateItem(p);
						Location loc = p.getLocation();
						loc.setY(loc.getY() + 1);
						Item i = loc.getWorld().dropItemNaturally(loc, item);
						i.setCustomName(item.getItemMeta().getDisplayName());
						i.setCustomNameVisible(true);
					} else {
						p.sendMessage("§cDu hast keine Permission!");
						p.sendMessage("§cPermission: §aspecialitems.getitem.random");
					}
				}
				if(args[0].equalsIgnoreCase("refresh")){
					if(p.hasPermission("specialitems.getitem.refresh")) {
						main.refreshItems(p);
						p.sendMessage("§aItems neugeladen!");
					} else {
						p.sendMessage("§cDu hast keine Permission!");
						p.sendMessage("§cPermission: §aspecialitems.getitem.refresh");
					}
				}
			}
			
		} else {
			if(args.length == 2) {
				if(args[0].equalsIgnoreCase("random")){
					Player p = Bukkit.getPlayer(args[1]);
					if(p == null) {
						sender.sendMessage("Spieler nicht online!");
						return true;
					}
					if(p.hasPermission("specialitems.getitem.random")) {
						ItemStack item = CreateRandomItem.generateItem(p);
						Location loc = p.getLocation();
						loc.setY(loc.getY() + 1);
						Item i = loc.getWorld().dropItemNaturally(loc, item);
						i.setCustomName(item.getItemMeta().getDisplayName());
						i.setCustomNameVisible(true);
					} else {
						p.sendMessage("§cDu hast keine Permission!");
						p.sendMessage("§cPermission: §aspecialitems.getitem.random");
					}
				}
			}
		}
		
		return false;
	}
	
}
