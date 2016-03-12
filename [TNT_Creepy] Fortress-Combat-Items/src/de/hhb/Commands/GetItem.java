package de.hhb.Commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import de.hhb.Items.Generators.CreateRandomItem;
import de.hhb.main.main;

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
						loc.getWorld().dropItemNaturally(loc, item);
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
			sender.sendMessage("NUR FÜR SPIELER!!!");
		}
		
		return false;
	}
	
}
