package com.gmail.Patey07;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ninja.leaping.configurate.ConfigurationNode;

import org.slf4j.Logger;
import org.spongepowered.api.event.Subscribe;
import org.spongepowered.api.event.entity.player.PlayerBreakBlockEvent;
import org.spongepowered.api.event.entity.player.PlayerPlaceBlockEvent;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;

import com.google.common.base.Function;

public class BlockEvents {
	List<String> breakList = new ArrayList<String>();
	List<String> placeList = new ArrayList<String>();
	String breakWarn = null;
	String placeWarn = null;
	
	public Logger getLogger() {
	    return DFSItems.getLogger();
	}
	
	public BlockEvents(ConfigurationNode rootNode){
		
		Function<Object,String> stringTransformer = new Function<Object,String>() {
		    public String apply(Object input) {
		        if (input instanceof String) {
		            return (String) input;
		        } else {
		            return null;
		        }
		    }
		};
		
	    ConfigurationNode breakNode = rootNode.getNode("Banned","Break");
	    breakList = breakNode.getList(stringTransformer);
	    ConfigurationNode placeNode = rootNode.getNode("Banned","Place");
	    placeList = placeNode.getList(stringTransformer);
	    breakWarn = rootNode.getNode("Warnings","Break").getString();
	    placeWarn = rootNode.getNode("Warnings","Place").getString();
	    
	}
	
	public Text getWarn(String message) {
	    return Texts.of(message).builder().color(TextColors.RED).build();
	}
	
	@Subscribe
	public void onBreak (PlayerBreakBlockEvent breakEvent) {
		String block = breakEvent.getBlock().getType().getName();
		String player = breakEvent.getUser().getName();
		
		String pattern = "(?<=:).*";
		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(block);
		
		if (m.find( )){
			if (breakList.contains(m.group(0))){
				breakEvent.setCancelled(true);
				getLogger().info(player+" attempted to break "+block);
				breakEvent.getUser().sendMessage(getWarn(breakWarn));
			}
		}else{
			getLogger().warn("could not seperate block name and domain of "+block);
		}
	}
	@Subscribe
	public void onPlace (PlayerPlaceBlockEvent placeEvent) {
		String block = placeEvent.getBlock().getType().getName();
		String player = placeEvent.getUser().getName();
		
		String pattern = "(?<=:).*";
		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(block);
		
		if (m.find( )){
			if (placeList.contains(m.group(0))){
				placeEvent.setCancelled(true);
				getLogger().info(player+" attempted to place "+block);
				placeEvent.getUser().sendMessage(getWarn(placeWarn));
			}
		}else{
			getLogger().warn("could not seperate block name and domain of "+block);
		}
	}
}
