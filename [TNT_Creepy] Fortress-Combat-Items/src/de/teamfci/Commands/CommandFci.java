package de.teamfci.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandFci implements CommandExecutor {
	
	public boolean onCommand(CommandSender sender, Command cmd, String cmdlabel, String[] args) {
		
		if(sender instanceof Player) {
			Player p = (Player) sender;
			
			if(args.length == 0) {
				if(p.hasPermission("fci.fci")) {
					p.sendMessage("§a§l *-*-*-*-*-*-*-*-*-*-*-*");
					p.sendMessage("§bPlugin von §aFilip Zocktan & HappyHappyBoy");
					p.sendMessage("§bAuftrag von §aTNT_Creepy");
					p.sendMessage("§bPlugin erstmals genutzt auf");
					p.sendMessage("§bdiesem Server: §aSERVER-IP");
					p.sendMessage("§bPlugin Version: §a1.0.0");
					p.sendMessage("§a§l *-*-*-*-*-*-*-*-*-*-*-*");
				} else {
					p.sendMessage("§cDu hast keine Permission!");
					p.sendMessage("§cPermission: §afci.fci");
				}
			}
			
		} else {
			sender.sendMessage("NUR FÜR SPIELER!!!");
		}
		
		return false;
	}
	
}
