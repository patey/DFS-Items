package com.gmail.Patey07;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;

import org.slf4j.Logger;
import org.spongepowered.api.event.Subscribe;
import org.spongepowered.api.event.entity.player.PlayerBreakBlockEvent;

import com.google.common.base.Function;

public class BlockEvents {
	
	public Logger getLogger() {
	    return DFSItems.getLogger();
	}
	File potentialFile = new File("DFSItems.conf");
	ConfigurationLoader<CommentedConfigurationNode> loader = HoconConfigurationLoader.builder().setFile(potentialFile).build();
	ConfigurationNode rootNode;
	
	public List<String> getList(String type){
		try {
		    rootNode = loader.load();
		} catch(IOException e) {
		    rootNode = null;
		}
		String test = rootNode.getNode("Banned","Test").getString();
		getLogger().info(test);
		ConfigurationNode listNode = rootNode.getNode("Banned",type);
		
		Function<Object,String> stringTransformer = new Function<Object,String>() {
		    public String apply(Object input) {
		        if (input instanceof String) {
		            return (String) input;
		        } else {
		            return null;
		        }
		    }
		};
		List<String> theList = listNode.getList(stringTransformer);
		return theList;
	}
	
	@Subscribe
	public void onBreak (PlayerBreakBlockEvent breakEvent) {
		String block = breakEvent.getBlock().getType().getName();
		String player = breakEvent.getUser().getName();
		List<String> breakList = getList("Break");
		
		 //debug, checking for a list while I get it working
		getLogger().info(Integer.toString(breakList.size()));
		for (int i = 0; i < breakList.size(); i++){
			getLogger().info(breakList.get(i));
		}
		
		String pattern = "(?<=:).*";
		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(block);
		
		if (m.find( )){
			if (breakList.contains(m.group(0))){
				breakEvent.setCancelled(true);
				getLogger().info(player+" attempted to break "+block);
			}
		}else{
			getLogger().warn("could not seperate block name and domain of "+block);
		}
	}
}
