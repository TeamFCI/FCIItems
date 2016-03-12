package de.happyhappyboy.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginEnableEvent;

import de.happyhappyboy.main.main;

public class EventPluginEnableEvent implements Listener {
	
	@EventHandler
	public void onEnable(PluginEnableEvent e) {
		if(main.isLoading == true) {
			return;
		}
		main.loadItems();
	}
	
}
