package com.gmail.Patey07;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;

import org.slf4j.Logger;
import org.spongepowered.api.event.Subscribe;
import org.spongepowered.api.event.state.ServerStartedEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.service.config.DefaultConfig;

import com.google.inject.Inject;

@Plugin(id = "DFSItems", name = "DFS Items", version = "0.1.0")
public class DFSItems {
	
	private static Logger logger;

	@Inject
	public DFSItems(Logger logger) {
	    DFSItems.logger = logger;
	}
	
	public static Logger getLogger() {
	    return logger;
	}
	
	@Inject
	@DefaultConfig(sharedRoot = true)
	private File defaultConfig;

	@Inject
	@DefaultConfig(sharedRoot = true)
	private ConfigurationLoader<CommentedConfigurationNode> configManager;
	
	@Subscribe
    public void onServerStart(ServerStartedEvent event) {
		ConfigurationNode config = null;
		try {
		     if (!defaultConfig.exists()) {
		        defaultConfig.createNewFile();
		        config = configManager.load();
		        
		        List<String> breakList = new ArrayList<String>();
		        	breakList.add("tnt");
		        	breakList.add("test");
			    List<String> placeList = new ArrayList<String>();
		        	placeList.add("tnt");
		        	placeList.add("test");
		        config.getNode("Banned","Break").setValue(breakList);
		        config.getNode("Banned","Test").setValue("test");
		        config.getNode("Banned","Place").setValue(placeList);
		        config.getNode("Warnings","Break").setValue("You can't break that!");
		        config.getNode("Warnings","Place").setValue("You can't place that!");
		        config.getNode("Warnings","PickUp").setValue("You can't pick that up!");
		        config.getNode("Warnings","Drop").setValue("You can't drop that!");
		        config.getNode("Warnings","Craft").setValue("You can't craft that!");
		        configManager.save(config);
		    }
		    config = configManager.load();
		} catch (IOException exception) {
		    getLogger().error("The default configuration could not be loaded or created!");
		}
		event.getGame().getEventManager().register(this, new BlockEvents(config));
		getLogger().info("DwarfFortress Suite Item module initialized");
    }
	
}
