package me.otho.metamods.core.meta;

import java.util.HashMap;
import java.util.Set;

import net.minecraft.creativetab.CreativeTabs;

public class CreativeTabHandler {

	private static HashMap<String, CreativeTabs> creativeTabs = new HashMap<String, CreativeTabs>();
	
	public static void initVanillaCreativeTabs() {
		for( CreativeTabs tab : CreativeTabs.CREATIVE_TAB_ARRAY ) {
			creativeTabs.put( tab.getTabLabel(), tab);
		}
	}
	
	public static void addNewCreativeTab( String id, CreativeTabs newTab ) {
		if ( creativeTabs.containsKey(id) ) {
			throw new Error("Tried to register tab:" + id + " more than once.");
		}
		
		creativeTabs.put(id, newTab);
	}
	
	public static CreativeTabs getTab( String id ) {
		return creativeTabs.get(id);
	}
	
	public static Set<String> getCreativeTabs () {
		return creativeTabs.keySet();
	}
}
