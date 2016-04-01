package de.teamfci.main;

import java.io.File;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import de.slikey.effectlib.EffectManager;
import de.teamfci.Commands.CommandFci;
import de.teamfci.Commands.GetItem;
import de.teamfci.KillMobs.KillDetector;
import de.teamfci.events.EventPluginEnableEvent;
import de.teamfci.events.items.WarpRole;
import de.teamfci.events.items.ZauberstabT1;
import de.teamfci.events.items.ZauberstabT2;

public class main extends JavaPlugin {
	public static EffectManager em;
	public static boolean isLoading = false;
	public static ArrayList<String> normalitems = new ArrayList<String>();
	public static ArrayList<String> filenames = new ArrayList<String>();
	
	public void onEnable() {
		loadItems();
		em = new EffectManager(this);
		WarpRole.pl = this;
		PluginManager pm = this.getServer().getPluginManager();
		pm.registerEvents(new EventPluginEnableEvent(), this);
		pm.registerEvents(new KillDetector(), this);
		pm.registerEvents(new WarpRole(this), this);
		pm.registerEvents(new ZauberstabT1(this), this);
		pm.registerEvents(new ZauberstabT2(this), this);
		this.getCommand("getitem").setExecutor(new GetItem());
		this.getCommand("fci").setExecutor(new CommandFci());
		WarpRole.running.clear();
		for (Player p : Bukkit.getOnlinePlayers()) {
			WarpRole.running.put(p, false);
		}
	}
	
	public static void loadItems() {
		isLoading = true;
		File fi = new File("plugins//Fortress-Combat-System//Fortress-Combat-Items//Item Storage//Items dropped by Monsters//");
		File[] files = fi.listFiles();
		sendMessageToAll("§eLoaded Items");
		sendMessageToAll("§e§l↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓");
		for(File file : files) {
			if(file.isFile()) {
				if(normalitems.contains(file.getName())) {
				}else{
					FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
					String name = file.getName();
					if(name.startsWith("Item")) {
						if(name.contains(".yml")) {
							String itemname = "";
							itemname = cfg.getString("ItemStack.Item.ItemName");
							itemname = itemname.replace("-oe-", "ö").replace("-ae-", "ä").replace("-ue-", "ü").replace("-s-", "ß");
							itemname = itemname.replace("-Oe-", "Ö").replace("-Ae-", "Ä").replace("-Ue-", "Ü");
							normalitems.add(itemname);
							filenames.add(file.getName());
							sendMessageToAll(itemname.replace("&", "§")+"");
						} else {
							sendMessageToAll("§cERROR: FCI Item File '"+name+"' ist keine '.yml' datei!");
						}
					} else {
						sendMessageToAll("§cERROR: FCI Item File '"+name+"' fängt nicht mit 'Item' an!");
					}
				}
			}
		}
		sendMessageToAll("§e§l↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑");
	}
	
	public static void sendMessageToAll(String msg) {
		for(Player p : Bukkit.getOnlinePlayers()) {
			p.sendMessage(msg);
		}
	}
	
	public static void refreshItems(Player p) {
		String item = "";
		
		File fi = new File("plugins//Fortress-Combat-System//Fortress-Combat-Items//Item Storage//Items dropped by Monsters//");
		File[] files = fi.listFiles();
		main.normalitems.clear();
		main.filenames.clear();
		p.sendMessage("§eLoaded Items");
		p.sendMessage("§e§l↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓");
		for(File file : files) {
			if(file.isFile()) {
				if(main.normalitems.contains(file.getName())) {
				}else{
					FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
					String name = file.getName();
					if(name.startsWith("Item")) {
						if(name.contains(".yml")) {
							String itemname = "";
							itemname = cfg.getString("ItemStack.Item.ItemName");
							itemname = itemname.replace("-oe-", "ö").replace("-ae-", "ä").replace("-ue-", "ü").replace("-s-", "ß");
							main.normalitems.add(itemname);
							main.filenames.add(file.getName());
							p.sendMessage(itemname.replace("&", "§")+"");
						}
					}
				}
			}
		}
		p.sendMessage("§e§l↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑");
	}
}
