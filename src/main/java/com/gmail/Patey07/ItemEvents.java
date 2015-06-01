package com.gmail.Patey07;

import java.util.ArrayList;
import java.util.List;
import ninja.leaping.configurate.ConfigurationNode;

import org.slf4j.Logger;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;

import com.google.common.base.Function;

public class ItemEvents {
	List<String> pickList = new ArrayList<String>();
	List<String> dropList = new ArrayList<String>();
	List<String> craftList = new ArrayList<String>();
	String pickWarn = null;
	String dropWarn = null;
	String craftWarn = null;
	
	public Logger getLogger() {
	    return DFSItems.getLogger();
	}
	
	public ItemEvents(ConfigurationNode rootNode){
		
		Function<Object,String> stringTransformer = new Function<Object,String>() {
		    public String apply(Object input) {
		        if (input instanceof String) {
		            return (String) input;
		        } else {
		            return null;
		        }
		    }
		};
		
	    ConfigurationNode pickNode = rootNode.getNode("Banned","PickUp");
	    pickList = pickNode.getList(stringTransformer);
	    ConfigurationNode dropNode = rootNode.getNode("Banned","Drop");
	    dropList = dropNode.getList(stringTransformer);
	    ConfigurationNode craftNode = rootNode.getNode("Banned","Craft");
	    craftList = craftNode.getList(stringTransformer);
	    pickWarn = rootNode.getNode("Warnings","PickUp").getString();
	    dropWarn = rootNode.getNode("Warnings","Drop").getString();
	    craftWarn = rootNode.getNode("Warnings","Craft").getString();
	    
	}
	
	public Text getWarn(String message) {
	    return Texts.of(message).builder().color(TextColors.RED).build();
	}
	

}
