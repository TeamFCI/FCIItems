package de.hhb.Items.Generators;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.hhb.main.main;

public class CreateRandomItem {
	public static ArrayList<String> enchants = new ArrayList<String>();
	public static ArrayList<String> lore = new ArrayList<String>();
	@SuppressWarnings("deprecation")
	public static ItemStack generateItem(Player p) {
		lore.clear();
		ItemStack item = new ItemStack(Material.STONE);
		ItemStack IT = new ItemStack(Material.STONE);
		String filename = randomItem();
		File file = new File("plugins//Fortress-Combat-System//Fortress-Combat-Items//Item Storage//Items dropped by Monsters//"+filename);
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
		
		int amount = cfg.getInt("ItemStack.Item.ItemAmount");
		int subid = cfg.getInt("ItemStack.Item.ItemSubID");
		int id = cfg.getInt("ItemStack.Item.ItemID");
		item = new ItemStack(id, amount, (short) subid);
		IT = addEnchants(item, filename);
		IT = addItemFlags(item, filename);
		ItemMeta meta = IT.getItemMeta();
		String name = "";
		name = cfg.getString("ItemStack.Item.ItemName").replace("&",  "§").replace("-oe-", "ö").replace("-ae-", "ä").replace("-ue-", "ü").replace("-sz-", "ß");
		name = name.replace("-Oe-", "Ö").replace("-Ae-", "Ä").replace("-Ue-", "Ü");
		meta.setDisplayName(name);
		int loreLines = cfg.getInt("ItemStack.Item.ItemLoreLines");
		boolean defaultLore = false;
		defaultLore = cfg.getBoolean("ItemStack.Item.ItemProperties.Add default lore to lore");
		if(defaultLore == true) {
			String def = "§aFortress§8-§aCombat§8-§aItem";
			lore.add(def);
		}
		lore.add("§9§m------------------------------");
		for(int i = 1; i < loreLines+1; i++) {
			String s = cfg.getString("ItemStack.Item.ItemLore.line"+i).replace("&",  "§").replace("{USER}",  p.getName());
			s = s.replace("&", "§").replace(";", ",").replace("-oe-", "ö").replace("-ae-", "ä").replace("-ue-", "ü").replace("-sz-", "ß").replace("<3", "§4❤§f").replace("´", "'");
			s = s.replace("{username}", p.getName()).replace("{displayname}", p.getDisplayName()).replace("{level}", p.getLevel() + "").replace("{exp}", p.getExp() + "").replace("{gamemode}", p.getGameMode().toString()).replace("{hearts}", p.getHealth()  + "").replace("{maxhearts}", p.getMaxHealth() + "").replace("{op}", p.isOp() + "");
			s = s.replace("-Oe-", "Ö").replace("-Ae-", "Ä").replace("-Ue-", "Ü");
			lore.add(s);
		}
		lore.add("§9§m------------------------------");
		boolean playerLore = false;
		playerLore = cfg.getBoolean("ItemStack.Item.ItemProperties.Add players owner name to lore");
		if(playerLore == true) {
			String def = "§aBesitzer: §e{USER}".replace("&",  "§").replace("{USER}",  p.getName());
			lore.add(def);
		}
		meta.setLore(lore);
		IT.setItemMeta(meta);
		return IT;
	}
	
	public static String randomItem() {
		String item = "";
		
		Random r = new Random();
		int i = r.nextInt(main.filenames.size());
		item = main.filenames.get(i);
		
		return item;
	}
	
