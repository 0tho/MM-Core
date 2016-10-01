package me.otho.metamods.core.meta;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.HashMap;
import java.util.Set;

public class CreativeTabHandler {

  private static HashMap<String, CreativeTabs> creativeTabs = new HashMap<String, CreativeTabs>();

  /**
   * Adds all vanilla creative tabs to a hashmap for later use.
   */
  public static void initVanillaCreativeTabs() {
    for (CreativeTabs tab : CreativeTabs.CREATIVE_TAB_ARRAY) {
      String label = ReflectionHelper.getPrivateValue(CreativeTabs.class, tab, "tabLabel");
      creativeTabs.put(label, tab);
    }
  }

  /**
   * Add a new creative tab to creativetabs hashmap.
   * 
   * @param id creativetab id.
   * @param newTab creativeTab.
   */
  public static void addNewCreativeTab(String id, CreativeTabs newTab) {
    if (creativeTabs.containsKey(id)) {
      throw new Error("Tried to register tab:" + id + " more than once.");
    }

    creativeTabs.put(id, newTab);
  }

  public static CreativeTabs getTab(String id) {
    return creativeTabs.get(id);
  }

  public static Set<String> getCreativeTabs() {
    return creativeTabs.keySet();
  }
}