	public static ItemStack addEnchants(ItemStack itemstack, String filename) {
		String s = "";
		File file = new File("plugins//Fortress-Combat-System//Fortress-Combat-Items//Item Storage//Items dropped by Monsters//"+filename);
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
		
		s = cfg.getString("ItemStack.Item.ItemEnchants.SCHUTZ");
		if(s.contains("false:") || s.contains("true:")) {
			String[] array = s.split(":");
			if(array[0].equals("true")) {
				itemstack.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, Integer.valueOf(array[1]));
			}
		}
		s = cfg.getString("ItemStack.Item.ItemEnchants.HALTBARKEIT");
		if(s.contains("false:") || s.contains("true:")) {
			String[] array = s.split(":");
			if(array[0].equals("true")) {
				itemstack.addUnsafeEnchantment(Enchantment.DURABILITY, Integer.valueOf(array[1]));
			}
		}
		s = cfg.getString("ItemStack.Item.ItemEnchants.ATMUNG");
		if(s.contains("false:") || s.contains("true:")) {
			String[] array = s.split(":");
			if(array[0].equals("true")) {
				itemstack.addUnsafeEnchantment(Enchantment.OXYGEN, Integer.valueOf(array[1]));
			}
		}
		s = cfg.getString("ItemStack.Item.ItemEnchants.FEUERSCHUTZ");
		if(s.contains("false:") || s.contains("true:")) {
			String[] array = s.split(":");
			if(array[0].equals("true")) {
				itemstack.addUnsafeEnchantment(Enchantment.PROTECTION_FIRE, Integer.valueOf(array[1]));
			}
		}
		s = cfg.getString("ItemStack.Item.ItemEnchants.AQUA_AFFINITY");
		if(s.contains("false:") || s.contains("true:")) {
			String[] array = s.split(":");
			if(array[0].equals("true")) {
				itemstack.addUnsafeEnchantment(Enchantment.WATER_WORKER, Integer.valueOf(array[1]));
			}
		}
		s = cfg.getString("ItemStack.Item.ItemEnchants.EXPLOSIONSSCHUTZ");
		if(s.contains("false:") || s.contains("true:")) {
			String[] array = s.split(":");
			if(array[0].equals("true")) {
				itemstack.addUnsafeEnchantment(Enchantment.PROTECTION_EXPLOSIONS, Integer.valueOf(array[1]));
			}
		}
		s = cfg.getString("ItemStack.Item.ItemEnchants.SCHUSSSICHER");
		if(s.contains("false:") || s.contains("true:")) {
			String[] array = s.split(":");
			if(array[0].equals("true")) {
				itemstack.addUnsafeEnchantment(Enchantment.PROTECTION_PROJECTILE, Integer.valueOf(array[1]));
			}
		}
		s = cfg.getString("ItemStack.Item.ItemEnchants.DORNEN");
		if(s.contains("false:") || s.contains("true:")) {
			String[] array = s.split(":");
			if(array[0].equals("true")) {
				itemstack.addUnsafeEnchantment(Enchantment.THORNS, Integer.valueOf(array[1]));
			}
		}
		s = cfg.getString("ItemStack.Item.ItemEnchants.FEDERFALL");
		if(s.contains("false:") || s.contains("true:")) {
			String[] array = s.split(":");
			if(array[0].equals("true")) {
				itemstack.addUnsafeEnchantment(Enchantment.PROTECTION_FALL, Integer.valueOf(array[1]));
			}
		}
		s = cfg.getString("ItemStack.Item.ItemEnchants.DEPTH_STRIDER");
		if(s.contains("false:") || s.contains("true:")) {
			String[] array = s.split(":");
			if(array[0].equals("true")) {
				itemstack.addUnsafeEnchantment(Enchantment.DEPTH_STRIDER, Integer.valueOf(array[1]));
			}
		}
		s = cfg.getString("ItemStack.Item.ItemEnchants.KNOCKBACK");
		if(s.contains("false:") || s.contains("true:")) {
			String[] array = s.split(":");
			if(array[0].equals("true")) {
				itemstack.addUnsafeEnchantment(Enchantment.KNOCKBACK, Integer.valueOf(array[1]));
			}
		}
		s = cfg.getString("ItemStack.Item.ItemEnchants.VERBRENNUNG");
		if(s.contains("false:") || s.contains("true:")) {
			String[] array = s.split(":");
			if(array[0].equals("true")) {
				itemstack.addUnsafeEnchantment(Enchantment.FIRE_ASPECT, Integer.valueOf(array[1]));
			}
		}
		s = cfg.getString("ItemStack.Item.ItemEnchants.LOOTING");
		if(s.contains("false:") || s.contains("true:")) {
			String[] array = s.split(":");
			if(array[0].equals("true")) {
				itemstack.addUnsafeEnchantment(Enchantment.LOOT_BONUS_MOBS, Integer.valueOf(array[1]));
			}
		}
		s = cfg.getString("ItemStack.Item.ItemEnchants.POWER");
		if(s.contains("false:") || s.contains("true:")) {
			String[] array = s.split(":");
			if(array[0].equals("true")) {
				itemstack.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, Integer.valueOf(array[1]));
			}
		}
		s = cfg.getString("ItemStack.Item.ItemEnchants.SCHLAG");
		if(s.contains("false:") || s.contains("true:")) {
			String[] array = s.split(":");
			if(array[0].equals("true")) {
				itemstack.addUnsafeEnchantment(Enchantment.ARROW_KNOCKBACK, Integer.valueOf(array[1]));
			}
		}
		s = cfg.getString("ItemStack.Item.ItemEnchants.FLAME");
		if(s.contains("false:") || s.contains("true:")) {
			String[] array = s.split(":");
			if(array[0].equals("true")) {
				itemstack.addUnsafeEnchantment(Enchantment.ARROW_FIRE, Integer.valueOf(array[1]));
			}
		}
		s = cfg.getString("ItemStack.Item.ItemEnchants.UNENDLICHKEIT");
		if(s.contains("false:") || s.contains("true:")) {
			String[] array = s.split(":");
			if(array[0].equals("true")) {
				itemstack.addUnsafeEnchantment(Enchantment.ARROW_INFINITE, Integer.valueOf(array[1]));
			}
		}
		s = cfg.getString("ItemStack.Item.ItemEnchants.EFFIZIENZ");
		if(s.contains("false:") || s.contains("true:")) {
			String[] array = s.split(":");
			if(array[0].equals("true")) {
				itemstack.addUnsafeEnchantment(Enchantment.DIG_SPEED, Integer.valueOf(array[1]));
			}
		}
		s = cfg.getString("ItemStack.Item.ItemEnchants.LUCK");
		if(s.contains("false:") || s.contains("true:")) {
			String[] array = s.split(":");
			if(array[0].equals("true")) {
				itemstack.addUnsafeEnchantment(Enchantment.LOOT_BONUS_BLOCKS, Integer.valueOf(array[1]));
			}
		}
		s = cfg.getString("ItemStack.Item.ItemEnchants.BEHUTSAMKEIT");
		if(s.contains("false:") || s.contains("true:")) {
			String[] array = s.split(":");
			if(array[0].equals("true")) {
				itemstack.addUnsafeEnchantment(Enchantment.SILK_TOUCH, Integer.valueOf(array[1]));
			}
		}
		if(itemstack.getType() == Material.DIAMOND_SWORD || itemstack.getType() == Material.IRON_SWORD || itemstack.getType() == Material.WOOD_SWORD || itemstack.getType() == Material.STONE_SWORD) {
			s = cfg.getString("ItemStack.Item.ItemEnchants.SHARPNESS");
			if(s.contains("false:") || s.contains("true:")) {
				String[] array = s.split(":");
				if(array[0].equals("true")) {
					itemstack.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, Integer.valueOf(array[1]));
				}
			}
		}
		
		return itemstack;
	}
	
	public static ItemStack addItemFlags(ItemStack itemstack, String filename) {
		String s = "";
		File file = new File("plugins//Fortress-Combat-System//Fortress-Combat-Items//Item Storage//Items dropped by Monsters//"+filename);
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
		ItemMeta meta = itemstack.getItemMeta();
		s = cfg.getString("ItemStack.Item.ItemFlags.Hide Enchants");
		if(s.equals("true")) {
			meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		}
		s = cfg.getString("ItemStack.Item.ItemFlags.Hide Attributes");
		if(s.equals("true")) {
			meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		}
		s = cfg.getString("ItemStack.Item.ItemFlags.Hide Placed On");
		if(s.equals("true")) {
			meta.addItemFlags(ItemFlag.HIDE_PLACED_ON);
		}
		s = cfg.getString("ItemStack.Item.ItemFlags.Hide Potion Effects");
		if(s.equals("true")) {
			meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
		}
		s = cfg.getString("ItemStack.Item.ItemFlags.Hide Unbreakable");
		if(s.equals("true")) {
			meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		}
		s = cfg.getString("ItemStack.Item.ItemFlags.Hide Destroys");
		if(s.equals("true")) {
			meta.addItemFlags(ItemFlag.HIDE_DESTROYS);
		}
		itemstack.setItemMeta(meta);
		return itemstack;
	}
}
